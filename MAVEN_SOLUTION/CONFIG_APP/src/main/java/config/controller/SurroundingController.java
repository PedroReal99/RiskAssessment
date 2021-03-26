/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.controller;

import core.domain.location.GPSLocation;
import core.domain.location.Location;
import core.domain.location.PostLocation;
import core.domain.Surrounding.STName;
import core.domain.Surrounding.Surrounding;
import core.domain.Surrounding.SurroundingName;
import core.persistence.PersistenceContext;
import core.persistence.SurroundingTypeRepository;
import core.domain.Surrounding.SurroundingType;
import core.persistence.SurroundingRepository;
import eapli.framework.application.Controller;

/**
 *
 * @author CarloS
 */
public class SurroundingController implements Controller {

    public SurroundingController() {
        //do nothing
    }

    public Iterable<Surrounding> getAll(){
        return PersistenceContext.repositories().surroundingRepository().findAll();
    }
    
    public boolean createSurrounding(String name, GPSLocation location, String type) {
        SurroundingName validName;
        STName validType;
        try {
            validName = new SurroundingName(name);
            validType = new STName(type);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        Surrounding sr = new Surrounding(validName, location, validType);

        try {
            SurroundingRepository rep=PersistenceContext.repositories().surroundingRepository();

            if(rep.findByBothParameters(validType, validName)!=null){
                System.out.println("Error: A Surrounding named " + validName+" already exists!\n");
                return false;
            }
            rep.save(sr);
            return true;
        }
        catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Error: Something went wrong when persisting this Surrounding!\n");
            return false;
        }
    }
}
