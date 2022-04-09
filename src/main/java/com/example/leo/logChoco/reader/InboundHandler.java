package com.example.leo.logChoco.reader;

import com.example.leo.logChoco.entity.InboundLog;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import reactor.core.publisher.Sinks;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class InboundHandler extends ChannelInboundHandlerAdapter {

    private Sinks.Many<InboundLog> sink;
    public InboundHandler(Sinks.Many<InboundLog> sink) {
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

        InetSocketAddress sock = (InetSocketAddress) ctx.channel().remoteAddress();

        String addr = sock.getAddress().getHostAddress();

        ByteBuf in = (ByteBuf) buf;
        String log = in.toString(CharsetUtil.UTF_8);

        InboundLog inboundLog = new InboundLog(addr, log);

        sink.emitNext(inboundLog, Sinks.EmitFailureHandler.FAIL_FAST);
        in.release();

    }
}
