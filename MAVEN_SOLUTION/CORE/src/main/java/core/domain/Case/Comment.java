/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Case;

import core.domain.Surrounding.*;
import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author CarloS
 */
@Embeddable
public class Comment implements ValueObject {

    private String comment;

    protected Comment() {
        comment="";
        //precistence implementation
    }
    
    public Comment(String comment) {
        this.comment=comment;
    }

    
    public void addComment(String comment){
        this.comment+="\n"+comment;
    }
    
    /**
     * Returns a string with the name of this object
     * @return 
     */
    @Override
    public String toString(){
        return this.comment;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.comment);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Comment)) {
            return false;
        }
        
        final Comment other = (Comment) obj;
        if (!Objects.equals(this.comment, other.comment)) {
            return false;
        }
        return true;
    }
    
    
}
