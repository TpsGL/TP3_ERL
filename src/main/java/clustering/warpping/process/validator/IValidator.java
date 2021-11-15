package clustering.warpping.process.validator;

import clustering.warpping.linkage.strategy.interfaces.LinkageStrategy;

public interface IValidator {

    public void checkInputs(double[][] distances, String[] clusterNames,
                         LinkageStrategy linkageStrategy);

}
