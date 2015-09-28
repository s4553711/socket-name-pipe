package com.CK.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileBasedPipe {
    private String pipeName;
    
    public FileBasedPipe(String file) {
        try {
            pipeName = file;
            Runtime.getRuntime().exec("mkfifo "+pipeName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void put(byte[] realPack) throws InterruptedException {
        try {
            BufferedOutputStream pipe = new BufferedOutputStream(new FileOutputStream("/tmp/"+pipeName));
            pipe.write(realPack);
            pipe.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void terminate() {
    }

}
