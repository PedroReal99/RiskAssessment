/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Case;

import eapli.framework.domain.model.ValueObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author morei
 */
@Embeddable
public class CaseDate implements ValueObject {

    private String date;

    protected CaseDate() {

    }

    public CaseDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.date);
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
        final CaseDate other = (CaseDate) obj;
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

    public static CaseDate now() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = dateFormat.format(now);
        return new CaseDate(strDate);
    }

}
