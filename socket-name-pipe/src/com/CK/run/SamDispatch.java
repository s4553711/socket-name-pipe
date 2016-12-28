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
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		try {
			while((line = br.readLine()) != null) {
				String[] col = line.split("\t");
				byte[] data = line.concat("\n").getBytes();
				maps.get(col[2].replace("chr", "")+"-"+(int) Math.floor(Integer.valueOf(col[3])/10000000))
					.push(data, data.length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String p: maps.keySet()) {
			maps.get(p).closePusher();
		}
	}

	public static Map<String, TCPNamedPipe> init_sys() {
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
