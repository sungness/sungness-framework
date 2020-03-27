package com.sungness.core.httpclient;

/**
 * HttpClient工具抛出的异常
 * Created by wanghongwei on 5/22/15.
 */
public class HttpClientException extends Exception {
    private static final long serialVersionUID = 5271107847129743931L;

    private String content;

    /**
     * Constructs a HttpClientException with no detail
     * message. A detail message is a String that describes this
     * particular exception.
     */
    public HttpClientException() {
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
    public HttpClientException(String msg) {
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
    public HttpClientException(String message, Throwable cause) {
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
    public HttpClientException(Throwable cause) {
        super(cause);
    }

    public HttpClientException(String message, String content) {
        this(message);
        this.content = content;
    }

    public HttpClientException(String message, Throwable cause, String content) {
        this(message, cause);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
