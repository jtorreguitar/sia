package ar.edu.itba.sia.interfaces;

import java.util.List;

public interface Chromosome {
    double getAptitude();
    void swap(int index, Allele newAllele);
    void swap(int startIndex, List<Allele> newAlleles);
    void mutate(int index);
}
