package com.sungness.core.security.aes;

import com.sungness.core.util.DateUtil;
import com.sungness.core.util.GsonUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AES 加解密样例程序
 * Created by wanghongwei on 28/02/2017.
 */
public class AesDemo {
    private static final Logger log = LoggerFactory.getLogger(AesDemo.class);

    public static void main2(String[] args) {
        try {
            // 需要加密的明文
            String token = "MTQ4ODYxMDY3NTgyMsMzAzMmEzNmRmYTIyZTM3NjIwODliNDdmYjU4NWNjYTcxMDk1ZjFmNTZhMTVjNGNhZjhhOjFfXzU4YmE1ZTY2MDBjOTYzYjYwMDAwMDAwMV9fQWlBaWVjS3ZSM1FHblNaU21hSVN0dFgxbUhQcXUyMk1KT09FS3FLbTNKelFfXzRsd09CSU1CbUVrdnAwWHpqUWRGa2cweE9nbWg5MUl6Nm9lQ0kxUGxqeXo6OTg1ZDFkZWU1YjY1NWE2NzhmMGQ4YjhhMTE3MGE2YWNjNWQ1ZmQwNjFhZTg3YWU4N2E4Yzc3MzE4MTM4ZTYxMmZjNTQ2NTNkNjQyNTQ5OWQyYjMxNGFhYTQwMGUzZTUzZDkxNmVhZTg4Mjk3ODBlNWM1NTJmM2E0YzNiNTJiZjg";
            String timestamp = "1488610680";
            String nonce = "1914910957";
            String msgSignature = "2426a0edeb92368e25f205e816873e0d6fe5a0a8";
            String aesKey = "4lwOBIMBmEgmh91Iz6oeCI1Pkvp0XzjQdFkg0xOljyz";
            String deviceId = "AiAiISttX1mHPqu22MJOOEKqecKvR3QGnSZSmaKm3JzQ";
            AesSecret aesSecret = new AesSecret(token, aesKey, deviceId);

            //json
            String content = "abcdddd";
            log.info("明文" + content);
            String encodingAesKey2 = RandomStringUtils.randomAlphanumeric(43);
            log.info("encodingAesKey2：" + encodingAesKey2);

            String encrypted = GsonUtils.toJson(aesSecret.encrypt(content, timestamp, nonce));
            log.info("密文：" + encrypted);
            EncryptedMessage encryptedMessage =
                    GsonUtils.fromJson(encrypted, EncryptedMessage.class);
            log.info("解密后json：" + aesSecret.decrypt(encryptedMessage));

//            EncryptedMessage encryptedMessage2 = new EncryptedMessage(
//                    "e0edf954c49c1dffc2207de60f159cd7e1d8d772f56ab18e6e7cdcd1097b4c3a",
//                    "1488610680",
//                    "1914910957",
//                    "LoN0tP6pQlOIY7wEgAD7hLCVnmSYT53kUrd3F4R5Pp5i9r6TatRLhOG+T0eZUJ0+hiOFc2jQ/Ax1NEpmPUZVA1hbK4EE698rcn6G7LHyV8rNie8e3KC68kkuUKOzjfuBLJnbGIYjFg4G8Mis26FKxGxPbCc8lE7gTgZkZJRpmLCYUiZHcrxceKhisIyC/qBZNfttBNSysrXfm2akJUKKUCax4v7ZW8Um0K2rZTUTaL/nwYsbxEUpt5AQVsmgW+oA3Ngnh976fiqmMgHOl06cJjF4bi3dxid/WfbRKiwAE0VSRT0UsAHSV/a0R9FXP3PsNiAfNWANui/Ao5KoyQ3S+uxJzs66IgEufMOL39WWtI26/b1lp1CCcyRGAZRk4W/MnxgaBhDomdrxtmvMiOwpQUrbM8XOvIeySkUsBTddHBf3/a598kODpeeFkgrUU+aRtxrp1xqFA2ij/6+PvzjXi8IqIQmfJsLIzy5tmQi23mpeh+sh7/vlgCs3GR58ZmyfFUUC2svzseoAgmApgZExXZ3OEJokOEar3uqJFzUQE1yFQuH2UuR5l4JNMYX2zySqPq5OlsCXqIdvx6lI+2lZZY417J+4QjWlSW6vDyrAKI5yDZMTvwUNlRbVlPQo1BnnYLxGphWsN+cvLc53Bks1qurlraki4mHq3q8DonD4Lttxd0Hk0r5I/9Mbg02MFD5+DEAbr/0i5tKPYFdohL6QW36OArrUJasrHCbSQiZovVNIBMLPxIRM0vq3TKdmWykW6YuGvMk2izjcs7qBKUzh3PW93m3y/5vP86SPzyBjZNxHjBGrTA0PuSYv2m/zP6S6Ch4eUv/WdFywAWm85P5CAEr0YyNFrLsGKQVT1fpKhKRkWOQEQFLOYu917HxIpu1TQ/vkqkzVSPnhwZItUyPS9dB6flKgRQbybMcqmS/RIs1/C/HhmK89f/LLSF5zWzk6upZaFhlbn7J07dt/uYiKYalcudwxdcLpv0v4v+WbtaauKvH//Z7uHxLSffsjufs+0HsOAmpN0CSYEN+NvEi4hzM7xNFwYXmxl9KWfKvmAQ0="
//            );
//            log.info("解密后json：" + aesSecret.decrypt(encryptedMessage2));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void xmlTest() {
        try {
            // 需要加密的明文
            String encodingAesKey = "rjR2UegvIgTY41oAvfOL59yEjbFkgD41Ptao4dI9qn3";
            String token = "TgyMTpiYzRiNjhhYTc0NTJmMzAzMmMTQ4ODYxMDY3NEzNmRmYTIyZTM3NjIwODliNDdmYjU4NWNjYTcxMDk1ZjFmNTZhMTVjNGNhZjhhOjFfXzU4YmE1ZTY2MDBjOTYzYjYwMDAwMDAwMV9fQWlBaWVjS3ZSM1FHblNaU21hSVN0dFgxbUhQcXUyMk1KT09FS3FLbTNKelFfXzRsd09CSU1CbUVrdnAwWHpqUWRGa2cweE9nbWg5MUl6Nm9lQ0kxUGxqeXo6OTg1ZDFkZWU1YjY1NWE2NzhmMGQ4YjhhMTE3MGE2YWNjNWQ1ZmQwNjFhZTg3YWU4N2E4Yzc3MzE4MTM4ZTYxMmZjNTQ2NTNkNjQyNTQ5OWQyYjMxNGFhYTQwMGUzZTUzZDkxNmVhZTg4Mjk3ODBlNWM1NTJmM2E0YzNiNTJiZjg";
            String timestamp = "1476006007";
            String nonce = "1353081791";
            String msgSignature = "5e816873e0d6f42625f20a0edeb92368e2e5a0a8";
            String appId = "wx9ef2f31818765cf6";

            String postData = "<xml>" +
                    "    <ToUserName><![CDATA[gh_ded06225a119]]></ToUserName>" +
                    "    <FromUserName><![CDATA[oJ2tXDxG0HbwECTp1z8Q-REy_YBk]]></FromUserName>" +
                    "    <CreateTime>1476006007</CreateTime>" +
                    "    <MsgType><![CDATA[text]]></MsgType>" +
                    "    <Content><![CDATA[hi]]></Content>" +
                    "    <MsgId>6339399354752917312</MsgId>" +
                    "    <Encrypt><![CDATA[2kTUr6hAC/Fhuzbs9cJ2SeqMwYK7B2OyRegGW5ZlT8Glh5avXYxlH2bJJfKwnaWe8GJNmcConubu+N57sPM/g//j0g9rDV7sQJLVNDHs64pBcEGQy3zpF3s+/bywS4vXg8hL+mUnDTh2bvywQFIW6jLI3313+ExseKKNuyr8gW4662mQ/WBDEfBJMruKheinunxtLyucMhBVYvvAATWiNsbIBmvvjgCH4qbGMi2sVqYZzrq6BDCup1ysGP02GHx/ZNNSC80JmvnQcEzmdAerTyFmhLwj5SXEkBWzeMdfgrn+fYnHT0y2wJPFtmNMhMOmCJv2I2bqvs3loOR43t7to8KC9dqKwDdlU43NRi8WPb0ITuNgqBS42Chix5s1XQEq7XVB9mmZS5zTfEouehBbUDOaP0RudktrA7cDUHOfEpA=]]></Encrypt>" +
                    "</xml>";

            String encrypt = "2kTUr6hY/Fhuzbs9cJ2SK7B2OyReACeqMwgGW5ZlT8Glh5avXYxlH2bJJfKwnaWe8GJNmcConubu+N57sPM/g//j0g9rDV7sQJLVNDHs64pBcEGQy3zpF3s+/bywS4vXg8hL+mUnDTh2bvywQFIW6jLI3313+ExseKKNuyr8gW4662mQ/WBDEfBJMruKheinunxtLyucMhBVYvvAATWiNsbIBmvvjgCH4qbGMi2sVqYZzrq6BDCup1ysGP02GHx/ZNNSC80JmvnQcEzmdAerTyFmhLwj5SXEkBWzeMdfgrn+fYnHT0y2wJPFtmNMhMOmCJv2I2bqvs3loOR43t7to8KC9dqKwDdlU43NRi8WPb0ITuNgqBS42Chix5s1XQEq7XVB9mmZS5zTfEouehBbUDOaP0RudktrA7cDUHOfEpA=";
            WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
            //公众号平台收的消息
            String result2 = pc.decrypt(msgSignature, timestamp, nonce, postData);
            log.info("解密后明文: " + result2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            String token = "=";
            String aesKey = "";
            String deviceId = "";

            String encryptKey = AesSecret.getEncryptKey();
            log.info("encryptKey=" + encryptKey);
            String body = "aaaasdfsadfasdfsadfsadfsafdasdf";
            AesSecret aesSecret2 = new AesSecret(token, encryptKey, deviceId);
            EncryptedMessage eResult = aesSecret2.encrypt(
                    GsonUtils.toJson(body), "" + DateUtil.getTimestamp(), RandomStringUtils.randomNumeric(10));
            log.info(GsonUtils.toJson(eResult));
            log.info(eResult.getEncrypt());
            log.info(aesSecret2.decrypt(eResult));
            log.info(Base64.encodeBase64String("9c6cf6ea966ad30f30a4dfb67e69274769c1054beeb9e436d0a680f105b4bb66".getBytes()));
            AesSecret aesSecret3 = new AesSecret(token, "pIvmytIbSSnAeo0GxksRdp9Ga", deviceId);
            log.info(aesSecret3.decrypt("7zQYgAUmCcSpekR8TnUv15/5GQt1LoU86dsr7ZsQ+Ks+kM5rZ9SRYPiqPPBY1vtCmrX0Gd7yKccMjGb4Xs7GoYv7fVbR332xHgpbkv85cs/1beVcxQnMv9W3LNINYyoGVNNtvUb6+2JnvHYk365ICg5f5CS2/NQ="));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
