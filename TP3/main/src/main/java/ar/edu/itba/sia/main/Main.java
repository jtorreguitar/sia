package ar.edu.itba.sia.main;

import ar.edu.itba.sia.geneticAlgorithmGps.GeneticAlgorithmGpsEngine;
import ar.edu.itba.sia.interfaces.Chromosome;
import ar.edu.itba.sia.model.Population;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        new EquipmentParser().parse();
        MainConfiguration configuration = new ConfigurationParser().parse();
        List<Chromosome> population = new Population().generateRandomPopulation(configuration.getInitialPopulationSize(),
                                                                                configuration.getCharacterData(),
                                                                                configuration.getRandom());
        GeneticAlgorithmGpsEngine engine = new GeneticAlgorithmGpsEngine(population, configuration);
        engine.solve();
    }
}
