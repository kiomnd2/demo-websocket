package com.kiomnd2.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.mqtt.MqttMessageBuilders;

import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {

    private static ConnectionManager connectionManager = null;
    private final ConcurrentHashMap<String, ChannelHandlerContext> map = new ConcurrentHashMap<String, ChannelHandlerContext>();

    public static ConnectionManager getInstance() {
        if (connectionManager == null) {
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

    protected void removeContext(String key) {
        map.remove(key);
    }

    protected void addContext(ChannelHandlerContext ctx) {
        map.putIfAbsent(ctx.channel().remoteAddress().toString(), ctx);
    }

    public void pushingMessage(final String message) {
        this.map.forEach( (k,v) -> {
            // 웹소캣으로 보낼거다
            WebSocketFrame webSocketFrame = new TextWebSocketFrame(message);
            v.fireChannelActive(); // 채널 활성화
            v.channel().writeAndFlush(webSocketFrame);
        });
    }

    public String getConnectedUser() {
        return map.keySet().toString();
    }

}
