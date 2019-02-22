package com.scw.playground.netty.tutorial.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * Created by Shi Chengwei on 21/02/2019.
 */
@Slf4j
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        sendTime(ctx);
//        future.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        log.info("get message: {}", in.toString(Charset.defaultCharset()));
        sendTime(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        log.info("ChannelHandler caught exception: ", cause);
        ctx.close();
    }

    /**
     * 发送当前时间
     * @param ctx
     */
    private void sendTime(ChannelHandlerContext ctx) {
        ByteBuf timeBf = ctx.alloc().buffer(4);
        timeBf.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        ctx.writeAndFlush(timeBf);
    }

}
