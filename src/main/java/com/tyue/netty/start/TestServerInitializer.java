package com.tyue.netty.start;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 子处理器
 *
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 初始化管道 ————回调
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //管理过滤器，相当于拦截器
        ChannelPipeline pipeline = ch.pipeline();
        // netty提供的Handler，web的请求和响应，编解码用的
        // HttpServerCodec：是HttpRequestDecoder和HttpResponseEncoder的组合，使服务器端的HTTP实现更容易。(A combination of HttpRequestDecoder and HttpResponseEncoder which enables easier server side HTTP implementation.)
        pipeline.addLast("httpServerCodec",new HttpServerCodec());
        //自定义若干处理器(Handler)
        pipeline.addLast("myHttpServerHandler",new TestHttpServerHandler()); //多实例
    }
}
