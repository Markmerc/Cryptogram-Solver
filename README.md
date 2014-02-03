#Cryptogram-Solver

A scala program that solves short cryptograms one would find in a newspaper
by Mark Mercurio and Wenyi Wu.

The program uses two dictionaries ordered by word frequency to solve the cryptogram. It
attemps to take advantage of the the best aspects of both the small and large dictionary.
The smaller dictionary is attempted first. The smaller dictionary works best with cryptograms
with common short words. The larger dictionary optimizes results by expanding the source of words,
however will sometimes produce solutions with very uncommon words. So it attempts to solve the 
cryptogram using the smaller dictionary first, and if it fails tries with the larger dictionary.

This was part of an assignment from CIT 591 Fall 2013 at the University of Pennsylvania.
The assignment instructions can be found here: http://www.cis.upenn.edu/~matuszek/cit591-2013/Assignments/06-cryptograms.html


