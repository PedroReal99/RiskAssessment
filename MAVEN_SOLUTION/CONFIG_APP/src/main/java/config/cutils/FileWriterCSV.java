/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.cutils;

import com.opencsv.CSVWriter;
import static com.opencsv.CSVWriter.DEFAULT_LINE_END;
import static com.opencsv.CSVWriter.NO_ESCAPE_CHARACTER;
import static com.opencsv.CSVWriter.NO_QUOTE_CHARACTER;
import core.domain.RiskFactors.RiskFactor;
import core.persistence.PersistenceContext;
import core.persistence.RiskFactorRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import core.domain.RiskMatrixs.Columns.BaseColumn;
import core.domain.RiskMatrixs.Line;
import core.domain.RiskMatrixs.MatrixVersion;
import core.domain.RiskMatrixs.RiskMatrix;
import core.persistence.MatrixRepository;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

/**
 *
 * @author Vasco_Rodrigues
 */
public class FileWriterCSV {

    private static final char DEFAULT_SEPARATOR = ';';

    public boolean exportMeaninfulRiskFactors(String filename) throws IOException {
        //FIXME move logic to the right class
        Writer writer = Files.newBufferedWriter(Paths.get(filename));

        RiskFactorRepository rfr = obtainRiskFactorRepository();
        MatrixRepository mr = obtainMatrixRepository();

        Iterator<RiskFactor> listFactorIt = rfr.findAll().iterator();

        Optional<RiskMatrix> optMatrix = mr.findById(MatrixVersion.tempVersion());
        RiskMatrix matrix;
        if (!optMatrix.isPresent()) {
            optMatrix = mr.findById(MatrixVersion.create("BASE TEST"));

            if (!optMatrix.isPresent()) {
                System.out.println("ERROR: Matrixes not found!");
                return false;
            }
        }

        matrix = optMatrix.get();

        List<Line> l = matrix.obtainMatrix();

        int size = countRiskFactors(rfr) + 1;

        String[] srnames = new String[size];
        String[] metrics = new String[size];

        srnames[0] = ("Fatores que contribuem Cobertura");
        metrics[0] = ("");

        List<RiskFactor> listFactors = new ArrayList<>();

        if (!listFactorIt.hasNext()) {
            System.out.println("Sem fatores disponíveis");
            return false;
        } else {

            int i = 1;
            while (listFactorIt.hasNext()) {
                RiskFactor r1 = listFactorIt.next();
                listFactors.add(r1);
                srnames[i] = r1.obtainSTName();
                metrics[i] = r1.obtainMetrics();
                i++;
            }

            CSVWriter filewriter = new CSVWriter(writer, DEFAULT_SEPARATOR, NO_QUOTE_CHARACTER, NO_ESCAPE_CHARACTER, DEFAULT_LINE_END);
            filewriter.writeNext(srnames);
            filewriter.writeNext(metrics);
            for (Line line : l) {
                String[] cov = new String[size];
                //List<String> aux = new ArrayList<>();
                cov[0] = line.obtainCoverage().obtainName();
                i=1;
                for (RiskFactor r : listFactors) {
                    if (line.obtainColumns().containsKey(new BaseColumn(r))) {
                        cov[i] = "Sim";
                    } else {
                        cov[i] = "Não";
                    }
                    i++;
                }
                filewriter.writeNext(cov);
            }
            filewriter.close();
            return true;
        }
    }

    protected final RiskFactorRepository obtainRiskFactorRepository() {
        return PersistenceContext.repositories().riskRepository();
    }

    protected final MatrixRepository obtainMatrixRepository() {
        return PersistenceContext.repositories().riskMatrixRepository();
    }
    
    private static int countRiskFactors(RiskFactorRepository rfr) {
        Iterator<RiskFactor> rf = rfr.findAll().iterator();
        int size = 0;
        while(rf.hasNext()) {
            rf.next();
            size++;
        }
        return size;
    }

}
