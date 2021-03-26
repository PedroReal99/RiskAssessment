package core.controller;

import com.mycompany.georeference_interface.GeoRefServiceDTO;
import com.mycompany.georeference_interface.TravelMethod;
import core.domain.Case.CaseCode;
import core.domain.Case.CaseI;
import core.domain.Coverage.Coverage;
import core.domain.Insurance.ClassificationTables.TableLine;
import core.domain.Insurance.InsuranceObject;
import core.domain.RiskFactors.RiskFactor;
import core.domain.RiskMatrixs.Columns.BaseColumn;
import core.domain.RiskMatrixs.Line;
import core.dto.GeoRefInfoDTO;
import core.grammar.Grammar;
import core.persistence.PersistenceContext;
import georeference.GeoreferenceServiceController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Ricardo Branco
 */
public class ExternalGeoreferenceDataController {

    private String caseCode;

    public ExternalGeoreferenceDataController(String caseCode) {
        this.caseCode = caseCode;
    }

    public String data() {
        String data = "";
        Grammar g;
        List<GeoRefInfoDTO> dtos = getCalculationInfo();
        for (GeoRefInfoDTO dto : dtos) {
            data += dto.surrounding;
            g = new Grammar(dto.distance);
            data += ";" + g.parse();
            g = new Grammar(dto.duration);
            data += ";" + g.parse();
            g = new Grammar(dto.quantity);
            data += ";" + g.parse();
            data += "|";
        }
        return data;
    }

    public String data_grammar() throws IOException {
        String data = "";
        Grammar g;
        List<GeoRefInfoDTO> dtos = getCalculationInfo();
        for (GeoRefInfoDTO dto : dtos) {
            data += dto.surrounding;
            g = new Grammar(dto.distance);
            data += ";" + g.parseWithListener();
            g = new Grammar(dto.duration);
            data += ";" + g.parseWithListener();
            g = new Grammar(dto.quantity);
            data += ";" + g.parseWithListener();
            data += "|";
        }
        return data;
    }

    private List<GeoRefInfoDTO> getCalculationInfo() {
        List<GeoRefInfoDTO> dtos = new ArrayList<>();
        GeoreferenceServiceController c = new GeoreferenceServiceController();

        CaseI cas = PersistenceContext.repositories().caseRepository().findByCaseCode(new CaseCode(this.caseCode));
        List<Line> lines = PersistenceContext.repositories().riskMatrixRepository().getInForceMatrix().obtainMatrix();

        List<InsuranceObject> io = cas.getAssociatedInsuranceObjects();

        for (InsuranceObject i : io) {
            List<TableLine> tl = i.obtainClassificationTable().obtainMatrix();

            for (TableLine t : tl) {
                Coverage cov = t.obtainCoverage();

                for (Line l : lines) {
                    Map<RiskFactor, BaseColumn> m = l.obtainColumns();

                    if (cov.equals(l.obtainCoverage())) {

                        for (RiskFactor r : m.keySet()) {
                            String metric = r.obtainMetrics();
                            String stname = r.obtainSTName();
                            Set<GeoRefServiceDTO> dto = c.getSurroundings(i.obtainLocation(), stname);

                            for (GeoRefServiceDTO d : dto) {
                                GeoRefServiceDTO aux = c.getDistanceAndDuration(i.obtainLocation(), d.name + "," + d.district, TravelMethod.TRANSIT);
                                dtos.add(new GeoRefInfoDTO(String.valueOf(aux.duration), String.valueOf(aux.distance), String.valueOf(dto.size()), d.name));
                            }
                        }
                    }
                }
            }
        }
        return dtos;
    }
}
