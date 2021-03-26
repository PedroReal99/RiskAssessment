/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Surrounding;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author CarloS
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SurroundingType implements AggregateRoot<STName>, Serializable {

    @EmbeddedId
    private STName tipo;

    protected SurroundingType() {
        //Precistence constructor
    }

    /**
     * Constructor of class SurrroudingType
     *
     * @param categoria
     */
    public SurroundingType(STName categoria) {
        Preconditions.nonNull(categoria);
        this.tipo = categoria;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.tipo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SurroundingType)) {
            return false;
        }

        final SurroundingType other = (SurroundingType) obj;
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        return true;
    }

    public STName obtainSTName() {
        return getSTName();
    }

    private STName getSTName() {
        return tipo;
    }

    @Override
    public String toString(){
        return tipo.toString();
    }
    
    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public STName identity() {
        return this.tipo;
    }

}
