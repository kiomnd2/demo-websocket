package com.kiomnd2.multipart;

import com.kiomnd2.multipart.HttpMultipartHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;

public class HttpMultipartInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpRequestEncoder());
        pipeline.addLast(new HttpContentCompressor());
        pipeline.addLast(new HttpMultipartHandler());

    }
}
