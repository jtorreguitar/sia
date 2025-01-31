package ar.edu.itba.sia.main;

import ar.edu.itba.sia.geneticAlgorithmGps.GeneticAlgorithmGpsEngine;
import ar.edu.itba.sia.geneticAlgorithmGps.Metrics;
import ar.edu.itba.sia.interfaces.Chromosome;
import ar.edu.itba.sia.model.Population;

import java.io.*;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        new EquipmentParser().parse(args[0]);
        MainConfiguration configuration = new ConfigurationParser().parse(args[0]);
        List<Chromosome> population = new Population().generateRandomPopulation(configuration.getInitialPopulationSize(),
                                                                                configuration.getCharacterData(),
                                                                                configuration.getRandom());
        GeneticAlgorithmGpsEngine engine = new GeneticAlgorithmGpsEngine(population, configuration);
        Metrics metrics = engine.solve();
        writeToTsv(metrics, args[0]);
    }

    private static void writeToTsv(Metrics metrics, String path) {
        File file = new File(path + "metrics.tsv");
        try (FileWriter fw = new FileWriter(file); BufferedWriter br = new BufferedWriter(fw)){
            br.write("generation\trepeats\tmean\tbest\tmutation rate\n");
            for(int i = 0; i < metrics.getMeanFitness().size(); i++) {
                br.write(String.format(Locale.forLanguageTag("es-AR"), "%d\t%d\t%.3f\t%.3f\t%.5f\n",
                                                                    i,
                                                                    metrics.getRepeatIndividuals().get(i),
                                                                    metrics.getMeanFitness().get(i),
                                                                    metrics.getFittestIndividualForEachGeneration().get(i),
                                                                    metrics.getMutationRates().get(i)));
            }
        }
        catch (IOException e) {

        }
    }
}
