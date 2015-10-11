package com.CK.util;

public class TCPServer {

    public static void main(String args[]) {
        TCPSocketServer server = new TCPSocketServer(Integer.valueOf(args[0]));
        server.receive();
    }
}
