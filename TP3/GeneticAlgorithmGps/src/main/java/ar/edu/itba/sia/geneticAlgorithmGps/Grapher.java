package ar.edu.itba.sia.geneticAlgorithmGps;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.PieStyler;

public class Grapher {

    private int t = 1;
    private double generations[] = new double[2002];
    private double maxApt[] = new double[2002];
    private XYChart chart = QuickChart.getChart("max Aptitude vs generations", "Generations", " Max Aptitude", "Max Aptitude",generations, maxApt );;
    private SwingWrapper<XYChart> sw = new SwingWrapper<XYChart>(chart);


    public Grapher(){
        generations[0] = 0.0;
        maxApt[0] = 0.0;
        sw.displayChart();
    }

    public void updateChart(double value){
        maxApt[t] = value;
        generations[t++] = ((double)t);

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                chart.updateXYSeries("Max Aptitude", generations,maxApt,null);
                sw.repaintChart();
            }
        });
    }

}
