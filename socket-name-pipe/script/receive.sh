#!/bin/bash
file=$1
start_port=$2
action=$3

if [ -z $file -o -z $start_port ]; then
	echo 'Port or input cannot be empty'
	exit
fi

inc=0
while read line; do
	if [[ ! "$line" =~ ^Interval ]] && [[ ! "$line" =~ ^Chr ]]; then
		host=$(echo $line|awk '{print $4}')
		chr=$(echo $line|awk '{print $1}')
		indx=$(echo $line|awk '{print $3}')
		echo "java -cp build/libs/socket-name-pipe.jar com.CK.run.SamReceiver $host " $((start_port+inc))
		java -cp build/libs/socket-name-pipe.jar com.CK.run.SamReceiver $((start_port+inc)) > fifo/"${chr}_${indx}.log"&
		inc=$((inc+1))
	fi
done<$file
