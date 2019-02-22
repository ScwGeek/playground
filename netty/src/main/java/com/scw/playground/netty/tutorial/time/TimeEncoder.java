package com.scw.playground.netty.tutorial.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Shi Chengwei on 22/02/2019.
 */
public class TimeEncoder extends MessageToByteEncoder<Integer> {

    protected void encode(ChannelHandlerContext ctx, Integer time, ByteBuf out) throws Exception {
        out.writeInt(time);
    }
}
