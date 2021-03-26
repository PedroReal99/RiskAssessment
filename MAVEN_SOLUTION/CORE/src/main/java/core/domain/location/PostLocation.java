/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.location;

import java.util.Objects;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author Ricardo Branco
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoLoc")
@DiscriminatorValue("POST")
public class PostLocation extends Location {

    private String country = "";
    private String road = "";
    private String number = "";
    private String post_code = "";

    public PostLocation() {
    }

    protected PostLocation(PostLocationBuilder builder) {
        super(builder.district);
        this.country = builder.country;
        this.road = builder.road;
        this.number = builder.number;
        this.post_code = builder.post_code;
    }

    public PostLocation(String country, String district, String road, String number, String post_code) {
        super(district);
        this.country = country;
        this.road = road;
        this.number = number;
        this.post_code = post_code;
    }

    public PostLocation(String road, String number, String post_code) {
        this.road = road;
        this.number = number;
        this.post_code = post_code;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDistrict(String district) {
        super.setDistrict(district);
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.country);
        hash = 37 * hash + Objects.hashCode(super.getDistrict());
        hash = 37 * hash + Objects.hashCode(this.road);
        hash = 37 * hash + Objects.hashCode(this.number);
        hash = 37 * hash + Objects.hashCode(this.post_code);
        return hash;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String getDistrict() {
        return super.getDistrict();
    }

    public String getRoad() {
        return road;
    }

    public String getNumber() {
        return number;
    }

    public String getPost_code() {
        return post_code;
    }

    @Override
    public String toString() {
        return number + "," + road + "," + super.getDistrict() + "," + country + "," + post_code;
    }

    @Override
    public String getLocation() {
        return toString();
    }

    @Override
    public boolean sameAs(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PostLocation other = (PostLocation) obj;
        return Objects.equals(this.post_code, other.post_code);
    }

}
