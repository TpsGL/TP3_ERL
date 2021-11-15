package clustering.warpping.linkage.strategy.interfaces;

import clustering.warpping.models.Proximity;

import java.util.Collection;

public interface LinkageStrategy {

    public Proximity calculateProximity(Collection<Proximity> proximities);
}
