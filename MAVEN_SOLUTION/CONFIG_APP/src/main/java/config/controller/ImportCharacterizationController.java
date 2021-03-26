/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.controller;
import config.cutils.FactorImport;
import core.domain.RiskMatrixs.MatrixBuilder;
import eapli.framework.application.Controller;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Pedro
 */
public class ImportCharacterizationController implements Controller{
    
    private FactorImport fi = new FactorImport();
    private MatrixBuilder mb = new MatrixBuilder(MatrixBuilder.State.DEFINED);

    public ImportCharacterizationController() {
    }

    public void importWeight(String fichName) throws FileNotFoundException, IOException{
        if(fi.importFactorValues(fichName,mb)){
            System.out.println("Importou com o sucesso o peso!");
        } else{
            System.out.println("Algo deu errado a importar!");
        }
       
    }

    public void importContribution(String fichName) throws FileNotFoundException, IOException{
       if(fi.importFactorValues(fichName,mb)){
           System.out.println("Importou com sucesso a contribuição!");
       }else{
           System.out.println("Algo deu errado a importar!");
       }

    }

    public void importNecessity(String fichName) throws FileNotFoundException, IOException{
        fi.importFactorValues(fichName,mb); 
 
    }
    
    public void importMeaningfullFactors(String fichName) throws FileNotFoundException, IOException{
       if(fi.importMeaningfulFactors(fichName, mb)){
           System.out.println("Importou com sucesso os factores importantes!");;
       }else{
           System.out.println("Algo deu errado a importar!");;
       }
    }

    public boolean importBaseMatrix(String fichName) throws FileNotFoundException, IOException, IntegrityViolationException, ConcurrencyException{
        return fi.importBaseRiskMatrix(fichName, mb);
    }
    
   public boolean importDetailedMatrix(String fichName) throws FileNotFoundException, IOException{
        return fi.imporDetailedRiskMatrix(fichName, mb);
    }
    
}
