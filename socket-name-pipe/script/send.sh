#!/bin/bash

rm *.time.log
for i in $(seq 1 1); do
	/usr/bin/time -v -o $i.time.log ~/git/samtools/samtools view -hS /mnt/repo1/backup/bio/alignment/wgEncodeUwRepliSeqBg02esG1bAlnRep1.bam  | java -cp build/libs/socket-name-pipe.jar com.CK.run.SamDispatch 2003 &
	#/usr/bin/time -v -o $i.time.log cat /dev/shm/a.sam  | java -cp build/libs/socket-name-pipe.jar com.CK.run.SamDispatch 2003 &
done
wait
#echo 'stop' > /dev/tcp/127.0.0.1/2003
