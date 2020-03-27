package com.sungness.core.security.hmac;

import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

/**
 * Hmac测试类
 * Created by wanghongwei on 16/08/2017.
 */
public class HmacTest {
    private static final Logger log = LoggerFactory.getLogger(HmacTest.class);

    public static void main(String[] args) {
        try {
            String key = "12324234234";
            String data = "asdflkjasd;fasd";
            log.debug(HmacUtils.hmacSha1Hex(key, data));
            log.debug(Base64Utils.encodeToString(HmacUtils.hmacSha1(key, data)));
            log.debug(Base64Utils.encodeToString(createSignature(key, data)));

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(1024, random);
            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();
            String key2 = "mykey2";
            String pkg = "com.project.app.eass";
            String payId = "pay" + HmacUtils.hmacSha1Hex(key2, "A000001" + pkg).substring(0, 16);
            log.debug("payId: {}", payId);
            log.debug("paySecret: {}", HmacUtils.hmacSha256Hex(payId, pkg));
            log.debug(Base64Utils.encodeToString(priv.getEncoded()));
            log.debug(Base64Utils.encodeToString(pub.getEncoded()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    /**
     * Computes RFC 2104-compliant HMAC signature.
     * signed.
     *
     * @param key  The signing key.
     * @param data The data to be
     * @return The Base64-encoded RFC 2104-compliant HMAC signature.
     * @throws java.security.SignatureException
     *             when signature generation fails
     */
    public static byte[] createSignature(String key, String data)
            throws java.security.SignatureException {
        // String result;
        byte[] rawHmac;
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(),
                    HMAC_SHA1_ALGORITHM);

            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);

            // compute the hmac on input data bytes
            rawHmac = mac.doFinal(data.getBytes());

            // base64-encode the hmac
            // result = Base64.Encoder.Encoding.EncodeBase64(rawHmac);

        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : "
                    + e.getMessage());
        }
        return rawHmac;
    }
}
