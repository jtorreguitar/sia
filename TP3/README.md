# Joe, Tomás and Sofía's wonderful genetic algorithm engine

The following is an implementation of a genetic algorithm engine. The core of it is in the module GeneticAlogirthmGps which uses the Chromosome interface to communicate with the population whose fitness it is trying to meximize. The idea is that a population must provide the engine with a way to cross, mutate and determine which individuals have a better fitness. This is done through the cross, mutate and getAptitude methods in the chromosome class. The chromosome must provide an index based representation of its mutable/crossable attributes.

## Implementation of the problem

The particular problem used as a particular implementation of the Chromosome interface was one where one must optimize the attack and defence attributes of video game characters which themselves vary with the equipment the character is using. This equipment is read from a file called "testdata" in the project root. After finishing, the engine will return a metrics object which is stored into a file called metrics.tsv, also in the project root.

## Configuring the engine


