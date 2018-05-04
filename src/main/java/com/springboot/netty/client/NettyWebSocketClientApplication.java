package com.springboot.netty.client;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.springboot.netty.client.handlers.HelloClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

@SpringBootApplication
public class NettyWebSocketClientApplication implements CommandLineRunner{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private HelloClientHandler helloClientHandler;
	
	public static void main(String[] args) throws IOException {
//		Properties pp = new Properties(); 
//		pp.load(NettyWebSocketClientApplication.class.getClassLoader().getResourceAsStream("application.properties"));
//		pp.keySet().stream().forEach(x->{System.out.println(x+":"+pp.get(x));});

		logger.info("Start NettyWebSocketClientApplication Application");
		SpringApplication.run(NettyWebSocketClientApplication.class, args);
	}
	@Override
		public void run(String... args) throws Exception {
			logger.info("Run Client Application");
			EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
			try {
				Bootstrap clientBootstrap = new Bootstrap();
				clientBootstrap.group(eventLoopGroup)
								.channel(NioSocketChannel.class)
								.remoteAddress(new InetSocketAddress("127.0.0.1", 9090))
								.handler(helloClientHandler);

				ChannelFuture cf = clientBootstrap.connect().sync();
				cf.channel().closeFuture().sync();
			} catch (Exception e) {
				 logger.error("client exception", e);
			} finally {
				eventLoopGroup.shutdownGracefully().sync();
				logger.info("client stopped");
			}
			
		}
}
