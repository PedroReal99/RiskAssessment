/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.controller;

import core.domain.location.GPSLocation;
import core.domain.location.Location;
import core.domain.Surrounding.STName;
import core.domain.Surrounding.Surrounding;
import core.persistence.PersistenceContext;
import core.persistence.SurroundingTypeRepository;
import core.domain.Surrounding.SurroundingType;
import eapli.framework.application.Controller;

/**
 *
 * @author CarloS
 */
public class SurroundingTypeController implements Controller {

    public SurroundingTypeController() {
        //do nothing
    }

    public Iterable<SurroundingType> getAll(){
        return PersistenceContext.repositories().surroundingTypeRepository().findAll();
    }
    
    public boolean createSurroundingType(String tipo) {
        STName validName;
        try {
            validName = new STName(tipo);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        SurroundingType sr = new SurroundingType(validName);

        try {
            SurroundingTypeRepository rep=PersistenceContext.repositories().surroundingTypeRepository();

            if(rep.findByName(validName)!=null){
                System.out.println("Error: A Surrounding type named " + validName+" already exists!\n");
                return false;
            }
            rep.save(sr);
            return true;
        }
        catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Error: Something went wrong when persisting this Surrounding type!\n");
            return false;
        }
    }
}
