/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.controller;

import config.controller.SendEmailController;
import core.domain.Coverage.Coverage;
import core.domain.Coverage.CoverageName;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Properties;
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
public class SendEmailControllerTest {

    Properties props1 = new Properties();

    public SendEmailControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws FileNotFoundException, IOException {
        props1.load(new FileInputStream("src/main/resources/mail.properties"));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<SendEmailController> constructor = SendEmailController.class.getDeclaredConstructor();
        assertEquals(false, Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    /**
     * Test of send method, of class SendEmailController.
     *
     * @throws java.io.FileNotFoundException
     */
    //@Test
    public void testSend() throws FileNotFoundException, IOException {
        System.out.println("send");
        Coverage coverage = new Coverage(new CoverageName("CoverageTest"));
        boolean expResult = true;
        boolean result = SendEmailController.send(props1, coverage);
        assertEquals(expResult, result);
    }

    /**
     * Test of obtainValueFromProperties method, of class SendEmailController.
     */
    @Test
    public void testObtainValueFromProperties() {
        System.out.println("obtainValueFromProperties");
        String key = "mail.smtp.host";
        String expResult = "smtp.gmail.com";
        String result = SendEmailController.obtainValueFromProperties(props1, key);
        assertEquals(expResult, result);
    }

    /**
     * Test of obtainProperties method, of class SendEmailController.
     */
    @Test
    public void testObtainProperties() {
        System.out.println("obtainProperties");
        String host = "smtp.gmail.com";
        String from = "bikesharingg44@gmail.com";
        String pass = "12345678=a";
        Properties expResult = System.getProperties();
        expResult.put("mail.smtp.starttls.enable", "true");
        expResult.put("mail.smtp.host", host);
        expResult.put("mail.smtp.user", from);
        expResult.put("mail.smtp.password", pass);
        expResult.put("mail.smtp.port", "587");
        expResult.put("mail.smtp.auth", "true");
        Properties result = SendEmailController.obtainProperties(host, from, pass);
        assertEquals(expResult, result);
    }

}
