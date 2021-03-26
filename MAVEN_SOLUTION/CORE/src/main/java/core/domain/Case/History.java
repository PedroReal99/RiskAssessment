/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Case;

import eapli.framework.domain.model.ValueObject;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.Embeddable;

/**
 *
 * @author CarloS
 */
@Embeddable
public class History implements ValueObject {

    private final Map<Date, String> history;

    public static final String MODIFICATION = "Modified by ";
    public static final String EVALUATION = "Evaluation started by ";
    public static final String REEVALUATION_REQUEST = "Reevaluation request by ";
    public static final String ASSIGNING = "Assigned by ";
    public static final String CONFIRMATION = "Confirmed by ";
    public static final String CREATED = "Created ";

    public History() {
        history = new TreeMap<>();
    }

    public void addEvent(String user, String type, String comment) {
        history.put(new Date(), type +user+": "+comment);
    }
    
    public Map<Date,String> getHistory(){
        return new HashMap<>(history);
    }
    
    public List<String> list(){
        List<String> list=new LinkedList<>();
        for(Date date:history.keySet()){
            list.add(0,date.toString()+": "+history.get(date));
        }
        return list;
    }
}
