package clustering.warpping.process.strategy.impls;

import clustering.warpping.linkage.strategy.interfaces.LinkageStrategy;
import clustering.warpping.models.Cluster;
import clustering.warpping.models.ProximityDic;
import clustering.warpping.process.builder.HierarchyBuilder;
import clustering.warpping.process.factories.ClustersFactory;
import clustering.warpping.process.factories.ProximityDicFactory;
import clustering.warpping.process.strategy.interfaces.ClusteringAlgorithm;
import clustering.warpping.process.validator.ArgumentsValidator;
import clustering.warpping.process.validator.IValidator;
import java.util.List;

public class DefaultClusteringAlgorithm implements ClusteringAlgorithm
{

    private IValidator iValidator;


    @Override
    public Cluster executeClustering(double[][] distances,
                                     String[] clusterNames, LinkageStrategy linkageStrategy)
    {
        iValidator = new ArgumentsValidator();
        iValidator.checkInputs(distances, clusterNames, linkageStrategy);


        List<Cluster> clusters = ClustersFactory.getInstance().createClusters(clusterNames) ;
        ProximityDic linkages = ProximityDicFactory.getInstance().createProximityDic(distances, clusters);

        HierarchyBuilder builder = new HierarchyBuilder(clusters, linkages);
        while (!builder.isTreeComplete())
        {
            builder.agglomerate(linkageStrategy);
        }

        return builder.getRootCluster();
    }

}
