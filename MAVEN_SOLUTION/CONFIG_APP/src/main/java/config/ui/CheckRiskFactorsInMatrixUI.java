package config.ui;

import config.controller.RiskFactorController;
import config.controller.RiskMatrixController;
import core.domain.RiskFactors.RiskFactor;
import core.domain.RiskMatrixs.Line;
import core.domain.RiskMatrixs.RiskMatrix;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.ArrayList;
import java.util.Iterator;

public class CheckRiskFactorsInMatrixUI extends AbstractUI {

    private final RiskMatrixController rm = new RiskMatrixController();

    private final RiskFactorController c = new RiskFactorController();

    protected Controller controller() {
        return this.c;
    }

    @Override
    protected boolean doShow() {
        String name = headline();
        Iterable<Line> listInMatrix;
        RiskMatrix riskMatrix = rm.getMatrix(name);
        Iterable<RiskFactor> list = c.listRiskFactors();
        ArrayList<RiskFactor> list1 = new ArrayList<>();
        ArrayList<RiskFactor> list2 = new ArrayList<>();
        try {
            listInMatrix = riskMatrix.obtainMatrix();
        } catch (NullPointerException e) {
            System.out.println("Name doesn't match with any matrix");
             return doShow();
        }
        for (RiskFactor rf : list) {
            list1.add(rf);
        }
        for (Line line : listInMatrix) {
            list2.addAll(line.obtainColumns().keySet());
        }
        if (list2.isEmpty()) {
            System.out.println("Matrix has no risk factors");
            return true;
        }
        checkRiskFactors(list1, list2);
        if (list1.isEmpty()) {
            System.out.println("Matrix has all risk factors.");
        } else {
            System.out.println("Missing risk factors:");
            for (RiskFactor c : list1) {
                System.out.println(c.toString());
            }
        }
        return true;

    }

    private void checkRiskFactors(ArrayList<RiskFactor> list1, ArrayList<RiskFactor> list2) {
        for (Iterator<RiskFactor> it = list1.iterator(); it.hasNext(); ) {
            RiskFactor aRF = it.next();
            if (list2.contains(aRF)) {
                it.remove();
            }
        }
    }

    @Override
    public String headline() {
        return Console.readLine("Insert risk matrix name:");
    }
}
