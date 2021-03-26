/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.persistence.jpa;

import core.domain.Case.CaseI;
import core.domain.Case.CaseCode;
import core.domain.Case.CaseDate;
import core.domain.Case.CaseState;
import core.domain.Insurance.InsuranceObject;
import core.domain.User.Email;
import core.persistence.CaseRepository;
import core.persistence.PersistenceContext;
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
public class CaseJPAImpl extends JpaAutoTxRepository<CaseI, CaseCode, CaseCode> implements CaseRepository {

    public CaseJPAImpl(TransactionalContext autoTx) {
        super(autoTx, "code");
    }

    public CaseJPAImpl(String puname) {
        super(puname, "code");
    }

    @Override
    public List<CaseI> findAllByState(CaseState caseState) {
        Map<String, Object> param = new HashMap<>();
        param.put("caseState", caseState);
        List<CaseI> ret = (super.match("e.caseState = :caseState ORDER BY e.creationDate", param));
        if (!ret.isEmpty()) {
            return ret;
        }
        return new ArrayList<>();
    }

    @Override
    public List<CaseI> findAllCaseByStateWithDistrict(String caseState, String district) {
        Map<String, Object> param = new HashMap<>();
        param.put("caseState", caseState);
        param.put("district", district);
        List<CaseI> ret = super.match("e.caseState = :caseState AND e.insurances.location.district = :district ORDER BY e.creationDate", param);
        //List<CaseI> ret = super.match("e.caseState = :" + caseState + " AND e.CASEI_INSURANCEOBJECT.INSURANCEOBJECT.location.district = :" + district);
        //List<CaseI> ret = super.match("e.caseState = :" + caseState + " AND e.INSURANCEOBJECT.location.district = :" + district);
        if (!ret.isEmpty()) {
            return ret;
        }
        return new ArrayList<>();
    }

    @Override
    public CaseI findByCaseCode(CaseCode caseCode) {
        Map<String, Object> param = new HashMap<>();
        param.put("caseCode", caseCode);
        Optional<CaseI> ret = super.matchOne("e.code = :caseCode", param);
        if (ret.isPresent()) {
            return ret.get();
        }
        return null;
    }

    @Override
    public List<CaseI> getCaseResultsFilteredByTimeInterval(CaseState caseState, CaseDate startDate, CaseDate finalDate) {
        Map<String, Object> param = new HashMap<>();
        param.put("caseState", caseState);
        param.put("initialDate", startDate);
        param.put("finalDate", finalDate);
        List<CaseI> ret = super.match("e.caseState = :caseState AND e.caseDate >=initialDate AND e.caseDate <= finalDate ", param);

        if (!ret.isEmpty()) {
            return ret;
        }
        return new ArrayList<>();
    }

    @Override
    public List<CaseI> getCaseResultsFilteredByCities(CaseState caseState, List<String> districts) {
        Map<String, Object> param = new HashMap<>();
        param.put("caseState", caseState);
        List<CaseI> ret = new ArrayList<>();
        List<CaseI> cases = new ArrayList<>();
        for (String district : districts) {
            param.put("city" + districts.indexOf(district), district);
            ret = super.match("e.caseState = :caseState AND e.INSURANCEOBJECT.location.district = : city", param);
            if (!ret.isEmpty()) {
                cases.addAll(ret);
            }
        }
        if (!cases.isEmpty()) {
            return cases;
        }
        return new ArrayList<>();
    }

    @Override
    public List<CaseI> getCaseResultsFilteredByTimeIntervalAndCities(CaseState caseState, CaseDate startDate, CaseDate finalDate, List<String> districts) {
        Map<String, Object> param = new HashMap<>();
        param.put("caseState", caseState);
        param.put("initialDate", startDate);
        param.put("finalDate", finalDate);
        List<CaseI> ret = new ArrayList<>();
        List<CaseI> cases = new ArrayList<>();
        for (String district : districts) {
            param.put("city" + districts.indexOf(district), district);
            ret = super.match("e.caseState = :caseState AND e.caseDate >=initialDate AND e.caseDate <= finalDate e.INSURANCEOBJECT.location.district = : city", param);
            if (!ret.isEmpty()) {
                cases.addAll(ret);
            }
        }
        if (!cases.isEmpty()) {
            return cases;
        }
        return new ArrayList<>();
    }

    @Override
    public List<CaseI> findAllByStateAssignedToMe(CaseState s, Email user) {
        Map<String, Object> param = new HashMap<>();
        param.put("caseState", s);
        param.put("mail", user);
        List<CaseI> ret = super.match("e.caseState = :caseState AND e.employee = :mail ORDER BY e.creationDate", param);
        if (!ret.isEmpty()) {
            return ret;
        }
        return new ArrayList<>();
    }
}
