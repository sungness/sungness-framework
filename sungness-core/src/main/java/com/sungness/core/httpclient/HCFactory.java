package com.sungness.core.httpclient;

import org.springframework.beans.factory.FactoryBean;

/**
 * HTTP Client 工厂类
 *   将 HTTP、HTTPS 请求做进一步，方便应用程序调用。
 *   如果不需要设置个性化参数，可直接使用 HCUtils。
 * Created by wanghongwei on 8/17/15.
 */
public class HCFactory implements FactoryBean<HClient> {
    public HClient buildHClient() {
        return null;
    }

    @Override
    public HClient getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
