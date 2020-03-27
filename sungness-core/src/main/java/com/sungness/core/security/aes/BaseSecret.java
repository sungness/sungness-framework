package com.sungness.core.security.aes;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * AES 加解密抽象基类
 * Created by wanghongwei on 28/02/2017.
 */
public abstract class BaseSecret {
    private static final Logger log = LoggerFactory.getLogger(BaseSecret.class);

    protected static String TRANSFORMATION = "AES/CBC/NoPadding";

    protected static String ALGORITHM = "AES";

    protected static Charset CHARSET = Charset.forName("utf-8");

    protected static final int IV_LENGTH = 16;

    protected Base64 base64 = new Base64();
    /** 密钥解码后的byte数组 */
    protected byte[] aesKey;
    /** 会话token */
    protected String token;

    /**
     * 获取扩展信息，appId 或 deviceId，子类根据需要实现该方法
     * @return String
     */
    protected abstract String getExtend();

    protected abstract int getKeyLength();

    /**
     * 构造方法
     * @param token App注册、登录应用平台成功后，应用平台返回的token
     * @param encodingAesKey App注册、登录应用平台成功后，应用平台返回的EncodingAESKey
     *
     * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
     */
    public BaseSecret(String token, String encodingAesKey)
            throws AesException {
        log.debug("encodingAesKey.length()=" + encodingAesKey.length());
        log.debug("KEY_LENGTH=" + getKeyLength());
        if (encodingAesKey.length() != getKeyLength()) {
            throw new AesException(AesException.IllegalAesKey);
        }
        this.token = token;
        aesKey = Base64.decodeBase64(encodingAesKey + "=");
    }

    /**
     * 生成4个字节的网络字节序
     * @param sourceNumber int 源数值
     * @return byte[] 字节数组
     */
    protected byte[] getNetworkBytesOrder(int sourceNumber) {
        byte[] orderBytes = new byte[4];
        orderBytes[3] = (byte) (sourceNumber & 0xFF);
        orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
        orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
        orderBytes[0] = (byte) (sourceNumber >> 24 & 0xFF);
        return orderBytes;
    }

    /**
     * 还原4个字节的网络字节序
     * @param orderBytes byte[] 字节数组
     * @return int 还原后的数值
     */
    protected int recoverNetworkBytesOrder(byte[] orderBytes) {
        int sourceNumber = 0;
        for (int i = 0; i < 4; i++) {
            sourceNumber <<= 8;
            sourceNumber |= orderBytes[i] & 0xff;
        }
        return sourceNumber;
    }

    /**
     * 对明文进行加密.
     *
     * @param content 需要加密的明文
     * @return 加密后base64编码的字符串
     * @throws AesException aes加密失败
     */
    protected String encrypt(String randomStr, String content) throws AesException {
        ByteGroup byteCollector = new ByteGroup();
        byte[] contentBytes = content.getBytes(CHARSET);
        // randomStr + networkBytesOrder + text + extend
        byteCollector.addBytes(randomStr.getBytes(CHARSET));
        byteCollector.addBytes(getNetworkBytesOrder(contentBytes.length));
        byteCollector.addBytes(contentBytes);
        byteCollector.addBytes(getExtend().getBytes(CHARSET));
        // 使用自定义的填充方式对明文进行补位填充
        byteCollector.addBytes(PKCS7Encoder.encode(byteCollector.size()));

        try {
            // 设置加密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(aesKey, 0, IV_LENGTH);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            // 加密，再使用BASE64对加密后的字符串进行编码
            return base64.encodeToString(cipher.doFinal(byteCollector.toBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new AesException(AesException.EncryptAESError);
        }
    }

    /**
     * 对密文进行解密.
     *
     * @param encryptedString 需要解密的密文
     * @return 解密得到的明文
     * @throws AesException aes解密失败
     */
    protected String decrypt(String encryptedString) throws AesException {
        byte[] original;
        try {
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, IV_LENGTH));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            // 使用BASE64对密文进行解码，然后解密
            byte[] base64DecodeBytes = Base64.decodeBase64(encryptedString);
            original = cipher.doFinal(base64DecodeBytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AesException(AesException.DecryptAESError);
        }

        try {
            // 去除补位字符
            byte[] bytes = PKCS7Encoder.decode(original);
            // 分离16位随机字符串,网络字节序和AppId
            byte[] networkOrder = Arrays.copyOfRange(bytes, IV_LENGTH, 20);
            int length = recoverNetworkBytesOrder(networkOrder);
            String extend = new String(Arrays.copyOfRange(bytes, 20 + length, bytes.length), CHARSET);
            // 扩展信息不相同的情况
            if (!extend.equals(getExtend())) {
                throw new AesException(AesException.ValidateAppidError);
            }
            return new String(Arrays.copyOfRange(bytes, 20, 20 + length), CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AesException(AesException.IllegalBuffer);
        }
    }
}