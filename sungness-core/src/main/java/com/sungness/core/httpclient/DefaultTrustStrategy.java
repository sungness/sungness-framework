package com.sungness.core.httpclient;


import org.apache.http.conn.ssl.TrustStrategy;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 模板信任策略，信任所有的ssl
 * Created by wanghongwei on 5/22/15.
 */
public class DefaultTrustStrategy implements TrustStrategy {
    @Override
    public boolean isTrusted(X509Certificate[] x509Certificates, String s)
            throws CertificateException {
        return true;
    }
}
