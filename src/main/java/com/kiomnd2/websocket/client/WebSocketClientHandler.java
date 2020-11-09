package com.kiomnd2.websocket.client;

import com.kiomnd2.websocket.server.WebsocketFrameHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {

    private WebSocketClientHandshaker handshaker;
    private ChannelPromise channelPromise;

    public WebSocketClientHandler(WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    public ChannelPromise handHandshakeFuture() {
        return channelPromise;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded :: " +ctx);
        channelPromise = ctx.newPromise();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("active Channel");
        handshaker.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("websocket closed");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        channelPromise.setFailure(cause);
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            // 핸드쉐이크 아직  안됏으면 마무리
            try{
                handshaker.finishHandshake(ctx.channel(), (FullHttpResponse) msg);
                channelPromise.setSuccess();
            }catch (Throwable t) {
                channelPromise.setFailure(t);
            }
            return;
        }

        System.out.println(msg);

        if (msg instanceof TextWebSocketFrame) {
            System.out.println(msg);
        }

    }


}
