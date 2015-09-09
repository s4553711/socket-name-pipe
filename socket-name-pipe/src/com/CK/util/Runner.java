package com.CK.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Runner {
    public static void main(String[] args) {
        System.out.println("GO");
        SocketNamePipe pipe = new SocketNamePipe();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = in.readLine()) != null)
                pipe.push(line);
            in.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
        pipe.closePusher();
    }
}
