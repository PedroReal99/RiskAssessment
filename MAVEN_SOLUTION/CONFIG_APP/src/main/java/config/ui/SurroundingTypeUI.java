/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.ui;

import config.controller.SurroundingTypeController;
import core.domain.location.GPSLocation;
import core.domain.Surrounding.SurroundingType;
import eapli.framework.presentation.console.AbstractUI;
import java.util.Scanner;

/**
 *
 * @author CarloS
 */
public class SurroundingTypeUI extends AbstractUI {
    
    SurroundingTypeController controller;
    public SurroundingTypeUI(){
        controller=new SurroundingTypeController();
    }
    
    public void createSurroundingType(){
        Scanner in=new Scanner(System.in);
        String type = readCategoria(in);
        String name = readName(in);  
        GPSLocation location = readLocation(in);
        
        controller.createSurroundingType(type);
    }
    
    public void listSurroundingType(){
        System.out.println("================================"
                       + "\n        SurroundingTypes"
                       + "\n================================\n");
        for(SurroundingType st:controller.getAll()){
            System.out.println(st);
        }
    }
    
    private String readName(Scanner in){
        System.out.println("\n\nInsert the name:\n");
        
        String name=null;
        name=in.nextLine();
        
        return name;
    }
    
    private String readCategoria(Scanner in){
        System.out.println("\n\nInsert the category:\n");
        
        String category=null;
        category=in.nextLine();
        
        return category;
    }
    
    private GPSLocation readLocation(Scanner in){
        System.out.println("\n\nInsert the latitude:\n");
        String lat = null;
        lat = in.nextLine();
        
        System.out.println("\n\nInsert the longitude:\n");
        String lon = null;
        lon = in.nextLine();
        
        System.out.println("\n\nInsert the altitude:\n");
        String alt = null;
        alt = in.nextLine();
        
        GPSLocation location = new GPSLocation(Float.parseFloat(lat), Float.parseFloat(lon), Float.parseFloat(alt), "Porto");
        
        return location;
    }

    @Override
    protected boolean doShow() {
        createSurroundingType();
        return true;
    }

    @Override
    public String headline() {
        return "Creation of Surronding type";
    }
}
