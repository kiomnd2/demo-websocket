package com.kiomnd2.multipartClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.util.internal.SocketUtils;

import java.io.File;
import java.net.URI;

public class Main {


    private final static URI uri = URI.create("http://localhost:10000/multipart");


    public static void main(String[] args) throws InterruptedException, HttpPostRequestEncoder.ErrorDataEncoderException {
        EventLoopGroup g = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();


        b.group(g)
                .channel(NioSocketChannel.class)
                .handler(new MultipartClientInitializer());

        HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);


        Channel channel = b.connect(uri.getHost(), uri.getPort()).sync().channel();



        File file = new File("/Users/kiomnd2/Documents/Iworkspace/websocket/src/main/java/com/kiomnd2/test.txt");

        // Request
        DefaultHttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uri.toASCIIString());

        // Header
        HttpHeaders headers = request.headers();

        //Encoder\
        // data
        HttpPostRequestEncoder bodyRequestEncoder = new HttpPostRequestEncoder(factory, request, true);  // true => multipart`







    }

}
