/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.persistence;

import core.domain.Case.CaseI;
import core.domain.Case.CaseCode;
import core.domain.Case.CaseDate;
import core.domain.Case.CaseState;
import core.domain.Case.CaseType;
import core.domain.User.Email;
import eapli.framework.infrastructure.repositories.Repository;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Pedro
 */
public interface CaseRepository extends Repository<CaseI, CaseCode> {

    public List<CaseI> findAllByState(CaseState state);

    public List<CaseI> findAllCaseByStateWithDistrict(String state, String district);

    public CaseI findByCaseCode(CaseCode caseId);

    public List<CaseI> getCaseResultsFilteredByTimeInterval(CaseState caseState,CaseDate startDate, CaseDate finalDiate);

    public List<CaseI> getCaseResultsFilteredByCities(CaseState caseState,List<String> cities);

    public List<CaseI> getCaseResultsFilteredByTimeIntervalAndCities(CaseState caseState,CaseDate startDate, CaseDate finalDiate, List<String> cities);
    
    public List<CaseI> findAllByStateAssignedToMe(CaseState s,Email user);

}
