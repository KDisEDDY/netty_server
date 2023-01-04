package io.netty.example.bytebufpackage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class BodyPackage implements IBodyPackage, Serializable {
    private ByteBuf bodyBuffer = null;

    public BodyPackage(ByteBuf bodyBuffer) {
        this.bodyBuffer = bodyBuffer;
    }

    public BodyPackage() {

    }

    protected void initBuffer(int length) {
        if (bodyBuffer == null) {
            bodyBuffer = Unpooled.buffer(length);
        }
    }

    public void reset() {
        bodyBuffer = null;
    }

    @Override
    public int getLength() {
        if (bodyBuffer != null) {
            return bodyBuffer.readableBytes();
        }
        return 0;
    }

    public int setBodyBuffer(String content) {
        reset();
        if (content == null) return 0;
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        initBuffer(contentBytes.length);
        try {
            synchronized (bodyBuffer) {
                bodyBuffer.writeBytes(contentBytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentBytes.length;
    }

    public ByteBuf toByteBuffer() {
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
