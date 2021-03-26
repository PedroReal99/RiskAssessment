package webserver.controller;

import core.domain.Case.CaseCode;
import core.domain.Case.CaseI;
import core.domain.Insurance.InsuranceObject;
import core.domain.RiskMatrixs.RiskMatrix;
import core.persistence.CaseRepository;
import core.persistence.MatrixRepository;
import core.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

public class GetRiskAssessmentResultController {

    MatrixRepository mr =  PersistenceContext.repositories().riskMatrixRepository();
    CaseRepository cr = PersistenceContext.repositories().caseRepository();

    public String getIntegerRiskIndex(String caseCode, String vers) {
        CaseI c = obtainCase(caseCode);
        if (c.obtainState().toString().equalsIgnoreCase("Em espera")) {
            return c.obtainState();
        }
        verifyIfAbleToCalculateRiskIndex(c);
        RiskMatrix rm = obtainMatrix(vers);
        c.calculateRiskAssessment("serviço de sistemas externos",rm, false);
        List<InsuranceObject> list1 = getInsurancesofCase(c);
        List<Integer> list11 = getValuesOfList(list1);
        String str = "";
        int cont = 0;
        for (Integer i : list11) {
            str = str + "\n" + list1.get(cont).toString() + "-> " + i;
            cont++;
        }
        return str;
    }

    private void verifyIfAbleToCalculateRiskIndex(CaseI c) {
        if(c == null || verifyCase(c)) {
            throw new IllegalArgumentException("There isn't Coverages connected to the Case");
        }
    }

    private boolean verifyCase(CaseI c) {
        return c.getAssociatedInsuranceObjects().isEmpty();
    }



    private CaseI obtainCase(String caseCode){
        CaseRepository cr = PersistenceContext.repositories().caseRepository();
        try{
            return cr.findById(new CaseCode(caseCode)).get();
        }
        catch(Exception ex){
            System.out.println("Erro a buscar a base de dados");
        }
        return null;
    }

    private RiskMatrix obtainMatrix(String version){
        MatrixRepository mr = PersistenceContext.repositories().riskMatrixRepository();
        return mr.findByName(version);
    }

    private List<InsuranceObject> getInsurancesofCase(CaseI c){
        List<InsuranceObject> l = new ArrayList<>();
        c.getAssociatedInsuranceObjects().forEach((io) -> {
            l.add(io);
        });
        return l;
    }

    private List<Integer> getValuesOfList(List<InsuranceObject> list1) {
        List<Integer> list = new ArrayList<>();
        for(InsuranceObject io:list1){
            list.add(io.obtainIntegerRiskIndex());
        }
        return list;
    }

}
