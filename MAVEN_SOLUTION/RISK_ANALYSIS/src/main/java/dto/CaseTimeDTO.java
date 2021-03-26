/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Pedro
 */
public class CaseTimeDTO implements Serializable{
    
    public String CaseCode;
    
    public String timePassed;
    
    public List<String> listInsurances;
    
    /**
     * CaseTimeDTO Constructor
     */
    public CaseTimeDTO(){
        CaseCode = "";
        timePassed="";
        listInsurances=new ArrayList<>();
    }
    
    /**
     * CaseTimeDTO Constructor
     * @param caseCode
     * @param timePassed 
     */
    public CaseTimeDTO(String caseCode, String timePassed, List<String> insuranceNames){
        this.CaseCode=caseCode;
        this.timePassed=timePassed;
        this.listInsurances = insuranceNames;
    }
    
}
