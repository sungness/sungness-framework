package com.sungness.core.security.aes;

/**
 * 加密消息，包含时间戳、随机数、签名以及加密内容
 * Created by wanghongwei on 28/02/2017.
 */
public class EncryptedMessage {
    /** 消息签名 */
    private String msgSignature;
    /** 时间戳 */
    private String timeStamp;
    /** 随机数 */
    private String nonce;
    /** 加密后的内容 */
    private String encrypt;

    public EncryptedMessage(String msgSignature, String timeStamp,
                            String nonce, String encrypt) {
        this.msgSignature = msgSignature;
        this.timeStamp = timeStamp;
        this.nonce = nonce;
        this.encrypt = encrypt;
    }

    public String getMsgSignature() {
        return msgSignature;
    }

    public void setMsgSignature(String msgSignature) {
        this.msgSignature = msgSignature;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }
}
