package com.scw.playground.netty.tutorial.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * Created by Shi Chengwei on 22/02/2019.
 */
public class TimeEncoder extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ByteBuf timeBf = ctx.alloc().buffer(4);
        timeBf.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
        super.write(ctx, timeBf, promise);
    }
}
