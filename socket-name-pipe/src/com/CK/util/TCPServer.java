package com.CK.util;

public class TCPServer {

    public static void main(String args[]) {
        System.out.println("TCP Server start");
        TCPSocketServer server = new TCPSocketServer(Integer.valueOf(args[0]));
        server.receive();
        System.out.println("Server shutdown");
    }
}
