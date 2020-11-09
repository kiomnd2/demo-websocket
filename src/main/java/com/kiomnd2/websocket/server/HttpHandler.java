package com.kiomnd2.websocket.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.Map;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    private static final HttpDataFactory factory =
            new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); // Disk if size exceed
    private HttpData partialContent;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        if (request.method() != HttpMethod.POST) {
            System.out.println("Reqeust is not post");
            ctx.channel().close();
        }



        ConnectionManager con = ConnectionManager.getInstance();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = objectMapper.readValue(request.content().toString(Charset.defaultCharset()), Map.class);

        // 메시지 푸싱
        con.pushingMessage(map.get("message"));

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK, Unpooled.copiedBuffer("success", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application-json; charset=UTF-8");
        response.headers().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private void readHttpDataChunkByChunk(HttpPostRequestDecoder decoder) {
        if (decoder.hasNext()) {
            InterfaceHttpData data = decoder.next();
            if (data != null) {
                // check if current HttpData is a FileUpload and previously set as partial
                if (partialContent == data) {
                    System.out.println(" 100% (FinalSize: " + partialContent.length() + ")");
                    partialContent = null;
                }
                // new value

            }
        }
    }

}
