/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.location;

import static core.domain.location.GPSLocation.normalize90;

/**
 *
 * @author joaoflores
 */
public class GPSLocationBuilder {

    // required parameters
    protected float latitude;
    protected float longitude;

    //optional
    protected float height;
    protected String district;

    public GPSLocationBuilder(float latitude, float longitude) {
        float[] val = normalize90(longitude, latitude);
        this.latitude = val[0];
        this.longitude = val[1];
    }

    public GPSLocationBuilder withAltitude(float height) {
        this.height = height;
        return this;
    }

    public GPSLocationBuilder withDistrict(String district) {
        this.district = district;
        return this;
    }

    public GPSLocation build() {
        return new GPSLocation(this);
    }

}
