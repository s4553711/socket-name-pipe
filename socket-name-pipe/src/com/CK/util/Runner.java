package com.CK.util;

public class Runner {
	public static void main(String[] args) {
		System.out.println("GO");
		SocketNamePipe pipe = new SocketNamePipe();
		pipe.attach();
	}
}
