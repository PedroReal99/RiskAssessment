/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskFactors;

import core.domain.Surrounding.STName;
import core.domain.Surrounding.SurroundingType;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CarloS
 */
public class SurroundingTypeTest {
    
    public SurroundingTypeTest() {
    }

    @Test
    public void testValidInstance() {
        assertNotNull(new SurroundingType(new STName("teste")));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testNullName() {
        new SurroundingType(new STName(null));
    }
}
