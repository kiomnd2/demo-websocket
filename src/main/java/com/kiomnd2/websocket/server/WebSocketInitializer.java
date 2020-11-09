package com.kiomnd2.websocket.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;

public class WebSocketInitializer extends ChannelInitializer<SocketChannel> {

    private final String WEBSOCKET_PATH = "/websocket";

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline cp = socketChannel.pipeline();
        cp.addLast(new HttpServerCodec());
        cp.addLast(new WebSocketServerCompressionHandler());
        cp.addLast(new HttpObjectAggregator(65536));
        cp.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));
        cp.addLast(new WebsocketFrameHandler());
        cp.addLast(new HttpHandler());
    }
}
