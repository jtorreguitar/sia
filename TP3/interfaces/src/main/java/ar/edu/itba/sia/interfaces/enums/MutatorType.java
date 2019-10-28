package ar.edu.itba.sia.interfaces.enums;

import ar.edu.itba.sia.geneticAlgorithmGps.ConfigurationParser;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.mutators.MultiGeneMutator;
import ar.edu.itba.sia.geneticAlgorithmGps.implementations.mutators.SingleGeneMutator;
import ar.edu.itba.sia.geneticAlgorithmGps.interfaces.Mutator;

import javax.management.AttributeNotFoundException;

public enum MutatorType {
    SINGLE_GENE,
    MULTI_GENE;

    public static MutatorType getMutationMethod(final String method) throws AttributeNotFoundException {
        if(method.equals(SINGLE_GENE.toString())) {
            return SINGLE_GENE;
        } else if(method.equals(MULTI_GENE.toString())) {
            return MULTI_GENE;
        } else throw new AttributeNotFoundException("mutation method doesn\'t exist");
    }

}
