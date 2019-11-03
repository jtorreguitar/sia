package ar.edu.itba.sia.main;

import ar.edu.itba.sia.interfaces.Configuration;
import ar.edu.itba.sia.interfaces.StoppingData;
import ar.edu.itba.sia.interfaces.enums.*;
import ar.edu.itba.sia.model.CharacterData;

import java.util.List;
import java.util.Random;

public class MainConfiguration implements Configuration {

    private List<SelectorType> selectors;
    private List<Integer> selectionQuantities;
    private List<Integer> selectionCompetitors;
    private CrosserType crosser;
    private double mutationRate;
    private MutatorType mutator;
    private ReplacerType replacer;
    private List<SelectorType> replacementSelectors;
    private List<Integer> replacementQuantities;
    private List<Integer> replacementCompetitors;
    private Random random;
    private StopConditionFunction stopConditionFunction;
    private int initialPopulationSize;
    private CharacterData characterData;

    @Override
    public List<SelectorType> getSelectors() {
        return selectors;
    }

    @Override
    public List<Integer> getSelectionQuantities() {
        return selectionQuantities;
    }

    @Override
    public List<Integer> getSelectionCompetitors() {
        return selectionCompetitors;
    }

    @Override
    public CrosserType getCrosser() {
        return crosser;
    }

    @Override
    public double getMutationRate() {
        return mutationRate;
    }

    @Override
    public MutatorType getMutator() {
        return mutator;
    }

    @Override
    public ReplacerType getReplacer() {
        return replacer;
    }

    @Override
    public List<SelectorType> getReplacementSelectors() {
        return replacementSelectors;
    }

    @Override
    public List<Integer> getReplacementQuantities() {
        return replacementQuantities;
    }

    @Override
    public List<Integer> getReplacementCompetitors() {
        return replacementCompetitors;
    }

    @Override
    public boolean stopConditionIsMet(final StoppingData stoppingData) {
        return stopConditionFunction.stopConditionIsMet(stoppingData);
    }

    @Override
    public Random getRandom() {
        return random;
    }

    public int getInitialPopulationSize() {
        return initialPopulationSize;
    }

    public CharacterData getCharacterData() {
        return characterData;
    }

    /* package */ void setSelectors(List<SelectorType> selectors) {
        this.selectors = selectors;
    }

    /* package */ void setSelectionQuantities(List<Integer> selectionQuantities) {
        this.selectionQuantities = selectionQuantities;
    }

    /* package */ void setSelectionCompetitors(List<Integer> selectionCompetitors){
        this.selectionCompetitors = selectionCompetitors;
    }

    /* package */ void setCrosser(CrosserType crosser) {
        this.crosser = crosser;
    }

    /* package */ void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    /* package */ void setMutator(MutatorType mutator) {
        this.mutator = mutator;
    }

    /* package */ void setReplacer(ReplacerType replacer) {
        this.replacer = replacer;
    }

    /* package */ void setReplacementSelectors(List<SelectorType> replacementSelectors) {
        this.replacementSelectors = replacementSelectors;
    }

    /* package */ void setReplacementQuantities(List<Integer> replacementQuantities) {
        this.replacementQuantities = replacementQuantities;
    }

    /* package */ void setReplacementCompetitors(List<Integer> replacementCompetitors){
        this.replacementCompetitors = replacementCompetitors;
    }

    /* package */ void setRandom(Random random) {
        this.random = random;
    }

    /* package */ void setStopConditionFunction(StopConditionFunction stopConditionFunction) {
        this.stopConditionFunction = stopConditionFunction;
    }

    public void setInitialPopulationSize(int initialPopulationSize) {
        this.initialPopulationSize = initialPopulationSize;
    }

    public void setCharacterData(CharacterData characterData) {
        this.characterData = characterData;
    }
}
