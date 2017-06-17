package com.tyue.netty.forsocket.client;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * 客户端的处理器
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {

    /**
     * ctx
     * @param ctx --> 上下文相关的信息
     * @param msg --> 接收到客户端请求的信息
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println("Server IP:"+ctx.channel().remoteAddress()+"\nmsg:"+msg);
        System.out.println("Client Output msg:"+msg);
        ctx.writeAndFlush("from client:"+ LocalDateTime.now());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("come from client");
        super.channelActive(ctx);
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
    }
}
