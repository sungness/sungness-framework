package com.sungness.core.protostuff;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.io.ByteArrayOutputStream;

/**
 * 对象序列化／反序列化工具类
 *  1、将 T 对象序列化成 byte[]
 *  2、将 byte[] 数组反序列化成 T 对象
 * Created by wanghongwei on 15/02/2017.
 */
public class RedisRuntimeSchema<T> {

    private final LinkedBuffer BUFFER = LinkedBuffer.allocate();

    private final Schema<T> SCHEMA;

    public RedisRuntimeSchema(Class<T> cls) {
        SCHEMA = RuntimeSchema.getSchema(cls);
    }

    public byte[] serialize(T dataObj) throws java.io.IOException {
        ByteArrayOutputStream temp = new ByteArrayOutputStream();
        BUFFER.clear();
        ProtobufIOUtil.writeTo(temp, dataObj, SCHEMA, BUFFER);
        byte[] bytes = temp.toByteArray();
        temp.close();
        return bytes;
    }

    public T deserialize(byte[] bytes) {
        T tmp = SCHEMA.newMessage();
        ProtobufIOUtil.mergeFrom(bytes, tmp, SCHEMA);
        return tmp;
    }
}
