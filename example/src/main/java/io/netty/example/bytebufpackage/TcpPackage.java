package io.netty.example.bytebufpackage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.Serializable;

public class TcpPackage implements Serializable {
    private HeaderPackage mHeadPackage = null;

    private BodyPackage mBodyPackage = null;


    public TcpPackage() {
        mHeadPackage = new HeaderPackage();
        mBodyPackage = new BodyPackage();
    }

    public int getPackageLength() {
        return mHeadPackage.LENGTH + mBodyPackage.getLength();
    }

    public void setHead(byte flags, short code, byte version, int bodyLength) {
        mHeadPackage.setAppFlags(flags);
        mHeadPackage.setProtocolCode(code);
        mHeadPackage.setProtocolVersion(version);
        mHeadPackage.setPackageBodyLength(bodyLength);
    }

    public int setBody(Object content) {
        if (content instanceof String) {
            return mBodyPackage.setBodyBuffer((String) content);
        } else {
            return 0;
        }
    }

    public ByteBuf toByteBuffer() {
        ByteBuf byteBuffer = Unpooled.buffer(mHeadPackage.LENGTH + mBodyPackage.getLength());
        byteBuffer.writeBytes(mHeadPackage.toByteBuffer());
        if (mBodyPackage.toByteBuffer() != null) {
            byteBuffer.writeBytes(mBodyPackage.toByteBuffer());
        }
        return byteBuffer;
    }

    public BodyPackage getBodyPackage() {
        return mBodyPackage;
    }

    public HeaderPackage getHeadPackage() {
        return mHeadPackage;
    }

    public void setBodyPackage(BodyPackage bodyPackage) {
        mBodyPackage = bodyPackage;
    }

    public void setHeadPackage(HeaderPackage headPackage) {
        mHeadPackage = headPackage;
    }

    @Override
    public String toString() {
        String result = "";
        result = "head:" + mHeadPackage + "  \tbody:" + mBodyPackage;
        return result;
    }


}
