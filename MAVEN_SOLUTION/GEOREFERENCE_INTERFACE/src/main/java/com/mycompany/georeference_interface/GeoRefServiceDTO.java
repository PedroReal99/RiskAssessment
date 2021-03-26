/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.georeference_interface;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author joaoflores
 */
public class GeoRefServiceDTO implements Serializable {

    /**
     * GPSLocation atributes
     */
    public double latitude;
    public double longitude;
    public double height;
    public String district;

    /**
     * PostLocation atributes
     */
    public String country;
    public String road;
    public int number;
    public String post_code;

    /**
     * Distance and duration attributes
     */
    public int duration;
    public int distance;

    public String name;

    /**
     * Default GPSLocation DTO constructor
     *
     */
    public GeoRefServiceDTO() {
        latitude = 0;
        longitude = 0;
        height = 0;
        district = "";

        country = "";
        road = "";
        number = -1;
        post_code = "";
        name = "";
        duration = 0;
        distance = 0;
    }

    /**
     * GPSLocation DTO constructor
     *
     * @param distance
     * @param duration
     */
    public GeoRefServiceDTO(int distance, int duration) {
        latitude = 0;
        longitude = 0;
        name = "";
        post_code = "";
        this.number = -1;
        this.duration = duration;
        this.distance = distance;
    }

    /**
     * GPSLocation DTO constructor
     *
     * @param latitude
     * @param longitude
     * @param height
     * @param district
     */
    public GeoRefServiceDTO(double latitude, double longitude, double height, String district) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.height = height;
        this.number = -1;
    }

    /**
     * PostLocation DTO constructor
     *
     * @param country
     * @param district
     * @param road
     * @param number
     * @param post_code
     */
    public GeoRefServiceDTO(int number, String road, String district, String country, String post_code, String name, double latitude, double longitude) {
        this.number = number;
        this.road = road;
        this.district = district;
        this.country = country;
        this.post_code = post_code;
        this.name = name;
        this.distance = -1;
        this.duration = -1;
        this.height = -1;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Adiciona a informação do segundo dto ao primeiro
     *
     * @param dto
     * @return
     */
    public GeoRefServiceDTO merge(GeoRefServiceDTO dto) {
        if (dto == null) {
            return this;
        }
        if (this.country == null || this.country.trim().isEmpty()) {
            this.country = dto.country;
        }

        if (this.district == null || this.district.trim().isEmpty()) {
            this.district = dto.district;
        }

        if (this.name == null || this.name.trim().isEmpty()) {
            this.name = dto.name;
        }

        if (this.number == -1) {
            this.number = dto.number;
        }

        if (this.post_code == null || this.post_code.trim().isEmpty()) {
            this.post_code = dto.post_code;
        } else if (dto.post_code != null) {
            boolean has;
            if ((has = this.post_code.contains("-")) == dto.post_code.contains("-")) {
                if (dto.post_code.length() > this.post_code.length()) {
                    this.post_code = dto.post_code;
                }
            } else {
                if (!has) {
                    this.post_code = dto.post_code;
                }
            }
        }

        if (this.road == null || this.road.trim().isEmpty()) {
            this.road = dto.road;
        }

        if (this.latitude != 0) {
            if (dto.latitude != 0) {
                this.latitude = (this.latitude + dto.latitude) / 2;
            }
        } else {
            this.latitude = dto.latitude;
        }

        if (this.longitude != 0) {
            if (dto.longitude != 0) {
                this.longitude = (this.longitude + dto.longitude) / 2;
            }
        } else {
            this.longitude = dto.longitude;
        }

        if (this.distance == 0) {
            this.distance = dto.distance;
        } else if (dto.distance > 0) {
            this.distance += dto.distance;
            this.distance /= 2;
        }

        if (this.duration == 0) {
            this.duration = dto.duration;
        } else if (dto.duration > 0) {
            this.duration += dto.duration;
            this.duration /= 2;
        }

        if (this.height == 0) {
            this.height = dto.height;
        } else if (dto.height > 0) {
            this.height += dto.height;
            this.height /= 2;
        }

        return this;
    }

    public int getDuration() {
        return duration;
    }

    public int getDistance() {
        return distance;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(Math.round((this.latitude) * 1000));
        hash = 37 * hash + Objects.hashCode(Math.round((this.longitude) * 1000));
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
        final GeoRefServiceDTO other = (GeoRefServiceDTO) obj;
        if (Math.abs(this.longitude - other.longitude) > 0.0015) {
            return false;
        }
        if (Math.abs(this.latitude - other.latitude) > 0.0015) {
            return false;
        }

        if (this.name.length() < other.name.length()) {
            this.name = other.name;
        } else {
            other.name = this.name;
        }

        return true;
    }

}
