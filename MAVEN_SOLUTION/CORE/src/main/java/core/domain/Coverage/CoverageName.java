/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Coverage;

import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author CarloS
 */
@Embeddable
public class CoverageName implements ValueObject {

    private String name;

    protected CoverageName() {
        //precistence implementation
    }
    
    public CoverageName(String name) {
        this.name=validate(name);
    }

    /**
     * Validates the name
     * @param name
     * @return 
     */
    private static String validate(String name) {
        if(name!=null&&name.trim().matches("([A-Za-zÇçãâÃÂáÁàÀèÈéÉêÊíìÍÌîÎóòÓÒõÕôÔúùÚÙûÛ]+\\s?)+")) {
            return name.trim();
        } else {
            throw new IllegalArgumentException("The name is not valid!"+ name);
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
        if (!(obj instanceof CoverageName)) {
            return false;
        }
        
        final CoverageName other = (CoverageName) obj;
        if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
    
    
}
