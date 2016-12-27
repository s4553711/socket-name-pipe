package com.CK.run;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.CK.util.TCPNamedPipe;

public class SamDispatch {
	public static void main(String[] args) {

		Map<String, TCPNamedPipe> maps = init_sys();	
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

	public static Map<String, TCPNamedPipe> init_sys() {
		System.out.println("IN");
		int start_port = 4000, i = 0;
		Map<String, TCPNamedPipe> maps = new HashMap<>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(
				new FileReader("/home/s4553711/git/socket-name-pipe/socket-name-pipe/nodes.config.2"));
			String line;
			while((line = br.readLine()) != null) {
				if (line.startsWith("Interval") || line.startsWith("Chr")) {
					continue;
				}
				String[] cols = line.split("\t");
				//mapper.put(cols[0]+"-"+cols[2], cols[3]);
				//port_mapper.put(cols[0]+"-"+cols[2], i+start_port);
				System.out.println(cols[0]+"-"+cols[2]+", "+cols[3]+", "+(i+start_port));
				maps.put(cols[0]+"-"+cols[2], new TCPNamedPipe(cols[3], i+start_port));
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return maps;
	}
}
