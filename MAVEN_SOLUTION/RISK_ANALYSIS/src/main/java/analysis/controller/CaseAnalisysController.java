/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.controller;

import analysis.utils.CaseParser;
import core.domain.Case.CaseI;
import core.domain.Case.CaseState;
import core.domain.Insurance.ClassificationTables.ClassificationColumn;
import core.domain.Insurance.ClassificationTables.TableLine;
import core.domain.Insurance.InsuranceObject;
import core.domain.RiskFactors.RiskFactor;
import core.persistence.PersistenceContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author Carlos Coelho
 */
public class CaseAnalisysController {

    private final CaseI selected;
    private boolean wasChanged;

    public CaseAnalisysController(CaseI caseI) {
        selected = caseI;
        wasChanged = false;
    }

    public List<String> getDetails() {
        List<String> list = new ArrayList<>();
        for (InsuranceObject ob : selected.getAssociatedInsuranceObjects()) {
            list.add(ob.formattedString());
        }
        return list;
    }

    public void exportToXml(String path) {
        Path file = Paths.get(path + selected.obtainCode() + ".xhtml");
        try {
            Files.write(file, CaseParser.xmlToXhtml(CaseParser.caseToXml(selected)).getBytes("UTF-8"));
        } catch (IOException | ParserConfigurationException ex) {
            ex.printStackTrace();
        }
    }

    public List<String> getResultsByName() {
        List<String> list = new ArrayList<>();
        for (InsuranceObject io : selected.getAssociatedInsuranceObjects()) {
            list.add(io.obtainName());
        }
        return list;
    }

    public void setRiskIndex(String insuranceObject, int index) {
        for (InsuranceObject io : selected.getAssociatedInsuranceObjects()) {
            if (io.obtainName().equals(insuranceObject)) {
                io.addFinalValueOfCalculation(index);
                selected.addUntrackedModification(LoginController.logged.obtainEmail().toString(), "changed risk index of " + insuranceObject + " to " + index);
            }
        }
    }

    public List<String> getRiskFactors() {
        List<String> risks = new ArrayList<>();
        for (InsuranceObject io : selected.getAssociatedInsuranceObjects()) {
            for (TableLine line : io.obtainClassificationTable().obtainMatrix()) {
                String coverage = line.obtainCoverage().obtainName();
                Map<RiskFactor, ClassificationColumn> map = line.obtainColumns();
                for (RiskFactor risk : map.keySet()) {
                    risks.add(io.obtainName() + ": " + coverage + "-" + risk.obtainMetrics() + " " + risk.obtainSTName());
                }
            }
        }
        return risks;
    }

    public List<String> getRelevantLocations(String input) {
        List<String> list = new ArrayList<>();
        for (InsuranceObject io : selected.getAssociatedInsuranceObjects()) {
            for (TableLine line : io.obtainClassificationTable().obtainMatrix()) {
                String coverage = line.obtainCoverage().obtainName();
                Map<RiskFactor, ClassificationColumn> map = line.obtainColumns();
                for (RiskFactor risk : map.keySet()) {
                    if (input.equals(io.obtainName() + ": " + coverage + "-" + risk.obtainMetrics() + " " + risk.obtainSTName())) {
                        list = (map.get(risk).getRelevantSurroundings());
                    }
                }
            }
        }
        return list;
    }

    public void addRelevantLocations(String input, String toAdd) {
        for (InsuranceObject io : selected.getAssociatedInsuranceObjects()) {
            for (TableLine line : io.obtainClassificationTable().obtainMatrix()) {
                String coverage = line.obtainCoverage().obtainName();
                Map<RiskFactor, ClassificationColumn> map = line.obtainColumns();
                for (RiskFactor risk : map.keySet()) {
                    if (input.equals(io.obtainName() + ": " + coverage + "-" + risk.obtainMetrics() + " " + risk.obtainSTName())) {
                        String temp[] = toAdd.split("\\|");
                        for (String s : temp) {
                            map.get(risk).addRelevantSurrounding(s);
                            selected.addUntrackedModification(LoginController.logged.obtainEmail().toString(), "added surrounding " + s + " to " + input);
                        }

                        wasChanged = true;
                        return;
                    }
                }
            }
        }
    }

    public void removeRelevantLocations(String input, String toRemove) {
        for (InsuranceObject io : selected.getAssociatedInsuranceObjects()) {
            for (TableLine line : io.obtainClassificationTable().obtainMatrix()) {
                String coverage = line.obtainCoverage().obtainName();
                Map<RiskFactor, ClassificationColumn> map = line.obtainColumns();
                for (RiskFactor risk : map.keySet()) {
                    if (input.equals(io.obtainName() + ": " + coverage + "-" + risk.obtainMetrics() + " " + risk.obtainSTName())) {
                        if (input.equals(io.obtainName() + ": " + coverage + "-" + risk.obtainMetrics() + " " + risk.obtainSTName())) {
                            if (toRemove.equals("-1")) {
                                map.get(risk).setRelevantSurroundings(new ArrayList<>());
                                selected.addUntrackedModification(LoginController.logged.obtainEmail().toString(), "removed all surroundings from " + input);
                                return;
                            }
                            String temp[] = toRemove.split("\\|");
                            List<String> list = (List<String>) Arrays.asList(temp);
                            Collections.sort(list, Comparator.reverseOrder());
                            for (String s : list) {
                                int i;
                                try {
                                    i = Integer.parseInt(s);
                                    map.get(risk).removeRelevantSurrounding(i - 1);
                                    selected.addUntrackedModification(LoginController.logged.obtainEmail().toString(), "removed surrounding " + s + " from " + input);
                                } catch (NumberFormatException ex) {
                                }
                            }
                            wasChanged = true;
                            return;
                        }
                    }
                }
            }
        }
    }

    public boolean wasChanged() {
        return this.wasChanged;
    }

    public void reevaluate() {
        selected.alterCaseStatus(LoginController.logged.obtainEmail().toString(),CaseState.WAITING);
        PersistenceContext.repositories().caseRepository().save(selected);
    }

    public void addComment(String comment) {
        selected.addComment(LoginController.logged.obtainEmail().toString(), comment);
    }

    public void confirmAssessement() {
        selected.alterCaseStatus(LoginController.logged.obtainEmail().toString(),CaseState.PROCESSED);
        PersistenceContext.repositories().caseRepository().save(selected);
    }

}
