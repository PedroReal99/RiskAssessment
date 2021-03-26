/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.importjson;

import core.domain.Case.CaseI;
import core.domain.Case.CaseCode;
import core.domain.Case.CaseState;
import core.domain.Case.CaseType;
import core.domain.RiskMatrixs.MatrixState;
import core.persistence.CaseRepository;
import core.persistence.CoverageRepository;
import core.persistence.PersistenceContext;
import core.persistence.RiskFactorRepository;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * https://crunchify.com/how-to-read-json-object-from-file-in-java/
 *
 * @author Pedro
 */
public class JsonImport {

    private final CaseRepository caseRepository = PersistenceContext.repositories().caseRepository();
    private final CoverageRepository coverageRepository = PersistenceContext.repositories().coverageRepository();
    private final RiskFactorRepository riskRepository = PersistenceContext.repositories().riskRepository();

    /*
     * public List<String> JsonFileReader(String nomeFich) throws
     * FileNotFoundException, IOException, ParseException { JSONParser jp = new
     * JSONParser(); List<String> casesList = new LinkedList<>(); JSONObject
     * jObj = (JSONObject) jp.parse(new FileReader(nomeFich));
     *
     * casesList.add((String) jObj.get("Codigo")); casesList.add((String)
     * jObj.get("Tipo")); casesList.add((String) jObj.get("Estado")); JSONArray
     * ja2 = (JSONArray) jObj.get("Objeto Seguro"); for (int j = 0; j <
     * ja2.size(); j++) { String objSeg = (String) ja2.get(j);
     * casesList.add(objSeg); }
     *
     * return casesList; }
     *
     *
     * @param nomeFich
     * @return
     * @throws java.io.IOException
     * @throws org.json.simple.parser.ParseException *
     */
//    public CaseI createCase(String nomeFich) throws IOException, ParseException {
//        List<String> cases = new LinkedList<>();
//        try {
//            cases = JsonFileReader(nomeFich);
//        } catch (FileNotFoundException ex) {
//        }
//        if (cases.isEmpty()) {
//            return null;
//        } else {
//            CaseCode code = new CaseCode(cases.get(0));
//            Optional<CaseI> casee = caseRepository.findById(code);
//            if (casee.isPresent()) {
//                System.out.println("O caso com o código " + code + " já existe!");
//                //FIXME return null ?? nao faz sentido criar um se ja existe
//                //return null;
//                getExistingCases(nomeFich);
//            }
//            return createNewCase(cases);
//        }
//    }

//    private CaseI createNewCase(List<String> cases) {
//        CaseI caseI = null;
//        Iterator<String> it = cases.iterator();
//        CaseCode code = new CaseCode(it.next());
//        CaseType type = new CaseType(it.next());
//        CaseState state = new CaseState(it.next());
//
//        caseI = new CaseI(type, state, code);
//        while (it.hasNext()) {
//            InsuranceName name = new InsuranceName(it.next());
//            String lo[] = it.next().split("-");
//            GPSLocation location = new GPSLocation(Float.parseFloat(lo[0]), Float.parseFloat(lo[1]), Float.parseFloat(lo[2]));
//            String s[] = it.next().split(";");
//            List<String> list = new ArrayList<>();
//            for (String v : s) {
//                list.add(v);
//            }
//            CalculationDetail detail = new CalculationDetail(list);
//            RiskIndex index = new RiskIndex(Integer.parseInt(it.next()));
//            String covName=it.next();
//            System.out.println("Coverage name is "+covName);
//            Coverage c = coverageRepository.findByName(new CoverageName(covName));
//
//            STName st = new STName(it.next());
//            Metric metrics = new Metric(it.next());
//
//            RiskFactor r = riskRepository.findByBothParameters(metrics, st);
//
//            Map<RiskFactor, ClassificationColumn> map = new HashMap<>();
//            Classification cla = new Classification(it.next());
//            ClassificationColumn column = new ClassificationColumn(r, cla);
//            map.put(r, column);
//
//            TableLine tableline = new TableLine(map, c);
//            List<TableLine> lista = new ArrayList<>();
//            lista.add(tableline);
//            ClassificationTable ct = new ClassificationTable(lista);
//            InsuranceObject insurance = new InsuranceObject(name, location, detail, index, ct);
//            caseI.associateInsurance(insurance);
//        }
//        return caseI;
//    }

    public CaseI getExistingCases(String nomeFich) throws IOException, ParseException {
        List<String> casesList = new LinkedList<>();
        try {
            casesList = JsonFileReader(nomeFich);
        } catch (FileNotFoundException ex) {
        }

        //for(List<String> list: casesList){
        CaseCode code = new CaseCode(casesList.get(0));
        CaseType type = new CaseType(casesList.get(1));
        CaseState state = new CaseState(casesList.get(2));

        Optional<CaseI> casee = caseRepository.findById(code);
        if (casee.isPresent()) {
            if (casee.get().obtainTypeObject().equals(type) && casee.get().obtainStateObject().equals(state)) {
                return casee.get();
            } else {
                System.out.println("O caso com o código " + code + " não corresponde ao tipo e ao estado do ficheiro!");
            }
        } else {
            System.out.println("O caso com o código " + code + " não existe");
        }
        //}
        return null;
    }

    public List<String> JsonFileReader(String nomeFich) throws IOException, ParseException {
        JSONParser jp = new JSONParser();
        List<String> casesList = new LinkedList<>();
        JSONObject jObj = (JSONObject) jp.parse(new FileReader(nomeFich));
        casesList.add((String) jObj.get("Codigo"));
        casesList.add((String) jObj.get("Tipo"));
        casesList.add((String) jObj.get("Estado"));
        JSONArray ja2 = (JSONArray) jObj.get("Objeto Seguro");
        for (int k = 0; k < ja2.size(); k++) {
            JSONObject j3 = (JSONObject) ja2.get(k);
            casesList.add((String) j3.get("nome"));
            casesList.add((String) j3.get("localizacao"));
            casesList.add((String) j3.get("detalhes"));
            casesList.add((String) j3.get("indice"));
            JSONArray ja4 = (JSONArray) j3.get("tabela");
            for (int m = 0; m < ja4.size(); m++) {
                JSONObject j5 = (JSONObject) ja4.get(m);
                //casesList.add((String) j5.get("versao"));
                //casesList.add((String) j5.get("estado"));
                JSONArray ja6 = (JSONArray) j5.get("linha");
                for (int n = 0; n < ja6.size(); n++) {
                    JSONObject j7 = (JSONObject) ja6.get(n);
                    casesList.add((String) j7.get("covertura"));
                    casesList.add((String) j7.get("factor risco"));
                    casesList.add((String) j7.get("metrica"));
                    casesList.add((String) j7.get("classificacao"));
                }
            }

        }
        return casesList;
    }

    private MatrixState obtainMatrixState(String next) {
        if (next.equals("BASE")) {
            return MatrixState.BASE;
        }
        if (next.equals("DEFINED")) {
            return MatrixState.DEFINED;
        }
        if (next.equals("DETAILED")) {
            return MatrixState.DETAILED;
        }
        if (next.equals("PUBLISHED")) {
            return MatrixState.PUBLISHED;
        } else {
            return MatrixState.OBSOLETE;
        }
    }

}
