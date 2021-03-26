/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.controller;

import core.domain.Case.CaseCode;
import core.domain.Case.CaseI;
import core.domain.Insurance.InsuranceObject;
import static core.domain.RiskMatrixs.MatrixState.DETAILED;
import static core.domain.RiskMatrixs.MatrixState.OBSOLETE;
import core.domain.RiskMatrixs.RiskMatrix;
import core.persistence.CaseRepository;
import core.persistence.MatrixRepository;
import core.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//import risk.controller.RiskAssessmentController;

/**
 *
 * @author Pedro
 */
public class ComparisonRiskAssessmentController {
    
    MatrixRepository mr =  PersistenceContext.repositories().riskMatrixRepository();
    CaseRepository cr = PersistenceContext.repositories().caseRepository();
    //RiskAssessmentController rac = new RiskAssessmentController();

    
    public ComparisonRiskAssessmentController(){
        
    }
//    public void compRiskAssess(){
//        List<RiskMatrix> listMatrix = new ArrayList<>();
//        List<RiskMatrix> partialList1 = new ArrayList<>();
//        List<RiskMatrix> partialList2 = new ArrayList<>();
//        
//        //Ir buscar caso com o cÃ³digo CODE pois Ã© o que vai ser importado no json
//        Optional<CaseI> op = cr.findById(new CaseCode("Code"));
//        CaseI casee = op.get();
//        
//        //Ir buscar obsoletas
//        partialList1=mr.findByState(OBSOLETE);
//        
//        //Ir buscar detalhadas
//        partialList2=mr.findByState(DETAILED);
//        
//        //Juntar listas
//        
//        listMatrix.addAll(partialList1);
//        listMatrix.addAll(partialList2);
//        
//        
//        //Comparar num loop
//        for(RiskMatrix rm:listMatrix){
//            rac.calculateRiskAssessmentForComparison(rm, casee);
//        }
//          
//    }
    
    
    
    
    public String comparisonRiskAssessment(String vers1, String vers2, String caseCode){
        List<InsuranceObject> list1 = new ArrayList<>();
        List<InsuranceObject> list2 = new ArrayList<>();
        List<Integer> list11 = new ArrayList<>();
        List<Integer> list22 = new ArrayList<>();
        
        CaseI c = obtainCase(caseCode);
        verifyIfAbleToCalculateRiskIndex(c);
        RiskMatrix rm1 = obtainMatrix(vers1);
        RiskMatrix rm2 = obtainMatrix(vers2);
        c.calculateRiskAssessment("serviço de sistemas externos",rm1, false);
        list1 = getInsurancesofCase(c);
        list11 = getValuesOfList(list1);
        c.calculateRiskAssessment("serviço de sistemas externos",rm2, false);
        list2 = getInsurancesofCase(c);
        list22 = getValuesOfList(list2);
        //String str = null;
        String str =CompareResults(list1,list2,list11,list22);
//        if(a==1){
//            return str+"Matriz 1 tem melhor indice de risco";
//        }else{
//            return str+"Matriz 2 tem melhor Ã­ndice de risco";
//        }
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

    private String CompareResults(List<InsuranceObject> list1, List<InsuranceObject> list2,List<Integer> list11, List<Integer> list22) {
        int cont1=0;
        int cont2=0;
        String str="";
        for(int i=0;i<list1.size();i++){
            str+="\n"+list1.get(i).obtainName();
            str+="\nPrimeira matriz apresenta "+ list11.get(i);
            str+="\nSegunda matriz apresenta "+ list22.get(i);
            if(list1.get(i).obtainIntegerRiskIndex()<list2.get(i).obtainIntegerRiskIndex()){
                cont1++;
            }else{
                cont2++;
            }
        }
        if(cont1>cont2){
            return str+"\nMatriz 1 tem melhor indice de risco";
        }else {
            return str+"\nMatriz 2 tem melhor indice de risco";
        }
    }

    private List<Integer> getValuesOfList(List<InsuranceObject> list1) {
        List<Integer> list = new ArrayList<>();
        for(InsuranceObject io:list1){
            list.add(io.obtainIntegerRiskIndex());
        }
        return list;
    }
    
    
}
