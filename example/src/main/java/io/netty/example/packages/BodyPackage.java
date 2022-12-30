package io.netty.example.packages;


import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;
import io.netty.example.packages.bodyLinked.AbsBodyLinked;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class BodyPackage implements IBodyPackage, Serializable {
    private ByteBuffer bodyBuffer = null;

    public BodyPackage(ByteBuffer bodyBuffer) {
        this.bodyBuffer = bodyBuffer;
    }

    public BodyPackage() {

    }

    protected void initBuffer(int length) {
        if (bodyBuffer == null) {
            bodyBuffer = ByteBuffer.allocate(length);
        }
    }

    public void reset() {
        bodyBuffer = null;
    }

    @Override
    public int getLength() {
        if (bodyBuffer != null) {
            return bodyBuffer.limit();
        }
        return 0;
    }

    public int setBodyBuffer(AbsBodyLinked linked) {
        reset();
        if (linked == null) return 0;
        int totalLength = linked.getTotalLength();
        initBuffer(totalLength);
        try {
            synchronized (bodyBuffer) {
                while (linked != null && linked.link(bodyBuffer)) {
                    linked = linked.next();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalLength;
    }

    public int setBodyBuffer(String content) {
        reset();
        if (content == null) return 0;
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        initBuffer(contentBytes.length);
        try {
            synchronized (bodyBuffer) {
                bodyBuffer.put(contentBytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentBytes.length;
    }

    public ByteBuffer toByteBuffer() {
        return bodyBuffer;
    }

    @Override
    public String toString() {
        String result = null;
        try {
            if (bodyBuffer == null) {
                result = "";
            } else {
                result = new String(bodyBuffer.array(), "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            result = "UnsupportedEncodingException";
        }
        return result;
    }
}
