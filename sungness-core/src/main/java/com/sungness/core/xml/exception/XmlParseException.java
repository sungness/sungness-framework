package com.sungness.core.xml.exception;

/**
 * XML解析抛出的异常
 * Created by wanghongwei on 6/5/15.
 */
public class XmlParseException extends Exception {

    private static final long serialVersionUID = 4309213775119827801L;

    /**
     * Constructs a HttpClientException with no detail
     * message. A detail message is a String that describes this
     * particular exception.
     */
    public XmlParseException() {
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
    public XmlParseException(String msg) {
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
    public XmlParseException(String message, Throwable cause) {
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
    public XmlParseException(Throwable cause) {
        super(cause);
    }
}
