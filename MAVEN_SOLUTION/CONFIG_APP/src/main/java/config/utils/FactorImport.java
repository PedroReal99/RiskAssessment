/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.Utils;

import core.domain.RiskFactors.MetricStrategy;
import core.domain.Coverage.Coverage;
import core.domain.RiskFactors.RiskFactor;
import core.domain.Surrounding.STName;
import core.domain.Surrounding.SurroundingType;
import static core.domain.RiskMatrixs.Attributes.Contribution.NEGATIVE;
import static core.domain.RiskMatrixs.Attributes.Contribution.POSITIVE;
import static core.domain.RiskMatrixs.Attributes.NeedOfAnalisys.FACULTATIVE;
import static core.domain.RiskMatrixs.Attributes.NeedOfAnalisys.OBLIGATORY;
import core.domain.RiskMatrixs.Attributes.Weight;
import core.domain.RiskMatrixs.Columns.BaseColumn;
import core.domain.RiskMatrixs.Columns.ColumnBuilder;
import core.domain.RiskMatrixs.Columns.DefinedColumn;
import core.domain.RiskMatrixs.Columns.DefinedColumnBuilder;
import core.domain.RiskMatrixs.MatrixBuilder;
import core.persistence.CoverageRepository;
import core.persistence.PersistenceContext;
import core.persistence.RiskFactorRepository;
import core.persistence.SurroundingTypeRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import config.utils.FileReaderCSV;
import core.domain.Coverage.CoverageName;
import core.domain.RiskFactors.Metric;

/**
 *
 * @author morei
 */
public class FactorImport {

    private final CoverageRepository coverageRepository = PersistenceContext.repositories().coverageRepository();
    private final RiskFactorRepository riskFactorRepository = PersistenceContext.repositories().riskRepository();
    private final SurroundingTypeRepository surroundingTypeRepository = PersistenceContext.repositories().surroundingTypeRepository();

    public FactorImport() {
    }

    public List<RiskFactor> getListRiskFactor(String[] f1, String[] f2){
        List<RiskFactor> riskList = new ArrayList<>();
        int i = 0, j = 1;
        SurroundingType surroundingType = null;
        for (int k = 1; k < f2.length; k++) {
            if (f1[k] != null) {
                if (surroundingType == null || !surroundingType.obtainSTName().toString().equals(f1[k])) {
                    if (f1[k].isEmpty() || f1[k].equals("")) {
                        surroundingType = surroundingTypeRepository.findByName(new STName(f1[i]));
                    } else {
                        surroundingType = surroundingTypeRepository.findByName(new STName(f1[k]));
                        i = k;
                    }
                }
            }
            MetricStrategy metrics = Metric.valueOf(f2[j].toUpperCase());
            RiskFactor riskFactor1 = riskFactorRepository.findByBothParameters(metrics, surroundingType.obtainSTName());
            j++;
            riskList.add(riskFactor1);
        }
        return riskList;
    }

    public void importFactorValues(String fileName, MatrixBuilder mb) throws IOException{
        FileReaderCSV fileReader = new FileReaderCSV(fileName);
        String line1[] = fileReader.nextLine();
        String line2[] = fileReader.nextLine();
        String lineP[] = null;
        List<RiskFactor> listRisk = getListRiskFactor(line1, line2);
        while ((lineP = fileReader.nextLine()) != null) {
            Coverage c = coverageRepository.findByName(new CoverageName(lineP[0]));
            addColumnToMatrix(c, listRisk, lineP, line2[0], mb);
        }
    }

    public void addColumnToMatrix(Coverage c, List<RiskFactor> listRisk, String line[], String tipo, MatrixBuilder mb) {
        int i = 1;
        DefinedColumn df = null;
        DefinedColumnBuilder cb = null;
        for (RiskFactor rf : listRisk) {
            addToMatrix(mb, tipo, rf, line, cb, c, i);
            i++;
        }
    }

