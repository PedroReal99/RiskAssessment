/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.controller;

import core.domain.Coverage.Coverage;
import core.domain.Coverage.CoverageName;
import core.persistence.CoverageRepository;
import core.persistence.PersistenceContext;
import eapli.framework.application.Controller;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Vasco_Rodrigues
 */
public class CoverageController implements Controller {

    private CoverageRepository rep = PersistenceContext.repositories().coverageRepository();

    public Iterable<Coverage> getAll() {
        return PersistenceContext.repositories().coverageRepository().findAll();
    }

    public Coverage getCoverage(String name) {
        CoverageName validName;
        try {
            validName = new CoverageName(name);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

        return rep.findByName(validName);
    }

    public Coverage createCoverage(String name) {
        boolean var;
        Coverage sr;
        try {
            if ((sr = getCoverage(name)) != null) {
                System.out.println("Error: A Coverage named" + name + " already exists!\n");
                return null;
            }
            CoverageName validName;
            validName = new CoverageName(name);
            sr = new Coverage(validName);
            rep.save(sr);
            var = true;
        } catch (Exception ex) {
            System.out.println("Error: Something went wrong when persisting this Coverage!\n");
            return null;
        }
        sendToUnderwriterManager(var, sr);

        return sr;
    }

    private boolean sendToUnderwriterManager(boolean var, Coverage sr) {
        if (var) {
            try {
                Properties p = System.getProperties();
                p.load(new FileInputStream("src/main/resources/mail.properties"));
                SendEmailController.send(p, sr);
            } catch (IOException ex) {
                System.out.println("Error: Something went wrong when informing underwriter manager about the new coverage!\n");
                return false;
            }
        }
        return true;
    }

}
