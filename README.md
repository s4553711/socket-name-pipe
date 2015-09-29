[![Coverage Status](https://coveralls.io/repos/s4553711/socket-name-pipe/badge.svg)](https://coveralls.io/r/s4553711/socket-name-pipe)  
[![Build Status](https://travis-ci.org/s4553711/socket-name-pipe.svg?branch=master)](https://travis-ci.org/s4553711/socket-name-pipe) 

Read fsatq input then pass to Runner
```
# Compile
$ javac -cp src/ src/com/CK/util/Runner.java 
# Run
$ zcat ~/upload/A_10w_R1_40.fastq.gz |  java -cp src com.CK.util.Runner
```

A example to send data to TCPServer
```
# Open TCP server and wait for the data
$ java -cp src com.CK.util.TCPServer

# Start send data from zcat
$ zcat ~/upload/A_10w_R1_40.fastq.gz | head -n 10 | java -cp src com.CK.util.Runner
```
