/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.ui;

import config.controller.ImportCharacterizationController;
import core.domain.RiskMatrixs.MatrixBuilder;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro
 */
public class ImportCharacterizationUI extends AbstractUI{
    
    private final ImportCharacterizationController c = new ImportCharacterizationController();

    @Override
    protected boolean doShow() {
        System.out.println(headline()); 
        String fichName = Console.readLine("Insert name File for Meaningfull factors import, please::");
        try{
        this.c.importMeaningfullFactors(fichName);
        } catch(FileNotFoundException ex){
            System.out.println("File not found");
        } catch (IOException ex) {
            Logger.getLogger(ImportCharacterizationUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        fichName = Console.readLine("Insert name File for Weight import, please:");
        try{
        this.c.importWeight(fichName);
        } catch(FileNotFoundException ex){
            System.out.println("File not found");
        } catch (IOException ex) {
            Logger.getLogger(ImportCharacterizationUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        fichName = Console.readLine("Insert name File for Contribution import, please:");
        try{
        this.c.importContribution(fichName);
        } catch(FileNotFoundException ex){
            System.out.println("File not found");
        } catch (IOException ex) {
            Logger.getLogger(ImportCharacterizationUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        fichName = Console.readLine("Insert name File for necessity import, please:");
        try{
        this.c.importNecessity(fichName);
        } catch(FileNotFoundException ex){
            System.out.println("File not found");
        } catch (IOException ex) {
            Logger.getLogger(ImportCharacterizationUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        /**
        fichName = Console.readLine("Insert name File for Base Matrix import, please:");
        try{
            this.c.importBaseMatrix(fichName);
        } catch(FileNotFoundException ex){
            System.out.println("File not found");
        } catch (IOException ex) {
            Logger.getLogger(ImportCharacterizationUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        **/
        return false;
    }

    @Override
    public String headline() {
         return "Meaningful Risk Factores and Characterization Imports:";
    }
    
    
}
