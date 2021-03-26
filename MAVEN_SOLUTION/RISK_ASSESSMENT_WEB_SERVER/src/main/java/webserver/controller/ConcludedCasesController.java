/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.controller;

import core.domain.Case.CaseDate;
import core.domain.Case.CaseI;
import core.domain.Case.CaseState;
import core.domain.RiskMatrixs.RiskMatrix;
import core.persistence.CaseRepository;
import core.persistence.MatrixRepository;
import core.persistence.PersistenceContext;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import webserver.utils.ObtainCasesByFilters;

/**
 *
 * @author joaoflores
 */
public class ConcludedCasesController {

    private static CaseRepository caseRepository = PersistenceContext.repositories().caseRepository();
    private static MatrixRepository matrixRepository = PersistenceContext.repositories().riskMatrixRepository();
    private ObtainCasesByFilters casesByFilters = new ObtainCasesByFilters();

    public ConcludedCasesController() {
        //do nothing
    }

//    public List<CaseI> getCaseResultsFilteredByTimeInterval(CaseState state, CaseDate start, CaseDate end) {
//        if (start.equals(end)) {
//            return null;
//        }
//        if (!state.toString().equalsIgnoreCase("processado")) {
//            return null;
//        }
//        return caseRepository.getCaseResultsFilteredByTimeInterval(state, start, end);
//    }
//
//    public List<CaseI> getCaseResultsFilteredByCities(CaseState state, List<String> cities) {
//        if (cities.isEmpty()) {
//            return new ArrayList<>();
//        }
//        if (!state.toString().equalsIgnoreCase("processado")) {
//            return null;
//        }
//        return caseRepository.getCaseResultsFilteredByCities(state, cities);
//    }
//
//    public List<CaseI> getCaseResultsFilteredByTimeIntervalAndCities(CaseState state, CaseDate start, CaseDate end, List<String> cities) {
//        if (start.equals(end)) {
//            return null;
//        }
//        if (cities.isEmpty()) {
//            return new ArrayList<>();
//        }
//        if (!state.toString().equalsIgnoreCase("processado")) {
//            return null;
//        }
//        return caseRepository.getCaseResultsFilteredByTimeIntervalAndCities(state, start, end, cities);
//    }
    public List<CaseI> getAllCases() {
        return (List<CaseI>) caseRepository.findAll();
    }

    private RiskMatrix obtainMatrix(String version) {
        return matrixRepository.findByName(version);
    }

    public List<CaseI> getCaseResultsFilteredByTimeInterval(CaseDate startDate, CaseDate finalDate, List<CaseI> allCasesByState) throws ParseException {
        return casesByFilters.getCaseResultsFilteredByTimeInterval(startDate, finalDate, allCasesByState);
    }

    public List<CaseI> getCaseResultsFilteredByTimeIntervalAndCities(CaseDate startDate, CaseDate finalDate, List<String> cities, List<CaseI> allCasesByState) throws ParseException {
        return casesByFilters.getCaseResultsFilteredByTimeInterval(startDate, finalDate, allCasesByState);
    }

    public List<CaseI> getCaseResultsFilteredByCities(List<String> cities, List<CaseI> allCasesByState) {
        return casesByFilters.getCaseResultsFilteredByCities(cities, allCasesByState);
    }
}
