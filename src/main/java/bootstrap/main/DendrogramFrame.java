package bootstrap.main;

import clustering.warpping.linkage.strategy.impls.AverageLinkageStrategy;
import clustering.warpping.linkage.strategy.interfaces.LinkageStrategy;
import clustering.warpping.models.Cluster;
import clustering.warpping.models.Pair;
import clustering.warpping.process.strategy.impls.DefaultClusteringAlgorithm;
import clustering.warpping.process.strategy.interfaces.ClusteringAlgorithm;
import clustering.warpping.visualization.DendrogramPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DendrogramFrame extends JFrame {

    public DendrogramFrame(Cluster cluster) {
        setSize(500, 400);
        setLocation(100, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel content = new JPanel();
        DendrogramPanel dp = new DendrogramPanel();

        setContentPane(content);
        content.setBackground(Color.red);
        content.setLayout(new BorderLayout());
        content.add(dp, BorderLayout.CENTER);
        dp.setBackground(Color.WHITE);
        dp.setLineColor(Color.BLACK);
        dp.setScaleValueDecimals(0);
        dp.setScaleValueInterval(1);
        dp.setShowDistances(false);

        dp.setModel(cluster);
        setVisible(true);
    }

    public static void main(String[] args) {

        LinkageStrategy strategy =
                new AverageLinkageStrategy();
        Frame f1 = new DendrogramFrame( createSampleCluster(strategy) );
        f1.setSize(500, 400);
        f1.setLocation(100, 200);
    }

    private static Cluster createSampleCluster(LinkageStrategy strategy) {

        //String[] names = new String[] { "O1", "O2", "O3", "O4", "O5", "O6" };

        String[] names =
                new String[] { "Vehicule", "Car", "Bus", "Calculator",
                        "Inspector", "Inspection" };

        double[][] distances = new double[][] {

                { 0, 1, 9, 7, 11,14 },
                { 1, 0, 4, 3, 8, 1  },
                { 9, 4, 0, 9, 2, 8  },
                { 7, 3, 9, 0, 6, 13 },
                { 11, 8, 2, 6, 0,1  },
                { 14, 1, 8, 13, 1,0  }
        };


        ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
        Cluster cluster = alg.executeClustering(distances, names, strategy);
        cluster.toConsole(0);
        System.err.println(selection_cluster(cluster));
        return cluster;
    }

    private static List<Cluster> selection_cluster(Cluster dendgr) {

        List<Cluster> R = new ArrayList<>();

        Stack<Cluster> parcoursCluster = new Stack<>();

        parcoursCluster.push(dendgr);

        while (!parcoursCluster.isEmpty()) {

            Cluster parent = parcoursCluster.pop();

            Cluster cl1 = parent.getChildren().get(0);
            Cluster cl2 = parent.getChildren().get(1);

            if (cl1 == null || cl2 == null) {
                R.add(parent);
                continue;
            }

            if ( S(parent) > avg( S(cl1) , S(cl2) ) ) {
                R.add(parent);
            } else {
                parcoursCluster.push(cl1);
                parcoursCluster.push(cl2);
            }
        }
        return R;

    }

    private static Double S(Cluster parent) {
        return parent.getDistanceValue();
    }

    private static Double avg(double value1, double value2) {
        return ( value1 + value1 ) / 2 ;
    }


}
