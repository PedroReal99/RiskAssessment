/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Case;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pedro
 */
public class CaseCodeTest {
    
    CaseCode cc = new CaseCode("12Ryu8");
    
    public CaseCodeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    
    @Test
    public void ensureCaseCodeIsValid() {
        new CaseCode("1234tyu");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureCaseCodeDoubleSpaceBetweenWordsNotAllowed() {
        new CaseCode("sdwaef  dwafes45");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureCaseCodeOnlyOneSpacesNotAllowed() {
        new CaseCode(" ");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureCaseCodeOnlyMultipleSpacesNotAllowed() {
        new CaseCode("     ");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureCaseCodeEmptyStringNotAllowed() {
        new CaseCode("");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureCaseCodeIllegalCharactersNotAllowed() {
        new CaseCode("^~~");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureCaseCodeIllegalCharactersBetweenWordNotAllowed() {
        new CaseCode("Dista^ncia");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureCaseCodeIllegalCharactersAfterWordAndSpaceNotAllowed() {
        new CaseCode("Distancia ^");
    }
    
    @Test
    public void ensureToStringIsWorking() {
        assertEquals("Distância", new CaseCode("Distância").toString());
    }
    
    
    

    
}
