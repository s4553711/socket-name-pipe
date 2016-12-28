package com.CK.util.sam;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.io.UnsupportedEncodingException;

public class SamHandler extends ChannelInboundHandlerAdapter {
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf in = (ByteBuf) msg;
		try {
			byte[] bytes = new byte[in.readableBytes()];
			in.readBytes(bytes);
			System.out.print(new String(bytes, "US-ASCII"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}
	
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
