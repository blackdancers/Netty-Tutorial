package com.tyue.netty.forsocket.server;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * ctx
     * @param ctx --> 上下文相关的信息
     * @param msg --> 接收到客户端请求的信息
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println("IP:"+ctx.channel().remoteAddress()+"\nmsg:"+msg);
        ctx.channel().writeAndFlush("from Server msg:"+ UUID.randomUUID());
    }

    /**
     * 出现异常，关闭连接
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
        //super.exceptionCaught(ctx, cause);
    }
}
