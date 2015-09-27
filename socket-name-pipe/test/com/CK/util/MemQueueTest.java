package com.CK.util;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Before;
import org.junit.Test;

public class MemQueueTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    
    
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }
   
    @Test
    public void testPut() {
        MemQueue mem = new MemQueue(new LinkedBlockingQueue<byte[]>());
        byte[] message = {41, 42, 43, 100};
        try {
            Thread t = new Thread(mem);
            t.start();
            mem.put(message);
            t.sleep(1000);
            assertEquals("runnable\n)*+d\nempty\n", outContent.toString());            
            mem.terminate();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
