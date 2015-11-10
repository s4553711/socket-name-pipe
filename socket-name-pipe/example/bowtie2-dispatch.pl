#!/usr/bin/perl
#
# Usage: bowtie2-dispatch.pl read_1.fastq read_2.fastq 45678 45680
#
use strict;
use warnings;
use utf8;
use Data::Dumper;

my $pair1 = shift;
my $pair2 = shift;
my @ports;
for my $tmp (@ARGV) {
	push(@ports, $tmp);
}

my $pid = 0;
$pid = fork();
if ($pid == 0) {
	fastqReader($pair1, \@ports);
	exit;
}

my @ports2 = map {$_ + 1} @ports;
my $pid2 = 0;
$pid2 = fork();
if ($pid2 == 0) {
	fastqReader($pair2, \@ports2);
	exit;
}

sub fastqReader {
	my ($file, $port) = @_;
	my $ifh = undef;
	my $ports = "";
	map { $ports .= "localhost:$_ " } @{$port};

	my $command = "cat $file |java -cp ../bin/src com.CK.run.FastqRunner $ports; ";
	for my $i (@{$port}) {
		$command .= "echo 'stopSignal' | java -cp ../bin/src com.CK.util.Runner $i localhost; ";
	}
	$command .= " |";
	open ($ifh, $command);
	while (my $line = <$ifh>) {
		#print $line;
	}
	close $ifh;
}
