#!/bin/bash
#
# Usage: bowtie2-executor.pl read_R1.fasta reads_R2.fasta 45678 45680
#
function fork() {
	porta=$1
	portb=$2
	echo "Use ${porta} and ${portb} .. ";
	$BT2_HOME/bowtie2 --local -x lambda_virus \
		-1 <(java -cp /home/s4553711/git/socket-name-pipe/socket-name-pipe/bin/src com.CK.util.TCPServer ${porta}) \
		-2 <(java -cp /home/s4553711/git/socket-name-pipe/socket-name-pipe/bin/src com.CK.util.TCPServer ${portb}) \
		-S ${porta}.sam &> ${porta}.log
}

port1=$1
port2=$2

fork $port1 $(($port1+1)) &
fork $port2 $(($port2+1)) &
