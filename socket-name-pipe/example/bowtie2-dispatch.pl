#!/usr/bin/perl
#
# Usage: bowtie2-dispatch.pl read_1.fastq read_2.fastq localhost:45678 localhost:45680
#
use strict;
use warnings;
use utf8;
use Data::Dumper;

my $pair1 = shift;
my $pair2 = shift;
my @portsR1;
my @portsR2;
for my $tmp (@ARGV) {
	my @str = split(/:/, $tmp);
	push(@portsR1, "$str[0]:$str[1]");
	push(@portsR2, "$str[0]:".($str[1]+1));
}

my $pid = 0;
$pid = fork();
if ($pid == 0) {
	fastqReader($pair1, \@portsR1);
	exit;
}

my $pid2 = 0;
$pid2 = fork();
if ($pid2 == 0) {
	fastqReader($pair2, \@portsR2);
	exit;
}

sub fastqReader {
	my ($file, $port) = @_;
	my $ifh = undef;

	my $command = "cat $file |java -cp ../bin/src com.CK.run.FastqRunner ".join(' ', @{$port})."; ";
	for my $i (@{$port}) {
		my @col = split(/:/, $i);
		$command .= "echo 'stopSignal' | java -cp ../bin/src com.CK.util.Runner $col[1] $col[0]; ";
	}
	$command .= " |";
	open ($ifh, $command);
	while (my $line = <$ifh>) {
		#print $line;
	}
	close $ifh;
}
