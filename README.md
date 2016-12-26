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

Another example for bowtie2
```bash
# Execute two bowtie2 process and wait for the paired-reads
$ $BT2_HOME/bowtie2 --local -x lambda_virus -1 <(java -cp /home/user/git/socket-name-pipe/socket-name-pipe/bin/src com.CK.util.TCPServer 45678) -2 <(java -cp /home/user/git/socket-name-pipe/socket-name-pipe/bin/src com.CK.util.TCPServer 45679) -S eg1.sam
$ BT2_HOME/bowtie2 --local -x lambda_virus -1 <(java -cp /home/user/git/socket-name-pipe/socket-name-pipe/bin/src com.CK.util.TCPServer 45680) -2 <(java -cp /home/user/git/socket-name-pipe/socket-name-pipe/bin/src com.CK.util.TCPServer 45681) -S eg2.sam

# Send reads from another process
$ cat ~/git/bowtie2/example/reads/reads_1.fq |java -cp bin/src com.CK.run.FastqRunner 45678 45680 ; echo 'stopSignal' | java -cp bin/src com.CK.util.Runner 45678 localhost; echo 'stopSignal' | java -cp bin/src com.CK.util.Runner 45680 localhost;
$ cat ~/git/bowtie2/example/reads/reads_2.fq |java -cp bin/src com.CK.run.FastqRunner 45679 45681 ; echo 'stopSignal' | java -cp bin/src com.CK.util.Runner 45679 localhost; echo 'stopSignal' | java -cp bin/src com.CK.util.Runner 45681 localhost;
```

Sam Sender & Receiver
```
# Sender: Read bam file and dispatch reads to different client by chr ID
$ ~/git/samtools/samtools view -hS /mnt/repo1/backup/bio/alignment/wgEncodeUwRepliSeqBg02esG1bAlnRep1.bam  | java -cp build/libs/socket-name-pipe.jar com.CK.run.SamDispatch 2003
# Receiver
$ java -cp ~/workspace/Nsock.jar com.ck.NettyServer 2003  | ~/git/samtools/samtools view -hSb - | ~/git/samtools/samtools sort -o sort.bam
```
