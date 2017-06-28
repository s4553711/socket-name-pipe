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

		Map<String, TCPNamedPipe> maps = init_sys(args[0]);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int Interval = Integer.valueOf(args[1]);
		String line, chrI = "";
		int index = -1;
		try {
			TCPNamedPipe key;
			while((line = br.readLine()) != null) {
				if (!line.startsWith("@")) {
					String[] col = line.split("\t");
					byte[] data = line.concat("\n").getBytes();
					index = (int)Math.floor(Integer.valueOf(col[3])/Interval);
					chrI = col[2].replace("chr", "");
					//System.out.println("send > "+chrI+"-"+index+"("+col[2]+","+col[3]+")");
					key = maps.get(chrI+"-"+index);
					if (key == null) {
						maps.get("M-0").push(data, data.length);
					} else {
						key.push(data, data.length);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Error > "+chrI+"-"+index);
			e.printStackTrace();
		}
		for(String p: maps.keySet()) {
			maps.get(p).closePusher();
		}
	}

	public static Map<String, TCPNamedPipe> init_sys(String config) {
		int start_port = 4000, i = 0;
		Map<String, TCPNamedPipe> maps = new HashMap<>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(config));
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
