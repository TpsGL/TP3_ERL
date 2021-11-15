package clustering.warpping.process.strategy.interfaces;

import clustering.warpping.linkage.strategy.interfaces.LinkageStrategy;
import clustering.warpping.models.Cluster;


public interface ClusteringAlgorithm
{
    public Cluster executeClustering(double[][] distances, String[] clusterNames,
                                     LinkageStrategy linkageStrategy);
}
