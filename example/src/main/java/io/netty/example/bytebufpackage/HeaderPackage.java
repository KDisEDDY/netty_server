package io.netty.example.bytebufpackage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.Serializable;

public class HeaderPackage implements IHeadPackage, Serializable {
    private ByteBuf headBuffer = null;

    public HeaderPackage() {
        headBuffer = Unpooled.buffer(LENGTH);
    }

    public HeaderPackage(ByteBuf byteBuffer) {
        headBuffer = byteBuffer;
    }
    @Override
    public byte getAppFlags() {
        synchronized (headBuffer){
            return headBuffer.getByte(0);
        }
    }

    @Override
    public short getProtocolCode() {
        synchronized (headBuffer){
            return headBuffer.getShort(1);
        }
    }

    @Override
    public byte getProtocolVersion() {
        synchronized (headBuffer){
            return headBuffer.getByte(3);
        }
    }

    @Override
    public int getPackageBodyLength() {
        synchronized (headBuffer){
            return headBuffer.getInt(4);
        }
    }

    @Override
    public void setAppFlags(byte flags) {
        synchronized (headBuffer){
            headBuffer.writerIndex(0);
            headBuffer.writeBytes(new byte[]{flags});
        }
    }

    @Override
    public void setProtocolCode(short code) {
        synchronized (headBuffer){
            headBuffer.writerIndex(1);
            headBuffer.writeShort(code);
        }
    }

    @Override
    public void setProtocolVersion(byte version) {
        synchronized (headBuffer){
            headBuffer.writerIndex(3);
            headBuffer.writeBytes(new byte[]{version});
        }
    }


    @Override
    public void setPackageBodyLength(int length) {
        synchronized (headBuffer) {
            headBuffer.writeInt(length);
        }
    }

    @Override
    public void reset() {
        headBuffer = null;
    }

    public ByteBuf toByteBuffer(){
        if (headBuffer != null) headBuffer.writerIndex(LENGTH);
        return headBuffer;
    }

    @Override
    public String toString() {
        String result = "";
        result = "{app标志:"+getAppFlags()+"}," +
                "{协议代码:"+getProtocolCode()+"}," +
                "{协议版本:"+getProtocolVersion()+"},"+
                "{包体长度:"+getPackageBodyLength()+"}";
        return result;
    }
}
