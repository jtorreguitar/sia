# General problem solver

## Summary
an implementation of a general problem solver, that is, an algorithm that given a problem which satisfies the interfaces defined in the interfaces module will always find a solution if it exists and the problem is correctly modelled.

The program can change the algorithm used based on user input. The possible algorithms are: BFS, DFS, IDDFS, A* and GREEDY.

## Running the project
running run.sh will start the project. This script takes three arguments:
1 - the algorithm to use. Options: BFS|DFS|IDDFS|ASTAR|GREEDY
2 - the absolute path to the file where the simple squares board that wants to be solved is.
3 - an optional argument representing the heuristic that wants to be used in case an informed search is being used. Options: InPlace|Distance|WeightedDistance.
