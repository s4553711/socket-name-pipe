#!/bin/bash
#
# Usage: bowtie2-executor.sh 45678 45680
#
function fork() {
#	echo $1 "port"
#	echo $2 "port"	
	porta=$1
	portb=$2
	rm ${porta}.log
	echo "Use ${porta} and ${portb} .. ";
	$BT2_HOME/bowtie2 --local -x lambda_virus \
		-1 <(java -cp /home/s4553711/git/socket-name-pipe/socket-name-pipe/bin/src com.CK.util.TCPServer ${porta}) \
		-2 <(java -cp /home/s4553711/git/socket-name-pipe/socket-name-pipe/bin/src com.CK.util.TCPServer ${portb}) \
		-S ${porta}.sam &> ${porta}.log
	echo "end" >> ${porta}.log
}

while [ $# -gt 0 ]
do
	echo $1;
	fork $1 $(($1+1)) &
	shift 1;
done
