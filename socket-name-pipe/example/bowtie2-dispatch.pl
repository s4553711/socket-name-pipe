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
my @portsR1;
my @portsR2;
for my $tmp (@ARGV) {
	my @str = split(/:/, $tmp);
	push(@portsR1, "$str[0]:$str[1]");
	push(@portsR2, "$str[0]:".($str[1]+1));
}

print Dumper(@portsR1);
print Dumper(@portsR2);

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
	my $ports = join(" ",@{$port});

	my $command = "cat $file |java -cp ../bin/src com.CK.run.FastqRunner $ports; ";
	for my $i (@{$port}) {
		$command .= "echo 'stopSignal' | java -cp ../bin/src com.CK.util.Runner $i localhost; ";
	}
	$command .= " |";
	print ">> $command\n";
	open ($ifh, $command);
	while (my $line = <$ifh>) {
		#print $line;
	}
	close $ifh;
}
