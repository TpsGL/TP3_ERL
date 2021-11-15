package clustering.warpping.process.validator;

import clustering.warpping.linkage.strategy.interfaces.LinkageStrategy;

import java.util.Arrays;
import java.util.HashSet;

public class ArgumentsValidator implements  IValidator{





    @Override
    public void checkInputs(double[][] distances, String[] clusterNames,
                         LinkageStrategy linkageStrategy) {

        /*if (distances == null || distances.length == 0
                || distances[0].length != distances.length)
        {
            throw new IllegalArgumentException("Invalid distance matrix");
        }
        if (distances.length != clusterNames.length)
        {
            throw new IllegalArgumentException("Invalid cluster name array");
        }
        if (linkageStrategy == null)
        {
            throw new IllegalArgumentException("Undefined linkage strategy");
        }
        int uniqueCount = new HashSet<String>(Arrays.asList(clusterNames)).size();
        if (uniqueCount != clusterNames.length)
        {
            throw new IllegalArgumentException("Duplicate names");
        }*/

    }
}
