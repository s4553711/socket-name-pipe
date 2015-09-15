package com.CK.util;

public class UDPServer {
    public static void main(String args[]) throws Exception {
        System.out.println("Server start");
        SocketServer server = new SocketServer(45678);
        server.receive();
        System.out.println("Server shutdown");
    }
}
