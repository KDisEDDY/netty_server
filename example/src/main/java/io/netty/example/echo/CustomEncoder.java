package io.netty.example.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.example.packages.TcpPackage;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 自定义编码器，用于规范tcp包
 */
public class CustomEncoder extends MessageToByteEncoder<TcpPackage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, TcpPackage msg, ByteBuf out) throws Exception {
        if (msg != null) {
            out.writeBytes(msg.toByteBuffer());
        }
    }
}
