package com.CK.run;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.CK.util.sam.SamHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

public class SamReceiver {
	
    private int port;
    private List<Channel> bindingChannels = new LinkedList<>();

    public SamReceiver(int port) {
        this.port = port;
    }

    synchronized private void registerChannel(Channel channel) {
    	bindingChannels.add(channel);
    }

	synchronized public void closeSocket() {
		for (Channel channel : bindingChannels) {
			try {
				channel.close().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

    public void run() throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                	 ch.config().setRecvByteBufAllocator(new FixedRecvByteBufAllocator(8*1024*1024));
                     ch.pipeline().addLast(
                    		 new LineBasedFrameDecoder(8192),
                    		 new SamHandler());
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128)
             .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)

            registerChannel(f.channel());
            System.err.println("Wait for connection");
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
        	System.err.println("EventLoopGroup closed");
        	workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

	private static void registerInterrupt(final SamReceiver server) {
    	Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
            	server.closeSocket();
            	System.err.println("Shutdown!");
            }
        });
    }

    private static void readHeader() {
    	BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("header.sam"));
			String line;
			while((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }

	public static void main(String args[]) throws Exception {
		readHeader();
		SamReceiver server = new SamReceiver(Integer.valueOf(args[0]));
		registerInterrupt(server);
		server.run();
	}
}
