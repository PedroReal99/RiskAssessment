package config.ui;

import config.controller.CoverageController;
import config.controller.RiskMatrixController;
import core.domain.Coverage.Coverage;
import core.domain.RiskMatrixs.Line;
import core.domain.RiskMatrixs.RiskMatrix;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CheckCoveragesInMatrixUI extends AbstractUI {

    private final RiskMatrixController rm = new RiskMatrixController();

    private final CoverageController c = new CoverageController();

    protected Controller controller() {
        return this.c;
    }

    @Override
    protected boolean doShow() {
        String name = headline();
        Iterable<Line> listInMatrix;
        RiskMatrix riskMatrix = rm.getMatrix(name);
        Iterable<Coverage> list = c.getAll();
        try {
            listInMatrix = riskMatrix.obtainMatrix();
        } catch (NullPointerException e) {
            System.out.println("Name doesn't match with any matrix");
            return doShow();
        }
        ArrayList<Coverage> list1 = new ArrayList<>();
        ArrayList<Coverage> list2 = new ArrayList<>();
        for (Coverage c : list) {
            list1.add(c);
        }
        for (Line line : listInMatrix) {
            list2.add(line.obtainCoverage());
        }
        if (list2.size() == 0) {
            System.out.println("Matrix has no coverages.");
            return true;
        }
        checkCoverages(list1, list2);
        if (list1.size() == 0) {
            System.out.println("Matrix has all coverages.");
        } else {
            System.out.println("Missing coverages.");
            for (Coverage c : list1) {
                System.out.println(c.toString());
            }
        }
        return true;
    }

    private void checkCoverages(ArrayList<Coverage> list1, ArrayList<Coverage> list2) {
        for (Iterator<Coverage> it = list1.iterator(); it.hasNext(); ) {
            Coverage aCoverage = it.next();
            if (list2.contains(aCoverage)) {
                it.remove();
            }
        }
    }

    @Override
    public String headline() {
        return Console.readLine("Insert risk matrix name:");
    }
}
