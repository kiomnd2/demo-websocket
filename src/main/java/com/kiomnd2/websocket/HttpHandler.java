package com.kiomnd2.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpRequest;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpMessage fullHttpMessage) throws Exception {
        ConnectionManager con = ConnectionManager.getInstance();

        HttpRequest request = null;
        System.out.println("????");

        // HttpRequest 일 때
        if (fullHttpMessage instanceof HttpRequest) {
            request = (HttpRequest) fullHttpMessage;
        }



        con.pushingMessage(fullHttpMessage.toString());
    }
}
