package core.persistence.jpa;

import core.domain.location.Location;
import core.persistence.LocationRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationJPAImpl extends JpaAutoTxRepository<Location, Long, Long> implements LocationRepository {

    public LocationJPAImpl(String persistenceUnitName) {
        super(persistenceUnitName, "id");
    }  
    
    public Iterable<Location> findByDistrict(String district) {
        Map<String,Object> param = new HashMap<>();
        param.put("district", district);
        List<Location> ret = super.match("e.district =:district", param);
        if(!ret.isEmpty()){
            return ret;
        }
        return null;
    }
    
    public Iterable<Location> findByCoordinates(String lat1, String lon1, String lat2, String lon2) {
        List<Location> ret = super.match("e.latitude >:"+lat1+" AND e.latitude <:"+lat2+" AND e.longitude >:"+lon1+" AND e.longitude <:"+lon2);
        if(!ret.isEmpty()){
            return ret;
        }
        return null;
    }
}



