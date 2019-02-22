package com.scw.playground.netty.tutorial.time;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Shi Chengwei on 21/02/2019.
 */
public class TimeClient {

    private String host;

    private int port;

    private EventLoopGroup worker;
    private ChannelFuture channelFuture;

    public TimeClient(String host, int port) {
        this.port = port;
        this.host = host;
    }

    /**
     * 启动客户端
     *
     * @throws InterruptedException
     */
    public void start() throws InterruptedException {

        worker = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(new TimeClientHandler());
                        }
                    });

            channelFuture = bootstrap.connect(host, port).sync();
        } catch (Exception e) {
            worker.shutdownGracefully();
        }
    }

    /**
     * 向服务器发送消息
     *
     * @param message
     * @return
     */
    public ChannelFuture send(String message) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(message.getBytes());
        return channelFuture.channel().writeAndFlush(byteBuf);
    }

    /**
     * 关闭客户端
     */
    public void stop() {
        worker.shutdownGracefully();
    }

    /**
     * 开启聊天模式。
     * 客户端随便发个啥，服务器返回当前时间。直到输入结束。
     *
     * @param timeClient
     * @throws Exception
     */
    public static void startChat(TimeClient timeClient) throws Exception {

        //Enter data using BufferReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        do {
            // Reading data using readLine
            String str = reader.readLine();
            if (str == null) {
                break;
            }
            timeClient.send(str);
        } while (true);
    }

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 8010;


        TimeClient timeClient = new TimeClient(host, port);

        timeClient.start();

        // todo 处理连接中断的问题
        startChat(timeClient);

        timeClient.stop();

    }

}
