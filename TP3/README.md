# Joe, Tomás and Sofía's wonderful genetic algorithm engine

The following is an implementation of a genetic algorithm engine. The core of it is in the module GeneticAlogirthmGps which uses the Chromosome interface to communicate with the population whose fitness it is trying to meximize. The idea is that a population must provide the engine with a way to cross, mutate and determine which individuals have a better fitness. This is done through the cross, mutate and getAptitude methods in the chromosome class. The chromosome must provide an index based representation of its mutable/crossable attributes.

## Implementation of the problem

The particular problem used as a particular implementation of the Chromosome interface was one where one must optimize the attack and defence attributes of video game characters which themselves vary with the equipment the character is using. This equipment is read from a file called "testdata" in the project root. After finishing, the engine will return a metrics object which is stored into a file called metrics.tsv, also in the project root.

## Configuring the engine

an example configuration would be:

```json
{
  "selectors": [
    {
      "method": "ELITE",
      "quantity": 6,
      "competitors": 2
    },
    {
      "method": "UNIVERSAL",
      "quantity": 14,
      "competitors": 2
    }
  ],
  "replacerSelectors": [
    {
      "method": "ELITE",
      "quantity": 5,
      "competitors": 2
    },
    {
      "method": "ELITE",
      "quantity": 5,
      "competitors": 2
    }
  ],
  "replacer": "FULL_REPLACEMENT",
  "crosser": "SINGLE_POINT",
  "mutator": "MULTI_GENE",
  "mutationRate": 0.5,
  "mutationRateChangeRate": 0.01,
  "initialPopulationSize": 20,
  "randomSeed": -1,
  "stopConditions": [
    "GENERATIONS",
    "OPTIMUM"
  ],
  "stoppingData": {
    "generations": 30000,
    "bestAptitude": 50,
    "bestAptitudeIncrease": 0,
    "repeatIndividuals": 0
  },
  "characterData": {
    "attackModifier": 0.7,
    "defenseModifier": 0.3,
    "strengthModifier": 0.8,
    "agilityModifier": 1.2,
    "wisdomModifier": 1.1,
    "enduranceModifier": 1,
    "healthModifier": 0.8
  }
}
```

### Selectors
An array of selection methods. from the original population an ammount of "quantity" will be taken from it for each selector. The "method" field can be any of: ELITE, ROULETTE, UNIVERSAL, BOLTZMANN, PTOURNAMENT, DTOURNAMENT, RANKING. Finally, the "competitors" field is used only for the tournament methods and determines how many participants there will be per match.

### Replacer selectors
Selectors that operate under the same rules as the previous ones. They are used in the replacement of the previous population to determine which individuals will move forward to the next generation.

### Replacer
Can be any of: FULL_REPLACEMENT, SECOND, THIRD.

### Crosser
Can be any of: SINGLE_POINT, TWO_POINT, UNIFORM, ANNULAR.

### Mutation
The mutator can be either MULTI_GENE OR SINGLE_GENE. The mutation rate determines the odd of a gene or chromosome mutating. The mutation rate change rate will determine how fast the mutation rate will change. If one does not want the mutation rate to change at all this value can be set to cero. The equation that determines the mutation change is given by: initial_mutation_rate*e^-(mutationRateChangeRate*generation)

### Stop conditions
They determine when the engine will cease. It will halt when any of the conditions in the array is met. These can be OPTIMUM, GENERATIONS, STRUCTURE, CONTENT. The stoppingData object determines the particular values which mark the end of execution. For GENERATIONS once the number of generations reaches stoppingData.generations execution will stop. For OPTIMUM once the fitness of the fittest individual for a given generation reaches bestAptitude execution will stop. For CONTENT once the fitness starts increasing by less than bestAptitudeIncrease execution will stop. Finally, for STRUCTURE, once generations start repeating individuals by a greater nuber than repeatIndividuals execution will stop.

### Character data
Determines the modifiers for the many attributes characters can have in the particular implementation of chromosome given.
