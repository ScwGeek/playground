package com.scw.playground.netty.tutorial.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * Created by Shi Chengwei on 19/02/2019.
 */
@Slf4j
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        ByteBuf in = (ByteBuf) msg;
        String s = in.toString(Charset.defaultCharset());
        log.info(s);

        ctx.writeAndFlush(msg);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        log.info("ChannelHandler caught exception: ", cause);
        ctx.close();

    }


}
