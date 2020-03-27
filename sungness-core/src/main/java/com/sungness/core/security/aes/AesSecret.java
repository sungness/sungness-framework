package com.sungness.core.security.aes;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * API接口消息的加解密接口(UTF8编码的字符串).
 * <ol>
 *     <li>App提交加密消息给应用平台API</li>
 *     <li>应用平台API接收App提交的消息，验证消息的安全性，并对消息进行解密。</li>
 * </ol>
 * 说明：异常java.security.InvalidKeyException:illegal Key Size的解决方案
 *     服务器端通过sha256生成 encodingAesKey，长度为64个字符。
 * <ol>
 *     <li>在官方网站下载JCE无限制权限策略文件（JDK7的下载地址：
 *      http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html</li>
 *     <li>下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt</li>
 *     <li>如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件</li>
 *     <li>如果安装了JDK，将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件</li>
 * </ol>
 */
public class AesSecret extends BaseSecret {
    private static final Logger log = LoggerFactory.getLogger(AesSecret.class);

    protected static final int KEY_LENGTH = 32;

    /** app设备id */
    private String deviceId;

    @Override
    protected String getExtend() {
        return deviceId;
    }

    @Override
    protected int getKeyLength() {
        return KEY_LENGTH;
    }

    /**
     * 构造方法
     * 为aesKey赋值的方式覆盖基类中的赋值，不使用微信原有的base64解码，因其他语言
     * 如objective-c中对非base64编码的字符串解码会报错。
     * @param token App注册、登录应用平台成功后，应用平台返回的token
     * @param encodingAesKey App注册、登录应用平台成功后，应用平台返回的EncodingAESKey
     * @param deviceId app设备id
     *
     * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
     */
    public AesSecret(String token, String encodingAesKey, String deviceId)
            throws AesException {
        super(token, encodingAesKey);
        aesKey = encodingAesKey.getBytes();
        this.deviceId = deviceId;
    }

    /**
     * 将应用平台API返回给APP的消息加密打包.
     * <ol>
     *     <li>对要发送的消息进行AES-CBC加密</li>
     *     <li>生成安全签名</li>
     *     <li>将消息密文和安全签名打包成json格式</li>
     * </ol>
     * 
     * @param content 公众平台待回复用户的消息，xml格式的字符串
     * @param timeStamp 时间戳，可以自己生成，也可以用URL参数的timestamp
     * @param nonce 随机串，可以自己生成，也可以用URL参数的nonce
     * 
     * @return 加密后的可以直接回复用户的密文，
     *  包括signature, timestamp, nonce, encrypt的json格式的字符串
     * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
     */
    public EncryptedMessage encrypt(String content, String timeStamp, String nonce)
            throws AesException {
        // 加密
        String encrypt =
                encrypt(RandomStringUtils.randomAlphanumeric(IV_LENGTH), content);
        // 生成安全签名
        if (StringUtils.isBlank(timeStamp)) {
            timeStamp = Long.toString(System.currentTimeMillis());
        }
        String signature = ShaUtils.sha256Hex(token, timeStamp, nonce, encrypt);
        log.debug("发送给平台的签名是: " + signature);
        // 生成返回的json
        return new EncryptedMessage(signature, timeStamp, nonce, encrypt);
    }

    /**
     * 检验消息的真实性，并且获取解密后的明文.
     * <ol>
     *     <li>利用收到的密文生成安全签名，进行签名验证</li>
     *     <li>若验证通过，则提取json中的加密消息</li>
     *     <li>对消息进行解密</li>
     * </ol>
     * 
     * @param msgSignature 签名串，对应URL参数的signature
     * @param timeStamp 时间戳，对应URL参数的timestamp
     * @param nonce 随机串，对应URL参数的nonce
     * @param encrypt 密文，对应POST请求的数据
     * @return String 解密后的原文
     * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
     */
    public String decrypt(String msgSignature, String timeStamp,
                          String nonce, String encrypt)
            throws AesException {
        // 验证安全签名
        String signature = ShaUtils.sha256Hex(token, timeStamp, nonce, encrypt);
        // 和URL中的签名比较是否相等
        log.debug("原签名：" + msgSignature);
        log.debug("校验签名：" + signature);
        if (!signature.equals(msgSignature)) {
            throw new AesException(AesException.ValidateSignatureError);
        }
        // 解密
        return decrypt(encrypt);
    }

    /**
     * 检验消息的真实性，并且获取解密后的明文.
     * @param encryptedMessage EncryptedMessage 加密消息对象
     * @return String 解密后的原文
     * @throws AesException
     */
    public String decrypt(EncryptedMessage encryptedMessage)
            throws AesException {
        return decrypt(encryptedMessage.getMsgSignature(),
                encryptedMessage.getTimeStamp(),
                encryptedMessage.getNonce(),
                encryptedMessage.getEncrypt());
    }

    /**
     * 获取加密、解密用的key，32个数字、字母随机组合
     * @return String 随机字符串
     */
    public static String getEncryptKey() {
        return RandomStringUtils.randomAlphanumeric(KEY_LENGTH);
    }
}