package core.persistence;

import core.domain.location.GPSLocation;
import core.domain.location.Location;
import core.domain.RiskMatrixs.MatrixState;
import core.domain.RiskMatrixs.RiskMatrix;
import eapli.framework.infrastructure.repositories.Repository;
import java.util.List;

public interface LocationRepository extends Repository<Location, Long> {
    
    public Iterable<Location> findByDistrict(String district);

    public Iterable<Location> findByCoordinates(String lat1, String lon1, String lat2, String lon2);

}
