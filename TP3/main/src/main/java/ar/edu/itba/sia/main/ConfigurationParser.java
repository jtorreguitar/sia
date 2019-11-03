package ar.edu.itba.sia.main;

import ar.edu.itba.sia.interfaces.Configuration;
import ar.edu.itba.sia.interfaces.StoppingData;
import ar.edu.itba.sia.interfaces.enums.*;
import ar.edu.itba.sia.model.CharacterData;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/* package */ class ConfigurationParser {

    private static final String filePath = "config.json";

    public MainConfiguration parse() {
        try {
            final FileReader reader = new FileReader(filePath);
            final JsonParser parser = new JsonParser();
            final JsonConfiguration jsonConfiguration = new Gson().fromJson(parser.parse(reader), JsonConfiguration.class);
            MainConfiguration mainConfiguration = new MainConfiguration();
            setVariables(mainConfiguration, jsonConfiguration);
            return mainConfiguration;
        }
        catch (IOException e){
            throw new IllegalArgumentException("configuration file not found");
        }
    }

    private void setVariables(MainConfiguration mainConfiguration, JsonConfiguration jsonConfiguration) {
        mainConfiguration.setSelectors(Arrays.stream(jsonConfiguration.selectors).map(s -> s.method).collect(Collectors.toList()));
        mainConfiguration.setSelectionPercentages(Arrays.stream(jsonConfiguration.selectors).map(s -> s.percentage).collect(Collectors.toList()));
        mainConfiguration.setCrosser(jsonConfiguration.crosser);
        mainConfiguration.setMutationRate(jsonConfiguration.mutationRate);
        mainConfiguration.setMutator(jsonConfiguration.mutator);
        mainConfiguration.setReplacer(jsonConfiguration.replacer);
        mainConfiguration.setReplacementPercentages(Arrays.stream(jsonConfiguration.replacerSelectors).map(s -> s.percentage).collect(Collectors.toList()));
        mainConfiguration.setReplacementSelectors(Arrays.stream(jsonConfiguration.replacerSelectors).map(s -> s.method).collect(Collectors.toList()));
        mainConfiguration.setRandom(parseRandom(jsonConfiguration));
        mainConfiguration.setStopConditionFunction(parseStopConditionFunction(jsonConfiguration.stopConditions, jsonConfiguration.stoppingData));
        mainConfiguration.setInitialPopulationSize(jsonConfiguration.initialPopulationSize);
        mainConfiguration.setCharacterData(jsonConfiguration.characterData);
    }

    private Random parseRandom(JsonConfiguration jsonConfiguration) {
        return jsonConfiguration.randomSeed == -1 ? new Random() : new Random(jsonConfiguration.randomSeed);
    }

    private StopConditionFunction parseStopConditionFunction(final StopCondition[] stopConditions, final StoppingData stoppingData) {
        return s -> Arrays.stream(stopConditions)
                        .map(sc -> parseStopConditionFunctions(sc, stoppingData).stopConditionIsMet(s))
                        .reduce(false, (accum, val) -> accum || val);
    }

    private StopConditionFunction parseStopConditionFunctions(StopCondition stopCondition, StoppingData stoppingData) {
        switch (stopCondition) {
            case GENERATIONS: return s -> s.getGenerations() > stoppingData.getGenerations();
            case OPTIMUM: return s -> s.getBestAptitude().compareTo(stoppingData.getBestAptitude()) > 0;
            case CONTENT: return s -> s.getBestAptitudeIncrease().compareTo(stoppingData.getBestAptitudeIncrease()) < 0;
            case STRUCTURE: return s -> s.getRepeatIndividuals().compareTo(stoppingData.getRepeatIndividuals()) < 0;
            default: throw new IllegalArgumentException("invalid stop condition");
        }
    }

    private class JsonConfiguration {
        private SelectionMethodAndPercentage[] selectors;
        private SelectionMethodAndPercentage[] replacerSelectors;
        private ReplacerType replacer;
        private CrosserType crosser;
        private MutatorType mutator;
        private double mutationRate;
        private StopCondition[] stopConditions;
        private int participantsPerMatchInTournament;
        private long randomSeed;
        private JsonStoppingData stoppingData;
        private int initialPopulationSize;
        private CharacterData characterData;
    }

    private class SelectionMethodAndPercentage {
        private SelectorType method;
        private Double percentage;
    }

    private class JsonStoppingData implements StoppingData {
        private Integer generations;
        private Double bestAptitude;
        private Double bestAptitudeIncrease;
        private Integer repeatIndividuals;

        @Override
        public Integer getGenerations() {
            return generations;
        }

        @Override
        public Double getBestAptitude() {
            return bestAptitude;
        }

        @Override
        public Double getBestAptitudeIncrease() {
            return bestAptitudeIncrease;
        }

        @Override
        public Integer getRepeatIndividuals() {
            return repeatIndividuals;
        }
    }
}
