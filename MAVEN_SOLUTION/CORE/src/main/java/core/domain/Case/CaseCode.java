/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Case;

import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Pedro
 */
@Embeddable
public class CaseCode implements ValueObject , Comparable<CaseCode>{
    
    private String code;
    
    protected CaseCode(){
        //persistence implementation
    }
    
    public CaseCode(String code){
        this.code=validate(code);
    }
    
    /**
     * Validates code
     * @param code
     * @return 
     */
    private static String validate(String code){
        if(code!=null&&code.trim().matches("([A-Za-zçÇãâÃÂáÁàÀèÈéÉêÊíìÍÌîÎóòÓÒõÕôÔúùÚÙûÛ0-9]+\\s?)+")){
            return code.trim();
        }else{
            throw new IllegalArgumentException("The code is not valid!");
        }
    }

    @Override
    public String toString() {
        return getCode();
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.getCode());
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
        if (!(obj instanceof CaseCode )) {
            return false;
        }
        final CaseCode other = (CaseCode) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

    /**
     * @return the code
     */
    private String getCode() {
        return code;
    }

    @Override
    public int compareTo(CaseCode t) {
        return this.getCode().compareTo(t.getCode());
    }

    
    
    
}
