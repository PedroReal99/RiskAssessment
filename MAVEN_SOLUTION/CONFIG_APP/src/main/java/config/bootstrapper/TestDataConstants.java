/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.bootstrapper;

import core.domain.Coverage.Coverage;
import core.domain.Coverage.CoverageName;
import core.domain.RiskFactors.Metric;
import core.domain.location.GPSLocation;
import core.domain.location.Location;
import core.domain.location.PostLocation;
import core.domain.RiskFactors.MetricStrategy;
import core.domain.RiskFactors.RiskFactor;
import core.domain.Surrounding.STName;
import core.domain.RiskMatrixs.Attributes.Contribution;
import core.domain.RiskMatrixs.Attributes.NeedOfAnalisys;
import core.domain.RiskMatrixs.Attributes.RiskLevel;
import core.domain.RiskMatrixs.Attributes.Scale;
import core.domain.RiskMatrixs.Attributes.Weight;
import core.domain.RiskMatrixs.Columns.BaseColumn;
import core.domain.RiskMatrixs.Columns.DefinedColumn;
import core.domain.RiskMatrixs.Columns.DetailedColumn;
import core.domain.RiskMatrixs.MatrixVersion;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

;

/**
 *
 * @author caduc
 */
public class TestDataConstants {

    public static final RiskFactor RSK1 = new RiskFactor(Metric.DISTANCIA, new STName("Bombeiros"));
    public static final RiskFactor RSK2 = new RiskFactor(Metric.QUANTIDADE, new STName("IE Saúde"));
    public static final RiskFactor RSK3 = new RiskFactor(Metric.QUANTIDADE, new STName("Zona Urbana"));

    private static final Set<RiskFactor> SET1 = new HashSet<>(Arrays.asList(RSK1, RSK2));
    public static final Coverage COV1 = new Coverage(new CoverageName("Incêndios"), SET1);
    private static final Set<RiskFactor> SET2 = new HashSet<>(Arrays.asList(RSK3, RSK2));
    public static final Coverage COV2 = new Coverage(new CoverageName("Inundações"), SET2);
    private static final Set<RiskFactor> SET3 = new HashSet<>(Arrays.asList(RSK3));
    public static final Coverage COV3 = new Coverage(new CoverageName("Tornados"), SET3);

    public static final NeedOfAnalisys NEED1 = NeedOfAnalisys.OBLIGATORY;
    public static final NeedOfAnalisys NEED2 = NeedOfAnalisys.FACULTATIVE;
    public static final NeedOfAnalisys NEED3 = NeedOfAnalisys.FACULTATIVE;

    public static final Contribution CONT1 = Contribution.POSITIVE;
    public static final Contribution CONT2 = Contribution.NEGATIVE;
    public static final Contribution CONT3 = Contribution.NEGATIVE;

    public static final Weight WEIGHT1 = new Weight(1);
    public static final Weight WEIGHT2 = new Weight(1);
    public static final Weight WEIGHT3 = new Weight(1);

    public static final RiskLevel RSK_LEVEL1 = new RiskLevel(1);
    public static final RiskLevel RSK_LEVEL2 = new RiskLevel(4);
    public static final RiskLevel RSK_LEVEL3 = new RiskLevel(5);

    public static final Scale SCL1 = new Scale(RSK_LEVEL1, RSK_LEVEL2, RSK_LEVEL3);
    public static final Scale SCL2 = new Scale(RSK_LEVEL3, RSK_LEVEL2, RSK_LEVEL1);
    public static final Scale SCL3 = new Scale(RSK_LEVEL3, RSK_LEVEL2, RSK_LEVEL1);

    public static final MatrixVersion BASE_VERSION = MatrixVersion.create("BASE_TEST");
    public static final MatrixVersion DEFINED_VERSION = MatrixVersion.create("DEFINED_TEST");
    public static final MatrixVersion DETAILED_VERSION = MatrixVersion.create("DETAILED_TEST");

    public static final BaseColumn C1 = new BaseColumn(RSK1);
    public static final BaseColumn C2 = new BaseColumn(RSK2);
    public static final BaseColumn C3 = new BaseColumn(RSK3);

    public static final DefinedColumn DFC1 = new DefinedColumn(RSK1, WEIGHT1, CONT1, NEED1);
    public static final DefinedColumn DFC2 = new DefinedColumn(RSK2, WEIGHT2, CONT2, NEED2);
    public static final DefinedColumn DFC3 = new DefinedColumn(RSK3, WEIGHT3, CONT3, NEED3);

    public static final DetailedColumn DTC1 = new DetailedColumn(RSK1, WEIGHT1, CONT1, NEED1, SCL1);
    public static final DetailedColumn DTC2 = new DetailedColumn(RSK2, WEIGHT2, CONT2, NEED2, SCL2);
    public static final DetailedColumn DTC3 = new DetailedColumn(RSK3, WEIGHT3, CONT3, NEED3, SCL3);

    public static final String ST1 = "Bombeiros";
    public static final String ST2 = "Zona Urbana";
    public static final String ST3 = "IE Saúde";
    
    public static final String STN1 = "Bombeiros Voluntarios";
    public static final String STN2 = "Cidade da Maia";
    public static final String STN3 = "Hospital Sao Joao";
    
    public static final GPSLocation GPSL1 = new GPSLocation(0, 0, 0, "Porto");
    public static final GPSLocation GPSL2 = new GPSLocation(1, 1, 1, "Lisboa");
    public static final GPSLocation GPSL3 = new GPSLocation(2, 2, 2, "Braga");

}
