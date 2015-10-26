#!/usr/bin/perl
use strict;
use warnings;
use utf8;
use Data::Dumper;

my $port = shift;
my $pair1 = shift;
my $pair2 = shift;

my $pid = 0;
$pid = fork();
if ($pid == 0) {
	fastqReader($pair1, $port);
	exit;
}

my $pid2 = 0;
$pid2 = fork();
if ($pid2 == 0) {
	fastqReader($pair2, $port+1);
	exit;
}

sub fastqReader {
	my ($file, $port) = @_;
	my $ifh = undef;
	my $command = "cat $file | java -cp bin/src com.CK.util.Runner $port localhost".
		";echo -n 'stopSignal' | java -cp bin/src com.CK.util.Runner $port localhost |";
	open ($ifh, $command);
	while (my $line = <$ifh>) {
		print $line;
	}
	close $ifh;
}
