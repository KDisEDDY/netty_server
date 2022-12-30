package io.netty.example.packages;

import java.io.Serializable;
import java.nio.ByteBuffer;


public class HeadPackage implements IHeadPackage,Serializable {
    private ByteBuffer headBuffer = null;

    public HeadPackage() {
        headBuffer = ByteBuffer.allocate(LENGTH);
    }

    public HeadPackage(ByteBuffer byteBuffer) {
        headBuffer = byteBuffer;
    }

    @Override
    public byte getAppFlags() {
        synchronized (headBuffer){
            headBuffer.position(0);
            return headBuffer.get();
        }
    }

    @Override
    public short getProtocolCode() {
        synchronized (headBuffer){
            headBuffer.position(1);
            return headBuffer.getShort();
        }
    }

    @Override
    public byte getProtocolVersion() {
        synchronized (headBuffer){
            headBuffer.position(3);
            return headBuffer.get();
        }
    }

    @Override
    public int getPackageBodyLength() {
        synchronized (headBuffer){
            headBuffer.position(4);
            return headBuffer.getInt();
        }
    }

    @Override
    public void setAppFlags(byte flags) {
        synchronized (headBuffer){
            headBuffer.position(0);
            headBuffer.put(flags);
        }
    }

    @Override
    public void setProtocolCode(short code) {
        synchronized (headBuffer){
            headBuffer.position(1);
            headBuffer.putShort(code);
        }
    }

    @Override
    public void setProtocolVersion(byte version) {
        synchronized (headBuffer){
            headBuffer.position(3);
            headBuffer.put(version);
        }
    }


    @Override
    public void setPackageBodyLength(int length) {
        synchronized (headBuffer) {
            headBuffer.position(4);
            headBuffer.putInt(length);
        }
    }

    @Override
    public void reset() {
        headBuffer = null;
    }

    public ByteBuffer toByteBuffer(){
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
