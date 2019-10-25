package ar.edu.itba.sia.interfaces;

import java.util.List;

public interface Chromosome {
    double getAptitude();

    int getAlleleCount();

    /**
     * For this method to function correctly the chromosome must have a consistent ordering of its alleles,
     * whatever representation they may have.
     * @param startIndex the first allele to be swapped (inclusive).
     * @param endIndex the last allele to be swapped (exclusive). If endIndex is less than startIndex it represents
     *                 annular crossing.
     * @param mate the chromosome with which the alleles will be swapped.
     * @return the children produced by the crossing.
     */
    List<Chromosome> cross(final int startIndex, final int endIndex, final Chromosome mate);

    /**
     *
     * @param indexes the indexes in which the alleles will be swapped
     * @param mate the chromosome with which the alleles will be swapped.
     * @return the children produced by the crossing.
     */
    List<Chromosome> cross(final List<Integer> indexes, final Chromosome mate);
    Chromosome mutate(final List<Integer> indexes);
}
