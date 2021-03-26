/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.location;

import core.domain.location.PostLocationBuilder;
import core.domain.location.PostLocation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author morei
 */
public class PostLocationBuilderTest {

    public PostLocationBuilderTest() {
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

    /**
     * Test of changeDistrict method, of class PostLocationBuilder.
     */
    @Test
    public void testPostCode() {
        System.out.println("changeDistrict");
        String district = "4545-455";
        PostLocationBuilder instance = new PostLocationBuilder("road", "number", "pais", "distrito");

        PostLocationBuilder exp = new PostLocationBuilder("road", "number", "pais", "distrito");
        exp.changePost("4554-455");
        PostLocationBuilder result = instance.changePost(district);
        assertEquals(exp.district, result.district);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeCountryErros() {
        System.out.println("changeCountry");
        String country = "";
        PostLocationBuilder instance = new PostLocationBuilder("road", "number", "pais", "distrito");

        PostLocationBuilder exp = new PostLocationBuilder("road", "number", "pais", "distrito");
        exp.changePost("4545-455");
        PostLocationBuilder result = instance.changePost(country);
        assertEquals(exp.country, result.country);
    }

    /**
     * Test of build method, of class PostLocationBuilder.
     */
    @Test
    public void testBuild() {
        System.out.println("build");
        PostLocationBuilder instance = new PostLocationBuilder("road", "number", "portugal", "aveiro");
        instance.changePost("4545-454");
        PostLocationBuilder expResult = new PostLocationBuilder("road", "number", "portugal", "aveiro");
        expResult.changePost("4545-454");
        PostLocation result = instance.build();
        assertEquals(expResult.country, result.getCountry());
        assertEquals(expResult.district, result.getDistrict());
        assertEquals(expResult.number, result.getNumber());
        assertEquals(expResult.post_code, result.getPost_code());
        assertEquals(expResult.road, result.getRoad());

    }

}
