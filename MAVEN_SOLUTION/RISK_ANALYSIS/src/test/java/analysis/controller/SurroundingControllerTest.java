/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.controller;

import core.domain.location.GPSLocation;
import core.domain.Surrounding.STName;
import core.domain.Surrounding.Surrounding;
import core.domain.Surrounding.SurroundingName;
import core.persistence.PersistenceContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Ignore;

/**
 *
 * @author caduc
 */
public class SurroundingControllerTest {

    public SurroundingControllerTest() {
    }

    static STName ST1 = new STName("Bombeiros");
    static STName ST2 = new STName("Zona Urbana");
    static STName ST3 = new STName("IE Saúde");

    static SurroundingName STN1 = new SurroundingName("Bombeiros Voluntarios");
    static SurroundingName STN2 = new SurroundingName("Cidade da Maia");
    static SurroundingName STN3 = new SurroundingName("Hospital Sao Joao");

    static GPSLocation GPSL1 = new GPSLocation(0, 0, 0, "Porto");
    static GPSLocation GPSL2 = new GPSLocation(1, 1, 1, "Lisboa");
    static GPSLocation GPSL3 = new GPSLocation(2, 2, 2, "Braga");

    static Surrounding S1;
    static Surrounding S2;
    static Surrounding S3;

    @BeforeClass
    public static void before() {
        GPSL1 = PersistenceContext.repositories().LocationRepository().save(GPSL1);
        System.out.println("saved " + GPSL1.identity());
        GPSL2 = PersistenceContext.repositories().LocationRepository().save(GPSL2);
        GPSL3 = PersistenceContext.repositories().LocationRepository().save(GPSL3);

        S1 = new Surrounding(STN1, GPSL1, ST1);
        S2 = new Surrounding(STN2, GPSL2, ST2);
        S3 = new Surrounding(STN3, GPSL3, ST3);
        
        PersistenceContext.repositories().surroundingRepository().save(S1);
        PersistenceContext.repositories().surroundingRepository().save(S2);
        PersistenceContext.repositories().surroundingRepository().save(S3);

    }

    @AfterClass
    public static void tearDown() {
        PersistenceContext.repositories().surroundingRepository().delete(S1);
        PersistenceContext.repositories().surroundingRepository().delete(S2);
        PersistenceContext.repositories().surroundingRepository().delete(S3);
//        
        System.out.println("deleting " + GPSL1.identity());
        PersistenceContext.repositories().LocationRepository().delete(GPSL1);
        PersistenceContext.repositories().LocationRepository().delete(GPSL2);
        PersistenceContext.repositories().LocationRepository().delete(GPSL3);

    }

    /**
     * Test of listSurroundings method, of class SurroundingController.
     */
    @Test
    public void testListSurroundings() {

        int i = 0;
        System.out.println("listSurroundings");
        SurroundingController instance = new SurroundingController();
        List<String> expResult = new ArrayList<>();
        expResult.add(STN1.toString());
        expResult.add(STN2.toString());
        expResult.add(STN3.toString());
        Iterable<Surrounding> result = instance.listSurroundings();
        for (Surrounding su : result) {
            System.out.println(su.obtainSName().toString());
            assertEquals(expResult.get(i), su.obtainSName().toString());
            i++;
        }
        instance.exportToXHTML(result, "output.xhtml");
    }

    /**
     * Test of listSurroundings method, of class SurroundingController.
     */
    @Test
    public void testListSurroundings_validDistrict() {
        int i = 0;
        System.out.println("listSurroundings");
        String district = "Faro";
        SurroundingController instance = new SurroundingController();
        List<String> expResult = new ArrayList<>();
        expResult.add("Bombeiros Voluntarios");
        Iterable<Surrounding> result = instance.listSurroundings(district);
        for (Surrounding su : result) {
            assertEquals(expResult.get(i), su.obtainSName().toString());
        }
    }
    
    /**
     * Test of listSurroundings method, of class SurroundingController.
     */
    @Test
    public void testListSurroundings_invalidDistrict() {
        int i = 0;
        System.out.println("listSurroundings");
        String district = "Coimbra";
        SurroundingController instance = new SurroundingController();
        List<String> expResult = new ArrayList<>();
        Iterable<Surrounding> result = new ArrayList<>();
        result = instance.listSurroundings(district);
        for (Surrounding su : result) {
            assertEquals(expResult.get(i), su.obtainSName().toString());
        }
    }

    /**
     * Test of listSurroundings method, of class SurroundingController.
     */
    @Ignore
    public void testListSurroundings_8args() {
        int i = 0;
        System.out.println("listSurroundings");
        String lat1 = "-1";
        String lon1 = "-1";
        String lat2 = "1";
        String lon2 = "1";
        SurroundingController instance = new SurroundingController();
        List<String> expResult = new ArrayList<>();
        expResult.add("Hospital Sao Joao");
        Iterable<Surrounding> result = instance.listSurroundings(lat1, lon1, lat2, lon2);
        for (Surrounding su : result) {
            assertEquals(expResult.get(i), su.obtainSName().toString());
            i++;
        }
    }

    /**
     * Test of listSurroundings method, of class SurroundingController.
     */
    @Ignore
    public void testListSurroundings_4args() {
        System.out.println("listSurroundings");
        String lat1 = "";
        String lon1 = "";
        String lat2 = "";
        String lon2 = "";
        SurroundingController instance = new SurroundingController();
        Iterable<Surrounding> expResult = null;
        Iterable<Surrounding> result = instance.listSurroundings(lat1, lon1, lat2, lon2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
 @Test
    public void testShowAerealImagemFromLocation() throws IOException {
         System.out.println("testShowAerealImagemFromLocation");
         SurroundingController instance = new SurroundingController();
         String address = "Alameda Prof.Hernâni Monteiro,4200-319 Porto";
         boolean res= instance.showAerealImagemFromLocation(address);
         assertEquals(true,res);
    }
}
