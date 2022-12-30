package io.netty.example.packages;

public interface IHeadPackage {

    int LENGTH = 10;

    byte getAppFlags();

    short getProtocolCode();

    byte getProtocolVersion();

    int getPackageBodyLength();

    void setAppFlags(byte flags);

    void setProtocolCode(short code);

    void setProtocolVersion(byte version);

    void setPackageBodyLength(int length);

    void reset();
}
