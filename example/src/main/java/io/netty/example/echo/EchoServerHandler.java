/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.example.echo;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.example.bytebufpackage.TcpPackage;

/**
 * Handler implementation for the echo server.
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.write(msg);
        System.out.println("channelRead");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
        System.out.println("channelReadComplete");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        int i = 0;
        System.out.println("connect success address: " + ctx.channel().remoteAddress());
//        while(i < 100) {
//            if (ctx.channel().isActive()) {
//                String msg = "send msg to client part " + ++i;
//                ctx.channel().writeAndFlush(buildPackage(msg));
//            }
//        }
        if (ctx.channel().isActive()) {
            String msg = "send msg to client part " + ++i;
            ctx.channel().writeAndFlush(buildPackage(msg));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        System.out.println("exceptionCaught " + cause.getMessage());
        ctx.close();
    }

    private TcpPackage buildPackage(Object content) {
        TcpPackage tcpPackage = new TcpPackage();
        int bodyLength = tcpPackage.setBody(content);
        tcpPackage.setHead(HeadPackageConstant.FLAG, HeadPackageConstant.CODE, HeadPackageConstant.VERSION,  bodyLength);
        return tcpPackage;
    }

}
