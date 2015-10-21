package com.CK.util;

import java.io.IOException;
import java.io.InputStream;

public class Runner {
    public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage> Runner [port|1234] [domain name|localhost]");
			return;
		}
        TCPNamedPipe pipe = new TCPNamedPipe(args[1], Integer.valueOf(args[0]));
        try {
            int nRead;
            byte[] data = new byte[8192];
            InputStream is = System.in;
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                //pipe.write(data, nRead);
                pipe.push(data, nRead);
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
        pipe.closePusher();
    }
}
