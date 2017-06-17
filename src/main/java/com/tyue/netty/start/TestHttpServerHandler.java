package com.tyue.netty.start;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.nio.charset.Charset;


/**
 * 自定义处理器
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    /**
     * 读取客户端的请求,并响应客户端请求
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        /**
         * class io.netty.handler.codec.http.DefaultHttpRequest
         * class io.netty.handler.codec.http.LastHttpContent$1
         * 对应initChannel()方法中添加的处理器
         */
        System.out.println(msg.getClass());
        System.out.println(ctx.channel().remoteAddress());
        if (msg instanceof HttpRequest){
            HttpRequest httpRequest = (HttpRequest) msg;
            System.out.println("channelRead0 invoke:"+httpRequest.method().name());
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求favicon.ico");
            }
        }

        //响应客户端内容
        ByteBuf content = Unpooled.copiedBuffer("Hello World.", Charset.defaultCharset());
        //netty提供的响应，协议以及状态，响应的内容
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);
        //响应的头信息
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

        ctx.writeAndFlush(response);
        //手动关闭
        ctx.channel().close();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler Added.");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel Active.");
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel Registered.");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel Unregistered.");
        super.channelUnregistered(ctx);
    }
}
