/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.bootstrap;

import core.domain.Case.CaseCode;
import core.domain.Case.CaseDate;
import core.domain.Case.CaseI;
import core.domain.Case.CasePriority;
import core.domain.Case.CaseState;
import core.domain.Case.CaseType;
import core.domain.Coverage.Coverage;
import core.domain.Coverage.CoverageName;
import core.domain.Insurance.CalculationDetail;
import core.domain.Insurance.Classification;
import core.domain.Insurance.ClassificationTables.ClassificationColumn;
import core.domain.Insurance.ClassificationTables.ClassificationTable;
import core.domain.Insurance.ClassificationTables.TableLine;
import core.domain.Insurance.InsuranceName;
import core.domain.Insurance.InsuranceObject;
import core.domain.location.PostLocation;
import core.domain.Insurance.RiskIndex;
import core.domain.RiskFactors.Metric;
import core.domain.RiskFactors.RiskFactor;
import core.domain.Surrounding.STName;
import core.domain.RiskMatrixs.Attributes.Contribution;
import core.domain.RiskMatrixs.Attributes.NeedOfAnalisys;
import core.domain.RiskMatrixs.Attributes.RiskLevel;
import core.domain.RiskMatrixs.Attributes.Scale;
import core.domain.RiskMatrixs.Attributes.Weight;
import core.domain.RiskMatrixs.Columns.ColumnBuilder;
import core.domain.RiskMatrixs.Columns.DefinedColumnBuilder;
import core.domain.RiskMatrixs.Columns.DetailedColumnBuilder;
import core.domain.RiskMatrixs.MatrixBuilder;
import core.domain.RiskMatrixs.MatrixVersion;
import core.domain.RiskMatrixs.RiskMatrix;
import core.domain.User.Email;
import core.domain.User.User;
import core.persistence.CaseRepository;
import core.persistence.MatrixRepository;
import core.persistence.PersistenceContext;
import core.persistence.UserRepository;
import eapli.framework.actions.Action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author morei
 */
public class CaseBootstrap implements Action {

    @Override
    public boolean execute() {
        try {
            instance4.associateEmployee("himself", new Email("ar1@gmail.com"));
            instance4.validatedDate(new CaseDate("2019-06-16 00:00:00"));
            instance4.validationStartDate(new CaseDate("2019-06-15 00:00:00"));
            instance5.associateEmployee("himself", new Email("ar1@gmail.com"));
            instance5.validatedDate(new CaseDate("2019-06-17 00:00:00"));
            instance5.validationStartDate(new CaseDate("2019-06-15 00:00:00"));
            instance6.associateEmployee("himself", new Email("ar1@gmail.com"));
            instance6.validatedDate(new CaseDate("2019-06-18 00:00:00"));
            instance6.validationStartDate(new CaseDate("2019-06-15 00:00:00"));
            instance7.associateEmployee("himself", new Email("ar1@gmail.com"));

            matrixRepository.save(createMatrixTest());
            matrixRepository.save(createMatrixTest2());
            caseRepository.save(createCaseTest1());
            caseRepository.save(createCaseTest2());
            caseRepository.save(createCaseTest3());
            caseRepository.save(createCaseTest4());
            caseRepository.save(createCaseTest5());
            caseRepository.save(createCaseTest6());
            caseRepository.save(instance4);
            caseRepository.save(instance5);
            caseRepository.save(instance6);
            caseRepository.save(instance7);
            caseRepository.save(instance8);
            return true;
        } catch (Exception ex) {
            System.out.println("Bootstrap error");
            return false;
        }
    }

