package com.CK.util;

import java.io.IOException;
import java.io.InputStream;

public class Runner {
    public static void main(String[] args) {
        SocketNamePipe pipe = new SocketNamePipe("localhost", 45678);
        try {
            int nRead;
            byte[] data = new byte[16384];
            InputStream is = System.in;
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                pipe.write(data, nRead);
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
        pipe.closePusher();
    }
}
