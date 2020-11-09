package com.kiomnd2.websocket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class Main {

    private static URI url = URI.create("ws://localhost:9999/websocket");

    public static void main(String[] args) {


        EventLoopGroup g = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();

        try{

            WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(url, WebSocketVersion.V13
                    , null, true, new DefaultHttpHeaders());

            WebSocketClientHandler webSocketClientHandler = new WebSocketClientHandler(handshaker);
            b.group(g)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new HttpClientCodec());
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            pipeline.addLast(webSocketClientHandler);
                        }
                    });

            Channel channel = b.connect(url.getHost(), url.getPort()).sync().channel();
            System.out.println(webSocketClientHandler.handHandshakeFuture().sync());

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println(webSocketClientHandler.handHandshakeFuture().isSuccess());
                String msg = reader.readLine();
                TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(msg);
                channel.writeAndFlush(textWebSocketFrame);
                System.out.println(msg);
            }

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
            g.shutdownGracefully();
        }

    }
}
