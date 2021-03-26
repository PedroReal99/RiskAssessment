/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Insurance;

import core.domain.Coverage.*;
import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author CarloS
 */
@Embeddable
public class InsuranceName implements ValueObject,Comparable<InsuranceName> {

    private String name;

    protected InsuranceName() {
        //precistence implementation
    }

    public InsuranceName(String name) {
        this.name = validate(name);
    }

    /**
     * Validates the name
     *
     * @param name
     * @return
     */
    private static String validate(String name) {
        return name.trim();
        //FIXME
//        if (name != null && name.trim().matches("([A-Za-zÇçãâÃÂáÁàÀèÈéÉêÊíìÍÌîÎóòÓÒõÕôÔúùÚÙûÛ\\.,;:-]+[0-9]+\\s?)+")) {
//            return name.trim();
//        } else {
//            System.out.println(name);
//            throw new IllegalArgumentException("The name is not valid!");
//        }
    }

    /**
     * Returns a string with the name of this object
     *
     * @return
     */
    @Override
    public String toString() {
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
        if (!(obj instanceof InsuranceName)) {
            return false;
        }

        final InsuranceName other = (InsuranceName) obj;
        return this.name.endsWith(other.name);
    }

    @Override
    public int compareTo(InsuranceName t) {
        return this.name.compareTo(t.name);    
    }

}
