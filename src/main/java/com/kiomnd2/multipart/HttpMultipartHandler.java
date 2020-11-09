package com.kiomnd2.multipart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostMultipartRequestDecoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;

public class HttpMultipartHandler extends SimpleChannelInboundHandler<HttpObject> {
    private static final HttpDataFactory factory =
            new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

//        System.out.println(msg);
        if (msg instanceof HttpRequest) {

            HttpRequest request = (HttpRequest) msg;
            System.out.println(request.headers());
            HttpPostRequestDecoder httpPostRequestDecoder = new HttpPostRequestDecoder(factory, request);


        }
    }




    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
