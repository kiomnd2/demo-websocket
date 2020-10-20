package com.kiomnd2.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;


public class WebsocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        if (frame instanceof TextWebSocketFrame) {
            String request = ((TextWebSocketFrame)frame).text();
            ctx.channel().writeAndFlush(new TextWebSocketFrame(request));
        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("connected by " + ctx.channel().remoteAddress());
        ConnectionManager instance = ConnectionManager.getInstance();


        instance.addContext(ctx);

        // 현재 사용자
        System.out.println("curUser : "+instance.getConnectedUser());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("closed " + ctx.channel().remoteAddress());
        ConnectionManager instance = ConnectionManager.getInstance();
        instance.removeContext(ctx.channel().remoteAddress().toString());

        // 현재 사용자
        System.out.println("curUser : "+instance.getConnectedUser());
    }
}
