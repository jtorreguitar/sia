package ar.edu.itba.sia.GPS.searchAlgorithms;

import ar.edu.itba.sia.GPS.GPSNode;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CriteriaSearch {

    public void findSolution(List<GPSNode> candidates, List<GPSNode> borderNodes, Comparator<GPSNode> criteria) {


        borderNodes.addAll(candidates);
        Collections.sort(borderNodes, criteria);
//        Collections.sort(candidates, criteria);
//
//        for (int i=0 ; i < borderNodes.size() && !candidates.isEmpty() ; i++) {
//            GPSNode candidate =  candidates.get(0);
//            if (criteria.compare(borderNodes.get(i), candidate) >= 0) {
//                borderNodes.add(i, candidate);
//                candidates.remove(0);
//            }
//
//        }
//        borderNodes.addAll(candidates);
    }
}
