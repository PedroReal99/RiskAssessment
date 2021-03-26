/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Surrounding;

import core.domain.location.GPSLocation;
import core.domain.location.Location;
import eapli.framework.domain.model.AggregateRoot;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 *
 * @author Ricardo Branco
 */
@Entity
@IdClass (SurroundingKey.class)
public class Surrounding implements AggregateRoot<SurroundingKey>, Serializable {
    
    
    @Id
    private STName type;
    
    @Id
    /**
     * Name of the surrounding type
     */
    private SurroundingName name;
    
    
    private GPSLocation  location;

    private long  fieldId;
    
    protected Surrounding() {
        //
    }
    
    public Surrounding(SurroundingName name, GPSLocation  location, STName categoria) {
        type=categoria;
        this.name = name;
        this.location=location;
        fieldId=hashCode();
    }
    
    public SurroundingName obtainSName() {
        return getSName();
    }

    private SurroundingName getSName() {
        return this.name;
    }
    
    public STName obtainSTName() {
        return getSTName();
    }

    private STName getSTName() {
        return this.type;
    }
    
    public GPSLocation obtainLocation() {
        return this.location;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Surrounding other = (Surrounding) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public SurroundingKey identity() {
        return new SurroundingKey(type, name);
    }

    
}
