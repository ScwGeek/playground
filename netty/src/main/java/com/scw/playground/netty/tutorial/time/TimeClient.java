package com.scw.playground.netty.tutorial.time;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by Shi Chengwei on 21/02/2019.
 */
public class TimeClient {

    private String host;

    private int port;

    public TimeClient(String host, int port) {
        this.port = port;
        this.host = host;
    }

    public void run() throws InterruptedException {

        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(new TimeClientHandler());
                        }
                    });

            ChannelFuture cf = bootstrap.connect(host, port).sync();
            cf.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 8010;

        TimeClient timeClient = new TimeClient(host, port);
        timeClient.run();
    }

}
