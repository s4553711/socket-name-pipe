package com.CK.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class SocketNamePipe {
    private DatagramSocket socket;
    private int port = 45678;
    private String host = "localhost";
    
    public SocketNamePipe(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
	
	public void write(byte[] data, int nRead) {
        try {
            DatagramPacket sendPacket = new DatagramPacket(data, nRead, InetAddress.getByName(host), port);
            socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }	    
	}

	public void closePusher() {
	    socket.close();
	}
}
