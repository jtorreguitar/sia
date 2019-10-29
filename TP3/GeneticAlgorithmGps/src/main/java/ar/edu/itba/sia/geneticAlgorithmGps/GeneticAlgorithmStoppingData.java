package ar.edu.itba.sia.geneticAlgorithmGps;

import ar.edu.itba.sia.interfaces.StoppingData;

public class GeneticAlgorithmStoppingData implements StoppingData {

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

    public void setGenerations(Integer generations) {
        this.generations = generations;
    }

    public void setBestAptitude(Double bestAptitude) {
        this.bestAptitude = bestAptitude;
    }

    public void setBestAptitudeIncrease(Double bestAptitudeIncrease) {
        this.bestAptitudeIncrease = bestAptitudeIncrease;
    }

    public void setRepeatIndividuals(Integer repeatIndividuals) {
        this.repeatIndividuals = repeatIndividuals;
    }
}
