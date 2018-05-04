package com.springboot.netty.client.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

@Component
public class HelloClientHandler extends SimpleChannelInboundHandler<ByteBuf>{
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		String name = "joye";
		logger.info("The client send【{}】to server.", name);
		ctx.writeAndFlush(Unpooled.copiedBuffer(name, CharsetUtil.UTF_8));
	}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf inBuf) throws Exception {
		logger.info("Client received:【{}】", inBuf.toString(CharsetUtil.UTF_8));
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
