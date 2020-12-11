# Advent of Code 2020

Install Clojure by executing `brew install clojure`.

## Day 1

* Execute `clj -X day1/run` to run the program for day 1 with the default tuple size of 2.
* Execute `clj -X day1/run '[:size]' 3` to run the same program with a tuple size of 3.
* There's also a variant that uses a transducer with the same interface: `clj -X day1-transducer/run`.
* A third variant stops as soon as a solution is found: `clj -X day1-stop-asap/run`.

The variant using a transducer could be more efficient than the plain variant in practice because it doesn't create intermediate sequences, but both variants perform the same in this scenario.
The variant that stops as soon as possible performs the same too.
