package ar.edu.itba.sia.GPS.searchAlgorithms;

import ar.edu.itba.sia.GPS.GPSNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IterativeDeepeningSearch implements SearchAlgorithm {

    private static final int HEIGHT_INCREMENT = 10;

    private Map<GPSNode, Integer> nodesHeight;
    private boolean keepon = true;

    private int currentHeight = 0;
    private int finalHeight = HEIGHT_INCREMENT + 1;

    public IterativeDeepeningSearch() {

        nodesHeight = new HashMap<>();
    }

    @Override
    public String toString() {
        return "IDDFS";
    }

    public void idsSolution(List<GPSNode> candidates, List<GPSNode> borderNodes, Map<GPSNode, Integer> minNodeHeight) throws OutOfMemoryError {

        currentHeight++;
        // anotamos en que iteracion vimos al nodo
        for (GPSNode n : candidates){
            nodesHeight.put(n, currentHeight);
            if(minNodeHeight.containsKey(n)){
                //anotamos la altura minima en la que vimos al nodo
                if( minNodeHeight.get(n) > n.getDepth() ){
                    minNodeHeight.put(n,n.getDepth());
                }
            }else{
                minNodeHeight.put(n,n.getDepth());
            }
        }


        if (currentHeight == finalHeight) {
            GPSNode nextNodeToExplore = borderNodes.get(0);
            currentHeight = nodesHeight.get(nextNodeToExplore);

            boolean expandHeight = true;
            for (GPSNode n : borderNodes) {
                if (nodesHeight.get(n) != finalHeight) {
                    expandHeight = false;
                    break;
                }
            }
            if (expandHeight){
                keepon = false;
                for (GPSNode n : borderNodes) {
                    if (minNodeHeight.get(n) >= finalHeight) {
                        keepon = true;
                    }
                }
                finalHeight += HEIGHT_INCREMENT;
                borderNodes.clear();
                nodesHeight.clear();
                //si borderNodes esta vacio, hay que reiniciar
                if(!keepon){ throw new OutOfMemoryError(); }
            }

        }
        else
            borderNodes.addAll(0, candidates);

    }


    @Override
    public boolean findSolution(List<GPSNode> candidates, List<GPSNode> borderNodes) {

        if( !candidates.isEmpty() && nodesHeight.containsKey(candidates.get(0).getParent())){
            nodesHeight.remove(candidates.get(0).getParent());
        }

        for(GPSNode n : nodesHeight.keySet()){
            if ( currentHeight < n.getDepth() ){
                currentHeight = n.getDepth();
            }
        }
        for (GPSNode n : candidates)
            nodesHeight.put(n, n.getDepth());

        if (currentHeight <= (finalHeight-1) ) {
            borderNodes.addAll(candidates);
        }
        else{
            if(currentHeight == finalHeight){
                boolean expandHeight = true;
                for (GPSNode n : borderNodes) {
                    if (nodesHeight.get(n) != finalHeight) {
                        expandHeight = false;
                        break;
                    }
                }
                if (expandHeight){
                    finalHeight += HEIGHT_INCREMENT;
                    borderNodes.clear();
                    nodesHeight.clear();
                    return true;
                }
                return false;
            }
            borderNodes.addAll(0, candidates);
        }

        return false;
    }
}
