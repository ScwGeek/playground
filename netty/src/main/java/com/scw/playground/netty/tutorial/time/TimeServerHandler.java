package com.scw.playground.netty.tutorial.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Shi Chengwei on 21/02/2019.
 */
@Slf4j
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ByteBuf timeBf = ctx.alloc().buffer(4);
        timeBf.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        ctx.writeAndFlush(timeBf);
//        future.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        log.info("ChannelHandler caught exception: ", cause);
        ctx.close();
    }

}
