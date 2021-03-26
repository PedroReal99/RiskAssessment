/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskFactors;

import core.domain.Surrounding.STName;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CarloS
 */
public class STNameTest {
    
    public STNameTest() {
    }
    
    @Test
    public void testValidName(){
        assertNotNull(new STName("José"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testEmptyName(){
        new STName(" ");
    }

     @Test (expected = IllegalArgumentException.class)
    public void testNullName(){
        new STName(null);
    }
    
    /**
     * Test of toString method, of class STName.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        STName instance = new STName(" TESTE");
        String expResult = "TESTE";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
