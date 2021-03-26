/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.controller;

import core.domain.Case.CaseCode;
import core.domain.Case.CaseDate;
import core.domain.Case.CaseI;
import core.domain.Case.CasePriority;
import core.domain.Case.CaseState;
import core.domain.Case.CaseType;
import core.domain.Coverage.Coverage;
import core.domain.Coverage.CoverageName;
import core.domain.location.GPSLocation;
import core.domain.Insurance.InsuranceName;
import core.domain.Insurance.InsuranceObject;
import core.domain.location.Location;
import core.domain.location.LocationFactory;
import core.persistence.CoverageRepository;
import core.persistence.PersistenceContext;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author CarloS
 */
public class RASubmissionController {
    
    private CaseI caso;
    
    public RASubmissionController(boolean needOfValidation, CasePriority priority){
        caso = new CaseI(new CaseType("submited from SE"), CaseState.WAITING, new CaseCode("caseCode" + this.hashCode()), needOfValidation, priority, new CaseDate((new Date()).toString()));
    }
    public void addInsuranceObject(String location, Set<String> coveragesNames){
        Location local=new LocationFactory(LocationFactory.Type.GPS, location).build();
        CoverageRepository cr=PersistenceContext.repositories().coverageRepository();
        InsuranceObject object=new InsuranceObject(new InsuranceName(location), local);
        for(String coverageName:coveragesNames){
            Coverage coverage=cr.findByName(new CoverageName(coverageName));
            if(coverage!=null){
                object.addCoverage(coverage,new HashMap<>());
            }
        }
        caso.associateInsurance("serviço de sistemas externos",object);
    }
    
    
    public void save(){
        PersistenceContext.repositories().caseRepository().save(caso);
    }
}
