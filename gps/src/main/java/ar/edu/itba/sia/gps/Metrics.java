package ar.edu.itba.sia.gps;


import java.util.*;

public class Metrics {

        private static final Metrics INSTANCE = new Metrics();

        private final long runningTimeStart = System.currentTimeMillis();

        private int repOmitted = 0;

        private Metrics () {
        }

        public double computeMetrics(GPSEngine engine) {

            long runningTimeEnd = System.currentTimeMillis();

            double runningTime = ((double)(runningTimeEnd - runningTimeStart)) / 1000;

            double cost = ( engine.getSolutionNode() != null ? engine.getSolutionNode().getDepth() : 0 );

            System.out.println("Movimientos elegidos para ganar: ");
            System.out.println("\n");

            printSolution(engine.getSolutionNode());

            System.out.println("heurística: " + engine.getHeuristic());
            System.out.println("algoritmo: " + engine.getStrategy());
            System.out.println("Altura de la solución: " + ( engine.getSolutionNode() != null ? engine.getSolutionNode().getDepth() : "no hay solución" ));
            System.out.println("Costo de la solución: " + ( engine.getSolutionNode() != null ? engine.getSolutionNode().getCost() : "no hay solución" ));
            System.out.println("Número de nodos frontera: " + engine.getOpen().size());
            System.out.println("Número de explosiones: " + engine.getExplosionCounter());
            System.out.println("Número de nodos analizados: " + engine.getBestCosts().size());
            System.out.println("Tiempo de ejecución: " + runningTime + " segundos");
            System.out.println("Costo de la solución: " + cost);
            System.out.println("Repeticiones omitidas: " + repOmitted);
            System.out.println("\n");

            return cost;
        }

    private void printSolution(GPSNode solutionNode) {
        Deque<GPSNode> path = generatePath(solutionNode);
        printPath(path);
    }

    private Deque<GPSNode> generatePath(GPSNode solutionNode) {
        Deque<GPSNode> ret = new LinkedList<>();
        GPSNode currentNode = solutionNode;
        while(currentNode != null) {
            ret.push(currentNode);
            currentNode = currentNode.getParent();
        }
        return ret;
    }


    private void printPath(Deque<GPSNode> path) {
        while (!path.isEmpty()) {
            GPSNode current = path.pop();
            if(current.getGenerationRule() != null) System.out.println(current.getGenerationRule());
            System.out.println(current.getState().getRepresentation());
        }
    }

    public void repHit() {
            repOmitted++;
        }

        public static Metrics getInstance() {
            return INSTANCE;
        }

}