    static CaseI instance4 = new CaseI(new CaseType("type"), CaseState.PROCESSED, new CaseCode("code4"), false, CasePriority.MAX, new CaseDate("2004-06-07 00:00:00"));
    static CaseI instance5 = new CaseI(new CaseType("type"), CaseState.PROCESSED, new CaseCode("code5"), false, CasePriority.MAX, new CaseDate("2004-06-06 00:00:00"));
    static CaseI instance6 = new CaseI(new CaseType("type"), CaseState.PROCESSED, new CaseCode("code6"), false, CasePriority.MAX, new CaseDate("2003-06-05 00:00:00"));
    static CaseI instance7 = new CaseI(new CaseType("type"), CaseState.VALIDATING, new CaseCode("code7"), false, CasePriority.MAX, new CaseDate("2003-6-06 00:00:00"));
    static CaseI instance8 = new CaseI(new CaseType("type"), CaseState.PROCESSING, new CaseCode("code8"), false, CasePriority.MAX, new CaseDate("2003-6-06 00:00:00"));


    List<String> detail = new ArrayList<>();
    CaseCode ccc1 = new CaseCode("Code");
    CaseType ct1 = new CaseType("Aceite");
    CaseState cs1 = new CaseState("Finalizada");
    CasePriority cp1 = CasePriority.MAX;
    List<InsuranceObject> insurances = new ArrayList<>();
    CaseI case1 = new CaseI(ct1, cs1, ccc1, insurances, false, cp1, new CaseDate("06/06/06"));
    RiskFactor RSK1 = new RiskFactor(Metric.DISTANCIA, new STName("Bombeiros"));
    RiskFactor RSK2 = new RiskFactor(Metric.QUANTIDADE, new STName("IE Saúde"));
    RiskFactor RSK3 = new RiskFactor(Metric.QUANTIDADE, new STName("Zona Urbana"));
    RiskFactor RSK4 = new RiskFactor(Metric.DISTANCIA, new STName("Policia"));
    RiskFactor RSK5 = new RiskFactor(Metric.QUANTIDADE, new STName("Médicos"));
    RiskFactor RSK6 = new RiskFactor(Metric.QUANTIDADE, new STName("Policias"));
    Classification c1 = new Classification("HIGH");
    Classification c2 = new Classification("LOW");
    Classification c3 = new Classification("MEDIUM");
    Classification c4 = new Classification("(não detetado)");
    Classification c5 = new Classification("HIGH");
    Classification c6 = new Classification("(não detetado)");
    ClassificationColumn cc1 = new ClassificationColumn(RSK1, c1);
    ClassificationColumn cc2 = new ClassificationColumn(RSK2, c2);
    ClassificationColumn cc3 = new ClassificationColumn(RSK3, c3);
    ClassificationColumn cc4 = new ClassificationColumn(RSK4, c4);
    ClassificationColumn cc5 = new ClassificationColumn(RSK5, c5);
    ClassificationColumn cc6 = new ClassificationColumn(RSK6, c6);
    ClassificationColumn cc7 = new ClassificationColumn(RSK6, c1);
    Map<RiskFactor, ClassificationColumn> map1 = new HashMap<>();
    Map<RiskFactor, ClassificationColumn> map2 = new HashMap<>();
    Map<RiskFactor, ClassificationColumn> map3 = new HashMap<>();
    Coverage cvg1 = new Coverage(new CoverageName("Incendios"));
    Coverage cvg2 = new Coverage(new CoverageName("Tempestades"));
    Coverage cvg3 = new Coverage(new CoverageName("Tsunamis"));
    TableLine tb1 = new TableLine(map1, cvg1);
    TableLine tb2 = new TableLine(map2, cvg2);
    TableLine tb3 = new TableLine(map3, cvg3);
    Scale sc1 = new Scale(new RiskLevel(1), new RiskLevel(3), new RiskLevel(5));
    Scale sc2 = new Scale(new RiskLevel(2), new RiskLevel(4), new RiskLevel(6));
    Scale sc3 = new Scale(new RiskLevel(3), new RiskLevel(6), new RiskLevel(9));
    List<TableLine> list = new ArrayList<>();
    List<TableLine> list2 = new ArrayList<>();
    ClassificationTable t = new ClassificationTable(list);
    ClassificationTable t2 = new ClassificationTable(list);
    InsuranceObject io = new InsuranceObject(new InsuranceName("Casa"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(detail), new RiskIndex(-1), t);
    InsuranceObject io2 = new InsuranceObject(new InsuranceName("Telemóvel"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(detail), new RiskIndex(-1), t2); //List não está a ser preenchida

    CaseRepository caseRepository = PersistenceContext.repositories().caseRepository();
    MatrixRepository matrixRepository = PersistenceContext.repositories().riskMatrixRepository();
    UserRepository userRepository = PersistenceContext.repositories().UserRepository();

    public CaseI createCaseTest1() {
        Map<RiskFactor, ClassificationColumn> map1test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map2test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map3test = new HashMap<>();
        map1test.put(RSK1, cc1);
        map1test.put(RSK2, cc2);
        map2test.put(RSK3, cc3);
        map2test.put(RSK4, cc4);
        map3test.put(RSK5, cc5);
        map3test.put(RSK6, cc6);
        TableLine tb1test = new TableLine(map1test, cvg1);
        TableLine tb2test = new TableLine(map2test, cvg2);
        TableLine tb3test = new TableLine(map3test, cvg3);
        List<TableLine> lt = new ArrayList<>();
        lt.add(tb1test);
        lt.add(tb2test);
        lt.add(tb3test);
        ClassificationTable t1 = new ClassificationTable(lt);
        List<InsuranceObject> li = new ArrayList<>();
        InsuranceObject ioteste = new InsuranceObject(new InsuranceName("Casa"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(new ArrayList<>()), new RiskIndex(-1), t1);
        InsuranceObject io2teste = new InsuranceObject(new InsuranceName("Telemóvel"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(detail), new RiskIndex(-1), t1);
        li.add(ioteste);
        li.add(io2teste);
        CaseI c = new CaseI(ct1, cs1, ccc1, li, false, cp1, new CaseDate("06/06/2016"), new Email("admin1@gmail.com"));
        return c;
    }

    public CaseI createCaseTest2() {
        Map<RiskFactor, ClassificationColumn> map1test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map2test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map3test = new HashMap<>();
        map1test.put(RSK1, cc1);
        map1test.put(RSK2, cc2);
        map2test.put(RSK3, cc3);
        map2test.put(RSK4, cc4);
        map3test.put(RSK5, cc5);
        map3test.put(RSK6, cc6);
        TableLine tb1test = new TableLine(map1test, cvg1);
        TableLine tb2test = new TableLine(map2test, cvg2);
        TableLine tb3test = new TableLine(map3test, cvg3);
        List<TableLine> lt = new ArrayList<>();
        lt.add(tb1test);
        lt.add(tb2test);
        lt.add(tb3test);
        ClassificationTable t1 = new ClassificationTable(lt);
        List<InsuranceObject> li = new ArrayList<>();
        InsuranceObject ioteste = new InsuranceObject(new InsuranceName("Casa"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(new ArrayList<>()), new RiskIndex(-1), t1);
        InsuranceObject io2teste = new InsuranceObject(new InsuranceName("Telemóvel"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(detail), new RiskIndex(-1), t1);
        li.add(ioteste);
        li.add(io2teste);
        CaseI c = new CaseI(ct1, CaseState.VALIDATING, new CaseCode("Code1"), li, false, cp1, new CaseDate("06/06/2016"));
        return c;
    }

    public CaseI createCaseTest3() {
        Map<RiskFactor, ClassificationColumn> map1test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map2test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map3test = new HashMap<>();
        map1test.put(RSK1, cc1);
        map1test.put(RSK2, cc2);
        map2test.put(RSK3, cc3);
        map2test.put(RSK4, cc4);
        map3test.put(RSK5, cc5);
        map3test.put(RSK6, cc6);
        TableLine tb1test = new TableLine(map1test, cvg1);
        TableLine tb2test = new TableLine(map2test, cvg2);
        TableLine tb3test = new TableLine(map3test, cvg3);
        List<TableLine> lt = new ArrayList<>();
        lt.add(tb1test);
        lt.add(tb2test);
        lt.add(tb3test);
        ClassificationTable t1 = new ClassificationTable(lt);
        List<InsuranceObject> li = new ArrayList<>();
        InsuranceObject ioteste = new InsuranceObject(new InsuranceName("Casa"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(new ArrayList<>()), new RiskIndex(-1), t1);
        InsuranceObject io2teste = new InsuranceObject(new InsuranceName("Carro"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(detail), new RiskIndex(-1), t1);
        li.add(ioteste);
        li.add(io2teste);
        CaseI c = new CaseI(ct1, CaseState.VALIDATING, new CaseCode("Code3"), li, false, cp1, new CaseDate("06/06/2018"));
        return c;
    }

    public CaseI createCaseTest4() {
        Map<RiskFactor, ClassificationColumn> map1test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map2test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map3test = new HashMap<>();
        map1test.put(RSK1, cc1);
        map1test.put(RSK2, cc2);
        map2test.put(RSK3, cc3);
        map2test.put(RSK4, cc4);
        map3test.put(RSK5, cc5);
        map3test.put(RSK6, cc6);
        TableLine tb1test = new TableLine(map1test, cvg1);
        TableLine tb2test = new TableLine(map2test, cvg2);
        TableLine tb3test = new TableLine(map3test, cvg3);
        List<TableLine> lt = new ArrayList<>();
        lt.add(tb1test);
        lt.add(tb2test);
        lt.add(tb3test);
        ClassificationTable t1 = new ClassificationTable(lt);
        List<InsuranceObject> li = new ArrayList<>();
        InsuranceObject ioteste = new InsuranceObject(new InsuranceName("Carro"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(new ArrayList<>()), new RiskIndex(-1), t1);
        InsuranceObject io2teste = new InsuranceObject(new InsuranceName("Mota"), new PostLocation("Portugal", "Aveiro", "123ed", "1", "4480"), new CalculationDetail(detail), new RiskIndex(-1), t1);
        li.add(ioteste);
        li.add(io2teste);
        CaseI c = new CaseI(ct1, CaseState.PROCESSED, new CaseCode("Code4"), li, false, cp1, new CaseDate("06/06/2018"));
        c.alterDate("06/06/2020");
        return c;
    }

    public CaseI createCaseTest5() {
        Map<RiskFactor, ClassificationColumn> map1test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map2test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map3test = new HashMap<>();
        map1test.put(RSK1, cc1);
        map1test.put(RSK2, cc2);
        map2test.put(RSK3, cc3);
        map2test.put(RSK4, cc4);
        map3test.put(RSK5, cc5);
        map3test.put(RSK6, cc6);
        TableLine tb1test = new TableLine(map1test, cvg1);
        TableLine tb2test = new TableLine(map2test, cvg2);
        TableLine tb3test = new TableLine(map3test, cvg3);
        List<TableLine> lt = new ArrayList<>();
        lt.add(tb1test);
        lt.add(tb2test);
        lt.add(tb3test);
        ClassificationTable t1 = new ClassificationTable(lt);
        List<InsuranceObject> li = new ArrayList<>();
        InsuranceObject ioteste = new InsuranceObject(new InsuranceName("Habitacao"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(new ArrayList<>()), new RiskIndex(-1), t1);
        InsuranceObject io2teste = new InsuranceObject(new InsuranceName("Roubos"), new PostLocation("Portugal", "Aveiro", "123ed", "1", "4480"), new CalculationDetail(detail), new RiskIndex(-1), t1);
        li.add(ioteste);
        li.add(io2teste);
        CaseI c = new CaseI(ct1, CaseState.PROCESSED, new CaseCode("Code33"), li, false, cp1, new CaseDate("06/06/2019"));
        c.alterDate("23/12/2020");
        return c;
    }

    public CaseI createCaseTest6() {
        Map<RiskFactor, ClassificationColumn> map1test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map2test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map3test = new HashMap<>();
        map1test.put(RSK1, cc1);
        map1test.put(RSK2, cc2);
        map2test.put(RSK3, cc3);
        map2test.put(RSK4, cc4);
        map3test.put(RSK5, cc5);
        map3test.put(RSK6, cc6);
        TableLine tb1test = new TableLine(map1test, cvg1);
        TableLine tb2test = new TableLine(map2test, cvg2);
        TableLine tb3test = new TableLine(map3test, cvg3);
        List<TableLine> lt = new ArrayList<>();
        lt.add(tb1test);
        lt.add(tb2test);
        lt.add(tb3test);
        ClassificationTable t1 = new ClassificationTable(lt);
        List<InsuranceObject> li = new ArrayList<>();
        InsuranceObject ioteste = new InsuranceObject(new InsuranceName("Habitacao"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(new ArrayList<>()), new RiskIndex(-1), t1);
        InsuranceObject io2teste = new InsuranceObject(new InsuranceName("Roubos"), new PostLocation("Portugal", "Aveiro", "123ed", "1", "4480"), new CalculationDetail(detail), new RiskIndex(-1), t1);
        li.add(ioteste);
        li.add(io2teste);
        CaseI c = new CaseI(ct1, CaseState.PROCESSED, new CaseCode("Code12"), li, false, cp1, new CaseDate("06/06/2019"));
        c.alterDate("27/06/2030");
        return c;
    }

    public RiskMatrix createMatrixTest() {
        ColumnBuilder builders[] = {ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),};
        builders[0].setRiskFactor(RSK1);
        builders[1].setRiskFactor(RSK2);
        builders[2].setRiskFactor(RSK3);
        builders[3].setRiskFactor(RSK4);
        builders[4].setRiskFactor(RSK5);
        builders[5].setRiskFactor(RSK6);
        cvg1.associateRisk(RSK1);
        cvg1.associateRisk(RSK2);
        cvg2.associateRisk(RSK3);
        cvg2.associateRisk(RSK4);
        cvg3.associateRisk(RSK5);
        cvg3.associateRisk(RSK6);
        MatrixBuilder mb = new MatrixBuilder(MatrixBuilder.State.BASE, new MatrixVersion("1"));
        mb.addColumnBuilder(cvg1, builders[0]);
        mb.addColumnBuilder(cvg1, builders[1]);
        mb.addColumnBuilder(cvg2, builders[2]);
        mb.addColumnBuilder(cvg2, builders[3]);
        mb.addColumnBuilder(cvg3, builders[4]);
        mb.addColumnBuilder(cvg3, builders[5]);
        builders[0] = new DefinedColumnBuilder(builders[0]);
        builders[1] = new DefinedColumnBuilder(builders[1]);
        builders[2] = new DefinedColumnBuilder(builders[2]);
        builders[3] = new DefinedColumnBuilder(builders[3]);
        builders[4] = new DefinedColumnBuilder(builders[4]);
        builders[5] = new DefinedColumnBuilder(builders[5]);
        ((DefinedColumnBuilder) builders[0]).setWeight(new Weight(7));
        ((DefinedColumnBuilder) builders[1]).setWeight(new Weight(5));
        ((DefinedColumnBuilder) builders[2]).setWeight(new Weight(2));
        ((DefinedColumnBuilder) builders[3]).setWeight(new Weight(6));
        ((DefinedColumnBuilder) builders[4]).setWeight(new Weight(2));
        ((DefinedColumnBuilder) builders[5]).setWeight(new Weight(9));
        ((DefinedColumnBuilder) builders[0]).setContribution(Contribution.NEGATIVE);
        ((DefinedColumnBuilder) builders[1]).setContribution(Contribution.NEGATIVE);
        ((DefinedColumnBuilder) builders[2]).setContribution(Contribution.POSITIVE);
        ((DefinedColumnBuilder) builders[3]).setContribution(Contribution.POSITIVE);
        ((DefinedColumnBuilder) builders[4]).setContribution(Contribution.NEGATIVE);
        ((DefinedColumnBuilder) builders[5]).setContribution(Contribution.NEGATIVE);
        ((DefinedColumnBuilder) builders[0]).setNeed(NeedOfAnalisys.OBLIGATORY);
        ((DefinedColumnBuilder) builders[1]).setNeed(NeedOfAnalisys.FACULTATIVE);
        ((DefinedColumnBuilder) builders[2]).setNeed(NeedOfAnalisys.FACULTATIVE);
        ((DefinedColumnBuilder) builders[3]).setNeed(NeedOfAnalisys.OBLIGATORY);
        ((DefinedColumnBuilder) builders[4]).setNeed(NeedOfAnalisys.FACULTATIVE);
        ((DefinedColumnBuilder) builders[5]).setNeed(NeedOfAnalisys.OBLIGATORY);
        mb = new MatrixBuilder(MatrixBuilder.State.DEFINED, new MatrixVersion("1"));
        mb.addColumnBuilder(cvg1, builders[0]);
        mb.addColumnBuilder(cvg1, builders[1]);
        mb.addColumnBuilder(cvg2, builders[2]);
        mb.addColumnBuilder(cvg2, builders[3]);
        mb.addColumnBuilder(cvg3, builders[4]);
        mb.addColumnBuilder(cvg3, builders[5]);
        builders[0] = new DetailedColumnBuilder(builders[0]);
        builders[1] = new DetailedColumnBuilder(builders[1]);
        builders[2] = new DetailedColumnBuilder(builders[2]);
        builders[3] = new DetailedColumnBuilder(builders[3]);
        builders[4] = new DetailedColumnBuilder(builders[4]);
        builders[5] = new DetailedColumnBuilder(builders[5]);
        ((DetailedColumnBuilder) builders[0]).setScale(sc1);
        ((DetailedColumnBuilder) builders[1]).setScale(sc2);
        ((DetailedColumnBuilder) builders[2]).setScale(sc3);
        ((DetailedColumnBuilder) builders[3]).setScale(sc1);
        ((DetailedColumnBuilder) builders[4]).setScale(sc2);
        ((DetailedColumnBuilder) builders[5]).setScale(sc3);
        mb = new MatrixBuilder(MatrixBuilder.State.DETAILED, new MatrixVersion("1"));
        mb.addColumnBuilder(cvg1, builders[0]);
        mb.addColumnBuilder(cvg1, builders[1]);
        mb.addColumnBuilder(cvg2, builders[2]);
        mb.addColumnBuilder(cvg2, builders[3]);
        mb.addColumnBuilder(cvg3, builders[4]);
        mb.addColumnBuilder(cvg3, builders[5]);
        return mb.build();
    }

    public RiskMatrix createMatrixTest2() {
        ColumnBuilder builders[] = {ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),};
        builders[0].setRiskFactor(RSK1);
        builders[1].setRiskFactor(RSK2);
        builders[2].setRiskFactor(RSK3);
        builders[3].setRiskFactor(RSK4);
        builders[4].setRiskFactor(RSK5);
        builders[5].setRiskFactor(RSK6);
        cvg1.associateRisk(RSK1);
        cvg1.associateRisk(RSK2);
        cvg2.associateRisk(RSK3);
        cvg2.associateRisk(RSK4);
        cvg3.associateRisk(RSK5);
        cvg3.associateRisk(RSK6);
        MatrixBuilder mb = new MatrixBuilder(MatrixBuilder.State.BASE, new MatrixVersion("1"));
        mb.addColumnBuilder(cvg1, builders[0]);
        mb.addColumnBuilder(cvg1, builders[1]);
        mb.addColumnBuilder(cvg2, builders[2]);
        mb.addColumnBuilder(cvg2, builders[3]);
        mb.addColumnBuilder(cvg3, builders[4]);
        mb.addColumnBuilder(cvg3, builders[5]);
        builders[0] = new DefinedColumnBuilder(builders[0]);
        builders[1] = new DefinedColumnBuilder(builders[1]);
        builders[2] = new DefinedColumnBuilder(builders[2]);
        builders[3] = new DefinedColumnBuilder(builders[3]);
        builders[4] = new DefinedColumnBuilder(builders[4]);
        builders[5] = new DefinedColumnBuilder(builders[5]);
        ((DefinedColumnBuilder) builders[0]).setWeight(new Weight(2));
        ((DefinedColumnBuilder) builders[1]).setWeight(new Weight(5));
        ((DefinedColumnBuilder) builders[2]).setWeight(new Weight(2));
        ((DefinedColumnBuilder) builders[3]).setWeight(new Weight(3));
        ((DefinedColumnBuilder) builders[4]).setWeight(new Weight(2));
        ((DefinedColumnBuilder) builders[5]).setWeight(new Weight(1));
        ((DefinedColumnBuilder) builders[0]).setContribution(Contribution.POSITIVE);
        ((DefinedColumnBuilder) builders[1]).setContribution(Contribution.NEGATIVE);
        ((DefinedColumnBuilder) builders[2]).setContribution(Contribution.POSITIVE);
        ((DefinedColumnBuilder) builders[3]).setContribution(Contribution.POSITIVE);
        ((DefinedColumnBuilder) builders[4]).setContribution(Contribution.NEGATIVE);
        ((DefinedColumnBuilder) builders[5]).setContribution(Contribution.NEGATIVE);
        ((DefinedColumnBuilder) builders[0]).setNeed(NeedOfAnalisys.OBLIGATORY);
        ((DefinedColumnBuilder) builders[1]).setNeed(NeedOfAnalisys.FACULTATIVE);
        ((DefinedColumnBuilder) builders[2]).setNeed(NeedOfAnalisys.FACULTATIVE);
        ((DefinedColumnBuilder) builders[3]).setNeed(NeedOfAnalisys.OBLIGATORY);
        ((DefinedColumnBuilder) builders[4]).setNeed(NeedOfAnalisys.FACULTATIVE);
        ((DefinedColumnBuilder) builders[5]).setNeed(NeedOfAnalisys.OBLIGATORY);
        mb = new MatrixBuilder(MatrixBuilder.State.DEFINED, new MatrixVersion("2"));
        mb.addColumnBuilder(cvg1, builders[0]);
        mb.addColumnBuilder(cvg1, builders[1]);
        mb.addColumnBuilder(cvg2, builders[2]);
        mb.addColumnBuilder(cvg2, builders[3]);
        mb.addColumnBuilder(cvg3, builders[4]);
        mb.addColumnBuilder(cvg3, builders[5]);
        builders[0] = new DetailedColumnBuilder(builders[0]);
        builders[1] = new DetailedColumnBuilder(builders[1]);
        builders[2] = new DetailedColumnBuilder(builders[2]);
        builders[3] = new DetailedColumnBuilder(builders[3]);
        builders[4] = new DetailedColumnBuilder(builders[4]);
        builders[5] = new DetailedColumnBuilder(builders[5]);
        ((DetailedColumnBuilder) builders[0]).setScale(sc1);
        ((DetailedColumnBuilder) builders[1]).setScale(sc2);
        ((DetailedColumnBuilder) builders[2]).setScale(sc3);
        ((DetailedColumnBuilder) builders[3]).setScale(sc1);
        ((DetailedColumnBuilder) builders[4]).setScale(sc2);
        ((DetailedColumnBuilder) builders[5]).setScale(sc1);
        mb = new MatrixBuilder(MatrixBuilder.State.DETAILED, new MatrixVersion("2"));
        mb.addColumnBuilder(cvg1, builders[0]);
        mb.addColumnBuilder(cvg1, builders[1]);
        mb.addColumnBuilder(cvg2, builders[2]);
        mb.addColumnBuilder(cvg2, builders[3]);
        mb.addColumnBuilder(cvg3, builders[4]);
        mb.addColumnBuilder(cvg3, builders[5]);
        return mb.build();
    }
}
