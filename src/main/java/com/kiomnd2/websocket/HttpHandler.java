package com.kiomnd2.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.Map;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        ConnectionManager con = ConnectionManager.getInstance();
        ObjectMapper objectMapper = new ObjectMapper();
        if (request.method() != HttpMethod.POST) {
            System.out.println("Reqeust is not post");
            ctx.channel().close();
        }
        Map<String, String> map = objectMapper.readValue(request.content().toString(Charset.defaultCharset()), Map.class);
        // 메시지 푸싱
        con.pushingMessage(map.get("message"));

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK, Unpooled.copiedBuffer("success", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application-json; charset=UTF-8");
        response.headers().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");

        ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        ctx.close();
    }


}
