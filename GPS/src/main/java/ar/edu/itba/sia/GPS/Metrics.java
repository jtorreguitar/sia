package ar.edu.itba.sia.GPS;


import java.util.HashMap;
import java.util.Map;

public class Metrics {

        private static final Metrics INSTANCE = new Metrics();

        private final long runningTimeStart = System.currentTimeMillis();

        private int repOmitted = 0;

        private Metrics () {
        }

        public double computeMetrics(final Integer statesGeneratedCount, final Integer borderNodesCount,
                                   final GPSNode solutionNode) {

            Map<String, Double> statesChosen = new HashMap<>();

            long runningTimeEnd = System.currentTimeMillis();

            double runningTime = ((double)(runningTimeEnd - runningTimeStart)) / 1000;

            System.out.println("Estados generados: " + statesGeneratedCount);

            double cost = solutionNode.getDepth();

            int height = 0;
            GPSNode currentNode = solutionNode;
            while (currentNode != null) {
                statesChosen.put( currentNode.getState().toString(), currentNode.getDepth() );
                currentNode = currentNode.getParent();
                height++;
            }
            height--;

            System.out.println("Altura de la solución: " + height);

            System.out.println("Número de nodos frontera: " + borderNodesCount);
            System.out.println("Número de nodos expandidos: " + (statesGeneratedCount - borderNodesCount));
            System.out.println("Tiempo de ejecución: " + runningTime + " segundos");
            System.out.println("Costo de la solución: " + cost);
            System.out.println("Repeticiones omitidas: " + repOmitted);
            System.out.println("\n");
            System.out.println("Movimientos elegidos para ganar: ");
            System.out.println("\n");

            int i = 0;
            double prevAccum = 0;
            for (Map.Entry<String, Double> pair : statesChosen.entrySet()) {
                if (i != 0) {
                    System.out.println("Movimiento " + i + ":\n");
                    System.out.println("Costo " + (pair.getValue() - prevAccum) + "\n");
                    prevAccum = pair.getValue();
                }
                else
                    System.out.println("Estado inicial:\n");

                System.out.println("Costo acumulado " + pair.getValue() + "\n");
                System.out.println(pair.getKey());
                i++;
            }

            return cost;
        }

        public void repHit() {
            repOmitted++;
        }

        public static Metrics getInstance() {
            return INSTANCE;
        }

}
