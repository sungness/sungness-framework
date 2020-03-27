/**
 * Service层处理异常类，异常封装了可能存在的相关字段、key、错误提示信息
 */
package com.sungness.core.service;


/**
 * @author wanghongwei
 * @since v2.0 2014-03-19
 */
public class ServiceProcessException extends Exception {

    private static final long serialVersionUID = 4324348262759503346L;

    /** 字段名 */
    private String field;

    /** 错误码 */
    private String errorCode;

    /** 默认错误信息 */
    private String defaultMessage;

    /**
     * 异常构造方法
     * @param field the field name (may be {@code null} or empty String)
     * @param errorCode error code, interpretable as a message key
     * @param defaultMessage fallback default message
     */
    public ServiceProcessException(
            String field, String errorCode, String defaultMessage) {
        super(defaultMessage);
        this.field = field;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
    }

    /**
     * 异常构造方法
     * @param field the field name (may be {@code null} or empty String)
     * @param errorCode error code, interpretable as a message key
     */
    public ServiceProcessException(String field, String errorCode) {
        super("Service处理异常");
        this.field = field;
        this.errorCode = errorCode;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    /**
     * Constructs a HttpClientException with no detail
     * message. A detail message is a String that describes this
     * particular exception.
     */
    public ServiceProcessException() {
        super();
    }

    /**
     * Constructs a HttpClientException with the specified
     * detail message. A detail message is a String that describes
     * this particular exception, which may, for example, specify which
     * algorithm is not available.
     *
     * @param msg the detail message.
     */
    public ServiceProcessException(String msg) {
        super(msg);
    }

    /**
     * Creates a <code>HttpClientException</code> with the specified
     * detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A <tt>null</tt> value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     */
    public ServiceProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a <code>HttpClientException</code> with the specified cause
     * and a detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A <tt>null</tt> value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     */
    public ServiceProcessException(Throwable cause) {
        super(cause);
    }
}