    public void addToMatrix(MatrixBuilder mb, String tipo, RiskFactor rf, String line[], DefinedColumnBuilder cb, Coverage c, int i) {
        if (tipo.equalsIgnoreCase("Peso")) {
            cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
            cb.setWeight(new Weight(Integer.parseInt(line[i])));
            cb.setRiskFactor(rf);
            mb.addColumnBuilder(c, (ColumnBuilder) cb);
        } else if (tipo.equalsIgnoreCase("Necessidade")) {
            if (line[i].equalsIgnoreCase("Obrigatorio")) {
                cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
                cb.setNeed(OBLIGATORY);
                cb.setRiskFactor(rf);
                mb.addColumnBuilder(c, (ColumnBuilder) cb);
            } else {
                cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
                cb.setNeed(FACULTATIVE);
                cb.setRiskFactor(rf);
                mb.addColumnBuilder(c, (ColumnBuilder) cb);
            }
        } else {
            if (line[i].equalsIgnoreCase("Positivo")) {
                cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
                cb.setContribution(POSITIVE);
                cb.setRiskFactor(rf);
                mb.addColumnBuilder(c, (ColumnBuilder) cb);
            } else {
                cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
                cb.setContribution(NEGATIVE);
                cb.setRiskFactor(rf);
                mb.addColumnBuilder(c, (ColumnBuilder) cb);
            }
        }
    }

    public boolean importMeaningfulFactors(String fileName, MatrixBuilder mb) throws IOException {
        FileReaderCSV fileReader = new FileReaderCSV(fileName);
        String line1[] = fileReader.nextLine();
        String line2[] = fileReader.nextLine();
        String lineP[] = null;
        List<RiskFactor> listRisk = getListRiskFactor(line1, line2);
        while ((lineP = fileReader.nextLine()) != null) {
            Coverage c = coverageRepository.findByName(new CoverageName(lineP[0]));
            for (int i = 1; i < lineP.length; i++) {
                if (lineP[i].equalsIgnoreCase("Sim")) {
                    mb.addColumnBuilder(c, new ColumnBuilder(new BaseColumn(listRisk.get(i - 1))));
                }
            }
        }
        return true;
    }

    public boolean imporBaseRiskMatrix(String fileName, MatrixBuilder matrixBuilder) throws IOException {
        FileReaderCSV fileReader = new FileReaderCSV(fileName);
        String firstLine[] = fileReader.nextLine();
        String secondLine[] = fileReader.nextLine();
        String line[] = null;
        List<RiskFactor> listRisk = getListRiskFactor(firstLine, secondLine);
        while ((line = fileReader.nextLine()) != null) {
            Coverage coverage = coverageRepository.findByName(new CoverageName(line[0]));
            for (int i = 1; i < line.length; i++) {
                matrixBuilder.addColumnBuilder(coverage, new ColumnBuilder(new BaseColumn(listRisk.get(i - 1))));
                // matrixBuilder.setColumn(coverage, new BaseColumn(listRisk.get(i - 1)));

            }
        }

        return true;
    }

    public boolean imporDetailedRiskMatrix(String fileName, MatrixBuilder matrixBuilder) throws IOException {
        FileReaderCSV fileReader = new FileReaderCSV(fileName);
        String firstLine[] = fileReader.nextLine();
        String secondLine[] = fileReader.nextLine();
        String line1[] = null;
        String line2[] = null;
        String line3[] = null;

        List<RiskFactor> listRisk = getListRiskFactor(firstLine, secondLine);
        while ((line1 = fileReader.nextLine()) != null && (line2 = fileReader.nextLine()) != null && (line3 = fileReader.nextLine()) != null) {
            Coverage coverage = coverageRepository.findByName(new CoverageName(line1[0]));
            for (int i = 1; i < line1.length; i++) {
                // matrixBuilder.setColumn(coverage, new DetailedColumn(listRisk.get(i), null, null, null, new Scale(new RiskLevel(Integer.parseInt(line1[i])), new RiskLevel(Integer.parseInt(line2[i])), new RiskLevel(Integer.parseInt(line3[i])))));

            }
        }

        return true;
    }

}
