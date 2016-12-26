package com.CK.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.CK.util.TCPNamedPipe;

public class SamDispatch {
	public static void main(String[] args) {
		TCPNamedPipe pipes = new TCPNamedPipe("127.0.0.1", Integer.valueOf(args[0]));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		try {
			while((line = br.readLine()) != null) {
				String[] col = line.split("\t");
				byte[] data = line.concat("\n").getBytes();
				pipes.push(data, data.length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        pipes.closePusher();
	}
}
