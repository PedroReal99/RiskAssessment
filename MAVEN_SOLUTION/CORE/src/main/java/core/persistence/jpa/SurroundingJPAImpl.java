/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.persistence.jpa;

import core.domain.location.Location;
import core.domain.Surrounding.STName;
import core.domain.Surrounding.Surrounding;
import core.domain.Surrounding.SurroundingName;
import core.persistence.PersistenceContext;
import core.persistence.SurroundingRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Pedro
 */
public class SurroundingJPAImpl extends JpaAutoTxRepository<Surrounding, Long, Long> implements SurroundingRepository {

    public SurroundingJPAImpl(TransactionalContext autoTx) {
        super(autoTx, "name");
    }

    public SurroundingJPAImpl(String puname) {
        super(puname, "name");
    }

    @Override
    public Surrounding findByBothParameters(STName type, SurroundingName name) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        //param.put("type", type);
        Optional<Surrounding> ret = super.matchOne("e.name =:name", param);
        if (ret.isPresent()) {
            return ret.get();
        }
        return null;
    }

    @Override
    public Iterable<Surrounding> findByDistrict(String district) {
        Map<String, Object> param = new HashMap<>();

        param.put("dist", district);
        List<Surrounding> sur = (super.match("e.location.district =:dist", param));

        if (!sur.isEmpty()) {
            return sur;
        }
        return new ArrayList<>();
    }

    @Override
    public Iterable<Surrounding> findByCoordinates(String lat1, String lon1, String lat2, String lon2) {
        Map<String, Object> param = new HashMap<>();
        List<Surrounding> ret = new ArrayList<>();
        List<Location> loc = (List<Location>) PersistenceContext.repositories().LocationRepository().findByCoordinates(lat1, lon1, lat2, lon2);
        for (Location loca : loc) {
            param.put("loc" + loc.indexOf(loca), loc);
            ret.add((super.matchOne("e.location =:loc" + loc.indexOf(loca), param)).get());
        }
        if (!ret.isEmpty()) {
            return ret;
        }
        return new ArrayList<>();
    }
}
