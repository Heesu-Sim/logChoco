package com.example.leo.logChoco.reader;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import reactor.core.publisher.Sinks;

public class InboundHandler extends ChannelInboundHandlerAdapter {

    private Sinks.Many<String> sink;
    public InboundHandler(Sinks.Many<String> sink) {
        this.sink = sink;
    }

    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object buf) throws Exception {

        ByteBuf in = (ByteBuf) buf;
        String msg = in.toString(CharsetUtil.UTF_8);

        sink.emitNext(msg, Sinks.EmitFailureHandler.FAIL_FAST);
        in.release();

    }
}
