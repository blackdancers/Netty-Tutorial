package com.tyue.netty.start;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty第一个程序：
 * 客户端发出不带参数的请求，服务端返回一个"Hello World"
 *
 */
public class TestServer {
    public static void main(String[] args){
        /**
         * 两个线程组，事件循环组(死循环)
         * bossGroup：不断地接收客户端连接，但是不对连接处理，转给workerGroup，由它对连接进行后续处理。
         * workerGroup：处理客户端请求，响应
         *
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //对服务端的启动进行了基本的封装，简化服务端启动
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            /**
             * bossGroup==>>workerGroup
             */
            serverBootstrap.group(bossGroup,workerGroup)
                    /*which uses NIO selector based implementation to accept new connections*/
                    //反射创建
                    .channel(NioServerSocketChannel.class)
                    /*(初始化器)自定义子处理器，对请求真正的进行处理*/
                    .childHandler(new TestServerInitializer());

            //绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //netty提供的优雅关闭方式
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
