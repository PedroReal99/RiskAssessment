/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.location;

//import Controller.ExternalGeoRefServiceController;
import eapli.framework.domain.model.AggregateRoot;
import java.io.Serializable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author Ricardo Branco
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoLoc")
public abstract class Location implements AggregateRoot<Long>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    private String district;
    
    public Location() {
        this.district = "";
    }
    
    public Location(String district) {
        this.district = district;
    }

    public abstract String getLocation();
    
    public String getDistrict(){
        return this.district;
    }
    
    public void setDistrict(String district){
        this.district = district;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Location)) {
            return false;
        }
        return this.id == ((Location) other).id;
    }

    @Override
    public Long identity() {
        return id;
    } 
}
