package core.domain.Insurance.ClassificationTables;

import core.domain.Coverage.Coverage;
import core.domain.Insurance.CalculationDetail;
import core.domain.RiskFactors.RiskFactor;
import core.domain.RiskMatrixs.*;
import core.domain.RiskMatrixs.Attributes.Contribution;
import core.domain.RiskMatrixs.Attributes.Designation;
import core.domain.RiskMatrixs.Attributes.NeedOfAnalisys;
import core.domain.RiskMatrixs.Columns.DetailedColumn;
import eapli.framework.domain.model.DomainEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;

@Embeddable
public class ClassificationTable implements DomainEntity<Long>, Serializable {
    
    @GeneratedValue
    long id;
    
    
    private List<TableLine> listofLines = new ArrayList<>();

    
    protected ClassificationTable() {
        // For ORM only
    }

    public ClassificationTable(List<TableLine> list) {
        this.listofLines = list;

    }

    public List<TableLine> obtainMatrix() {
        return this.listofLines;
    }

    public long getId() {
        return id;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClassificationTable other = (ClassificationTable) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }



    
   
    @Override
    public boolean sameAs(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClassificationTable other = (ClassificationTable) obj;

        if(this.listofLines.containsAll(other.listofLines)){
            return this.listofLines.size()==other.listofLines.size();
        }
        return false;
    }

    @Override
    public Long identity() {
        return this.id;
    }

    public int calculateFinalScore(String source,RiskMatrix rm , CalculationDetail dets , boolean det) {
        dets.reset();
        StringBuilder sb = new StringBuilder();
        sb.append("Indice de Risco = ");
        int vObt = calculateObtainedScore(source,rm, dets);
        sb.append(vObt);
        sb.append("/");
        int vMax = calculateMaxScore(rm, dets);
        sb.append(vMax);
        sb.append(" = ");
        float temp = (float)  vObt/vMax;
        temp *= 100;
        int vFinal = Math.round(temp);
        sb.append(vFinal);
        sb.append("%");
        sb.append(" (Versão: ");
        sb.append(rm.getVersion());
        sb.append(")");
        dets.addDetailsOfCalculation(sb.toString());
        if(!det) {
           dets = new CalculationDetail(new ArrayList<>()); 
        } else {
            System.out.println(dets.toString());
        }
        return vFinal;
    }
    
    public int calculateObtainedScore(String source,RiskMatrix rm , CalculationDetail dets) {
        int vTotal=0;
        List<Line> matrix = rm.obtainMatrix();
        for(TableLine l : listofLines) {
            int vObt = 0;
            int i = 0;
            StringBuilder sb = new StringBuilder();
            sb.append("Score Obtido de ");
            sb.append(l.obtainCoverage().obtainName());
            sb.append(" = ");
            Line aux = obtainLineFromMatrix(matrix, l.obtainCoverage());
            for(RiskFactor rf : aux.obtainColumns().keySet()) {
                DetailedColumn a = (DetailedColumn) aux.obtainColumns().get(rf);
                sb.append("(");
                int temp = a.obtainWeight().obtainWeight();
                sb.append(temp);
                sb.append(" x ");
                ClassificationColumn column=l.obtainColumns().get(rf);
                column.updateClassification(source);
                Designation c = new Designation(column.classifiation.toString());
                int temp1;
                if(c.equals(new Designation("(não detetado)"))) {
                    if(a.obtainNecessity().equals(NeedOfAnalisys.FACULTATIVE)) {
                        temp1 = 0;
                    } else {
                        Designation d;
                        if(a.obtainContribution().equals(Contribution.NEGATIVE)) {
                            d = new Designation("HIGH");
                        } else {
                            d = new Designation("LOW");    
                        }
                        temp1 = a.obtainScale().obtainRiskLevelValue(d);
                    }
                } else {
                    temp1 = a.obtainScale().obtainRiskLevelValue(c);
                }
                sb.append(temp1);
                vObt += (temp * temp1);
                sb.append(")");
                i++;
                if(i != aux.obtainColumns().keySet().size()) {
                    sb.append(" + ");
                } else {
                    sb.append(" = ");
                    sb.append(vObt);
                } 
            }
            vTotal += vObt;
            dets.addDetailsOfCalculation(sb.toString());
        } 
        dets.addDetailsOfCalculation("Score Obtido Total = " +vTotal);
        return vTotal;
    }

    public int calculateMaxScore(RiskMatrix rm , CalculationDetail dets) {
        int vTotal=0;
        Designation d = new Designation("HIGH");
        List<Line> matrix = rm.obtainMatrix();
        for(TableLine l : listofLines) {
            int vMax = 0;
            int i = 0;
            StringBuilder sb = new StringBuilder();
            sb.append("Score Máximo de ");
            sb.append(l.obtainCoverage().obtainName());
            sb.append(" = ");
            Line aux = obtainLineFromMatrix(matrix, l.obtainCoverage());
            for(RiskFactor rf : aux.obtainColumns().keySet()) {
                DetailedColumn a = (DetailedColumn) aux.obtainColumns().get(rf);
                sb.append("(");
                int temp = a.obtainWeight().obtainWeight();
                sb.append(temp);
                sb.append(" x ");
                int temp1 = a.obtainScale().obtainRiskLevelValue(d);
                sb.append(temp1);
                vMax += (temp * temp1);
                sb.append(")");
                i++;
                if(i != aux.obtainColumns().keySet().size()) {
                    sb.append(" + ");
                } else {
                    sb.append(" = ");
                    sb.append(vMax);
                } 
            }
            vTotal += vMax;
            dets.addDetailsOfCalculation(sb.toString());
        } 
        dets.addDetailsOfCalculation("Score Máximo Total = " +vTotal);
        return vTotal;
    }
    
    public Line obtainLineFromMatrix(List<Line> list , Coverage c) {
        for(Line l : list) {
            if(l.obtainCoverage().equals(c)) {
                return l;
            }
        }
        return null;
    }
    
    /**
     * Retorna a linha para uma dada cobertura
     * @param c
     * @return 
     */
    public TableLine obtainLine(Coverage c) {
        for(TableLine l : listofLines) {
            if(l.obtainCoverage().equals(c)) {
                return l;
            }
        }
        return null;
    }
    
    /**
     * Adiciona uma cobertura a tabela
     * @param coverage 
     */
    public void addCoverage(Coverage coverage, Map<RiskFactor,ClassificationColumn> column) {
        if(obtainLine(coverage)!=null){
            listofLines.add(new TableLine(column, coverage));
        }
    }
}