/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.utils;

import core.domain.Case.CaseDate;
import core.domain.Case.CaseI;
import core.domain.Insurance.InsuranceObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author joaoflores
 */
public class ObtainCasesByFilters {

    public ObtainCasesByFilters() {
        //do nothing
    }

    public List<CaseI> getCaseResultsFilteredByTimeInterval(CaseDate startDate, CaseDate finalDate, List<CaseI> allCasesByState) throws ParseException {
        List<CaseI> allCasesByDate = new ArrayList<>();
        Date in = convertStringToDate(startDate);
        Date fin = convertStringToDate(finalDate);
        for (CaseI caseFromAllCasesByState : allCasesByState) {
            Date date = convertStringToDate(new CaseDate(caseFromAllCasesByState.obtainDate()));
            if (date.after(in) && date.before(fin)) {
                allCasesByDate.add(caseFromAllCasesByState);
            }
        }
        return allCasesByDate;
    }
    
      public  List<CaseI> getCaseResultsFilteredByCities(List<String> cities, List<CaseI> allCasesByState) {
        List<CaseI> allCasesByCities = new ArrayList<>();
        for (String city : cities) {
            for (CaseI caseFromAllCasesByState : allCasesByState) {
                List<InsuranceObject> insuranceAssociated = caseFromAllCasesByState.getAssociatedInsuranceObjects();
                for (InsuranceObject io : insuranceAssociated) {
                    if (io.obtainDistrict().trim().equalsIgnoreCase(city.trim())) {
                        allCasesByCities.add(caseFromAllCasesByState);
                    }
                }
            }
        }
        return allCasesByCities;
    }
        
     public   List<CaseI> getCaseResultsFilteredByTimeIntervalAndCities(CaseDate startDate, CaseDate finalDate, List<String> cities, List<CaseI> allCasesByState) throws java.text.ParseException {
        List<CaseI> allCasesByCitiesAndDate = new ArrayList<>();
        Date in = convertStringToDate(startDate);
        Date fin = convertStringToDate(finalDate);
        for (String city : cities) {
            for (CaseI caseFromAllCasesByState : allCasesByState) {
                Date date = convertStringToDate(new CaseDate(caseFromAllCasesByState.obtainDate()));
                List<InsuranceObject> insuranceAssociated = caseFromAllCasesByState.getAssociatedInsuranceObjects();
                for (InsuranceObject io : insuranceAssociated) {
                    if (date.after(in) && date.before(fin)) {
                        if (io.obtainDistrict().trim().equalsIgnoreCase(city.trim())) {
                            allCasesByCitiesAndDate.add(caseFromAllCasesByState);
                        }
                    }
                }
            }
        }
        return allCasesByCitiesAndDate;
    }
    
    
    
    public static Date convertStringToDate(CaseDate date) throws java.text.ParseException {
        // formato data 06/06/2006 dia-mes-ano
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        Date dateFormated = formatter.parse(date.toString());
        System.out.println(dateFormated.toString());
        return dateFormated;
    }


}
