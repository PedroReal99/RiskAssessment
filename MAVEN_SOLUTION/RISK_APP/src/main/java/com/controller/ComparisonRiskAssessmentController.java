/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import core.domain.Case.CaseCode;
import core.domain.Case.CaseI;
import static core.domain.RiskMatrixs.MatrixState.DETAILED;
import static core.domain.RiskMatrixs.MatrixState.OBSOLETE;
import core.domain.RiskMatrixs.RiskMatrix;
import core.persistence.CaseRepository;
import core.persistence.MatrixRepository;
import core.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import risk.controller.RiskAssessmentController;

/**
 *
 * @author Pedro
 */
public class ComparisonRiskAssessmentController {
    
    MatrixRepository mr =  PersistenceContext.repositories().riskMatrixRepository();
    CaseRepository cr = PersistenceContext.repositories().caseRepository();
    RiskAssessmentController rac = new RiskAssessmentController();

    
    public ComparisonRiskAssessmentController(){
        
    }
    public void compRiskAssess(){
        List<RiskMatrix> listMatrix = new ArrayList<>();
        List<RiskMatrix> partialList1 = new ArrayList<>();
        List<RiskMatrix> partialList2 = new ArrayList<>();
        
        //Ir buscar caso com o código CODE pois é o que vai ser importado no json
        Optional<CaseI> op = cr.findById(new CaseCode("Code"));
        CaseI casee = op.get();
        
        //Ir buscar obsoletas
        partialList1=mr.findByState(OBSOLETE);
        
        //Ir buscar detalhadas
        partialList2=mr.findByState(DETAILED);
        
        //Juntar listas
        
        listMatrix.addAll(partialList1);
        listMatrix.addAll(partialList2);
        
        
        //Comparar num loop
        for(RiskMatrix rm:listMatrix){
            rac.calculateRiskAssessmentForComparison(rm, casee);
        }
        
    } 
    
}
