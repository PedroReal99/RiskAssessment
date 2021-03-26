/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.utils;

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
        
        Writer writer = Files.newBufferedWriter(Paths.get(filename));
       
        RiskFactorRepository rfr = obtainRiskFactorRepository();
        MatrixRepository mr = obtainMatrixRepository();
        
        
        Iterator <RiskFactor> listFactorit = rfr.findAll().iterator();
        
        List<String> srnames = new ArrayList<>();
        List<String> metrics = new ArrayList<>();

        srnames.add("Fatores que contribuem Cobertura");
        metrics.add("");
        
        List<RiskFactor> listFactors = new ArrayList<>();
        
        if(!listFactorit.hasNext()) {
            System.out.println("Sem fatores disponíveis");
            return false;
        } else {
            while(listFactorit.hasNext()) {
                RiskFactor r1 = listFactorit.next();
                listFactors.add(r1);
                srnames.add(r1.obtainSTName());
                metrics.add(r1.obtainMetrics());
            }
            
            String[] srnames1 = new String[listFactors.size()];
            String[] metrics1 = new String[listFactors.size()];
            
            srnames.toArray(srnames1);
            metrics.toArray(metrics1);

            Optional<RiskMatrix> matrix = mr.findById(MatrixVersion.tempVersion());
            
            if(matrix.isPresent()) {
                CSVWriter filewriter = new CSVWriter(writer, DEFAULT_SEPARATOR, NO_QUOTE_CHARACTER, NO_ESCAPE_CHARACTER, DEFAULT_LINE_END);
                filewriter.writeNext(srnames1);
                filewriter.writeNext(metrics1);
                List<Line> l = matrix.get().obtainMatrix();
                for(Line line : l) {
                    String[] cov = new String[l.size()+1];
                    List<String> aux = new ArrayList<>();
                    aux.add(line.obtainCoverage().obtainName());
                    for(RiskFactor r : listFactors) {
                        if(line.obtainColumns().containsValue(new BaseColumn(r))) {
                            aux.add("Sim");
                        } else {
                            aux.add("Não");
                        }
                    }
                    aux.toArray(cov);
                    filewriter.writeNext(cov);
                }
                filewriter.close();
                return true;
            } else {
                System.out.println("No Matrix available");
                return false;
            }
        }
    }
    protected final RiskFactorRepository obtainRiskFactorRepository() {
        return PersistenceContext.repositories().riskRepository();
    }
    
    protected final MatrixRepository obtainMatrixRepository() {
        return PersistenceContext.repositories().riskMatrixRepository();
    }
    
}
