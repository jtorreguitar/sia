package ar.edu.itba.sia.main;

import ar.edu.itba.sia.geneticAlgorithmGps.GeneticAlgorithmGpsEngine;
import ar.edu.itba.sia.geneticAlgorithmGps.Metrics;
import ar.edu.itba.sia.interfaces.Chromosome;
import ar.edu.itba.sia.model.Population;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        new EquipmentParser().parse();
        MainConfiguration configuration = new ConfigurationParser().parse();
        List<Chromosome> population = new Population().generateRandomPopulation(configuration.getInitialPopulationSize(),
                                                                                configuration.getCharacterData(),
                                                                                configuration.getRandom());
        GeneticAlgorithmGpsEngine engine = new GeneticAlgorithmGpsEngine(population, configuration);
        Metrics metrics = engine.solve();
        writeToCsv(metrics);
    }

    private static void writeToCsv(Metrics metrics) {
        File file = new File("metrics.csv");
        try (FileWriter fw = new FileWriter(file); BufferedWriter br = new BufferedWriter(fw)){
            br.write("generation,repeats,mean,best\n");
            for(int i = 0; i < metrics.getMeanFitness().size(); i++) {
                br.write(String.format("%d,%d,%.3f,%.3f\n", i, metrics.getRepeatIndividuals().get(i),
                                                            metrics.getMeanFitness().get(i),
                                                            metrics.getFittestIndividualForEachGeneration().get(i)));
            }
        }
        catch (IOException e) {

        }
    }
}
