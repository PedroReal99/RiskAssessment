/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.dto;

import java.util.Objects;

/**
 *
 * @author Ricardo Branco
 */
public class GeoRefInfoDTO {

    public String duration, quantity, distance, surrounding;

    public GeoRefInfoDTO() {
        this.distance = "Distance 0";
        this.duration = "Duration 0";
        this.quantity = "Quantity 0";
        this.surrounding = "Surrounding";
    }

    public GeoRefInfoDTO(String duration, String distance, String quantity, String surrounding) {
        this.distance = "Distance " + duration;
        this.duration = "Duration " + distance;
        this.quantity = "Quantity " + quantity;
        this.surrounding = "Surrounding " + surrounding;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.duration);
        hash = 83 * hash + Objects.hashCode(this.quantity);
        hash = 83 * hash + Objects.hashCode(this.distance);
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
        final GeoRefInfoDTO other = (GeoRefInfoDTO) obj;
        if (!Objects.equals(this.duration, other.duration)) {
            return false;
        }
        if (!Objects.equals(this.quantity, other.quantity)) {
            return false;
        }
        if (!Objects.equals(this.distance, other.distance)) {
            return false;
        }
        return true;
    }
}
