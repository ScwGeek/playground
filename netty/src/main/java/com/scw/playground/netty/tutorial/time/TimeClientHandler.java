package com.scw.playground.netty.tutorial.time;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * Created by Shi Chengwei on 21/02/2019.
 */
@Slf4j
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        int time = (Integer) msg;
        long currentTimeMillis = (time - 2208988800L) * 1000L;
        log.info("current time: {}", new Date(currentTimeMillis));
//      ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        log.info("ChannelHandler caught exception: ", cause);
//        ctx.close();
    }

}
