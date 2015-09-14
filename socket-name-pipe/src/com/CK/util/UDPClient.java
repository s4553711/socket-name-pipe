package com.CK.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPClient {
    public static void main(String args[]) throws Exception {
        try {
            String serverHostname = new String("127.0.0.1");
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            DatagramSocket clientSocket = new DatagramSocket();

            InetAddress IPAddress = InetAddress.getByName(serverHostname);
            System.out.println("Attemping to connect to " + IPAddress + ") via UDP port 45678");

            byte[] sendData = new byte[16384];

            String sentence;
            while((sentence = inFromUser.readLine()) != null) {
                sendData = sentence.getBytes();
                System.out.println("Sending data to " + sendData.length + " bytes to server.");
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 45678);
                clientSocket.send(sendPacket);
            }
            clientSocket.close();
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
