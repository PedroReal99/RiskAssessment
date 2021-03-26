/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.ui;

import config.controller.RiskMatrixController;
import core.domain.RiskMatrixs.Columns.BaseColumn;
import core.domain.RiskMatrixs.Line;
import core.domain.RiskMatrixs.MatrixState;
import core.domain.RiskMatrixs.MatrixVersion;
import core.domain.RiskMatrixs.RiskMatrix;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import javafx.util.Pair;

/**
 *
 * @author CarloS
 */
public class MatrixConfigUI extends AbstractUI {

    RiskMatrixController cont;

    public MatrixConfigUI(RiskMatrixController cont) {
        this.cont = cont;
    }

    protected boolean doShowSelect() {
        System.out.println(headline());
        String mat = Console.readLine("Insert matrix version:");
        cont.getMatrix(mat);
        return doShow();
    }

    protected boolean doShowstartFromExisting() {
        System.out.println(headline());
        String mat = Console.readLine("Insert matrix version:");
        cont.startFromMatrix(mat);
        return doShow();
    }
    
    protected boolean doShowPublishMatrix() {
        System.out.println(headline());
        String mat = Console.readLine("Insert matrix version:");
        String date = Console.readLine("Publish date (yyyy/mm/dd):");
        cont.publishMatrix(mat, date);
        return doShow();
    }
    
    protected boolean doShowCurrentMatrix() {
        System.out.println(headline());
        RiskMatrix matrix=cont.getCurrentMatrix();
        System.out.println("State: "+matrix.getState());
        
        for(Line line:matrix.obtainMatrix()){
            System.out.println("Coverage: "+line.obtainCoverage().obtainName());
            for(BaseColumn c:line.obtainColumns().values()){
                System.out.println(c);
            }
        }
        
        return doShow();
    }
    
    protected boolean showAll() {
        System.out.println(headline());
         System.out.println("=========================");
         System.out.println("     Version - State     ");
         System.out.println("=========================");
         
        for(Pair<MatrixVersion,MatrixState> pair : cont.getAll()){
            System.out.println("   "+ pair.getKey() + " - " + pair.getValue());
        }
        return doShow();
    }

    protected boolean doShowNew() {
        System.out.println("RESETED!");
        cont.newMatrix();
        return doShow();
    }

    @Override
    protected boolean doShow() {
        return true;
    }

    @Override
    public String headline() {
        return "Configurig matrix " + cont.version() + " - " + cont.state();
    }
}
