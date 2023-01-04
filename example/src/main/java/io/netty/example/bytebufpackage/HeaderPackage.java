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
            return headBuffer.getByte(2);
        }
    }

    @Override
    public int getPackageBodyLength() {
        synchronized (headBuffer){
            return headBuffer.getInt(3);
        }
    }

    @Override
    public void setAppFlags(byte flags) {
        synchronized (headBuffer){
            headBuffer.writerIndex(0);
            headBuffer.setByte(0, flags);
        }
    }

    @Override
    public void setProtocolCode(short code) {
        synchronized (headBuffer){
            headBuffer.writerIndex(1);
            headBuffer.setByte(1, code);
        }
    }

    @Override
    public void setProtocolVersion(byte version) {
        synchronized (headBuffer){
            headBuffer.writerIndex(3);
            headBuffer.setByte(3, version);
        }
    }


    @Override
    public void setPackageBodyLength(int length) {
        synchronized (headBuffer) {
            headBuffer.writerIndex(4);
            headBuffer.setInt(4, length);
        }
    }

    @Override
    public void reset() {
        headBuffer = null;
    }

    public ByteBuf toByteBuffer(){
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
