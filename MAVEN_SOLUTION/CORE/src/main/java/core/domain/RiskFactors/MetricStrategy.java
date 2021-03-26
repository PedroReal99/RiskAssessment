/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskFactors;

import core.domain.RiskMatrixs.Attributes.Designation;
import eapli.framework.domain.model.ValueObject;
import java.util.List;
import javax.persistence.Embeddable;

/**
 *
 * @author Vasco Rodrigues
 */

@Embeddable
public interface MetricStrategy extends ValueObject {
    public String getMetrics();

    @Override
    public String toString();
    
    public Designation getClassification(String source,List<String> locations,RiskFactor risk);
    
}
