/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Surrounding;

import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author CarloS
 */
@Embeddable
public class STName implements ValueObject {

    private String name;

    protected STName() {
        //precistence implementation
    }
    
    public STName(String name) {
        this.name=validate(name);
    }

    /**
     * Validates the name
     * @param name
     * @return 
     */
    private static String validate(String name) {
        if(name!=null&&name.trim().matches("([A-Za-zzçÇãâÃÂáÁàÀèÈéÉêÊíìÍÌîÎóòÓÒõÕôÔúùÚÙûÛ]+\\s?)+")) {
            return name.trim();
        } else {
            throw new IllegalArgumentException("The name is not valid!");
        }     
    }
    
    /**
     * Returns a string with the name of this object
     * @return 
     */
    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof STName)) {
            return false;
        }
        
        final STName other = (STName) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    
}
