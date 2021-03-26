/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import core.domain.Case.CaseI;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import risk.importjson.JsonImport;

/**
 *
 * @author Pedro
 */
public class ImportExistingCasesController {
    
    private JsonImport ji = new JsonImport();
    
    public ImportExistingCasesController(){
        
    }
    
    public CaseI importExistingCases(String nomeFich) throws IOException, ParseException{
        CaseI caase = ji.getExistingCases(nomeFich);
        return caase;
    }
    
}
