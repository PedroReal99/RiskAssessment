package core.domain.location;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoLoc")
@DiscriminatorValue("GPS")
public class GPSLocation extends Location {

    private float latitude;
    private float longitude;
    private float height;

    /**
     * Empty constructor
     */
    public GPSLocation() {
        this.height = 0;
        this.latitude = 0;
        this.longitude = 0;
    }

    /**
     * Constructor of class Location
     *
     * @param latitude
     * @param longitude
     * @param altitude
     * @param district
     */
    public GPSLocation(float latitude, float longitude, float altitude, String district) {
        super(district);
        float[] val = normalize90(longitude, latitude);
        this.latitude = val[0];
        this.longitude = val[1];
        this.height = altitude;
    }

    public GPSLocation(GPSLocationBuilder glb) {
        super(glb.district);
        this.latitude = glb.latitude;
        this.longitude = glb.longitude;
        this.height = glb.height;
    }

    /**
     * Returns the latitude of this location
     *
     * @return
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Sets a new latitude to this location
     *
     * @param latitude
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * Returns the longitude of this location
     *
     * @return
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Sets a new longitude to this location
     *
     * @param longitude
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * Returns the height of this location
     *
     * @return
     */
    public float getAltitude() {
        return height;
    }

    /**
     * Sets a new height to this location
     *
     * @param altitude
     */
    public void setAltitude(float altitude) {
        this.height = altitude;
    }

    /**
     * Transforms an angle into its [-180º,180º] equivalent
     *
     * @param angle
     * @return
     */
    protected static float normalize180(float angle) {
        float nAngle = angle;
        while (nAngle > 180) {
            nAngle -= 360;
        }

        while (nAngle < -180) {
            nAngle += 360;
        }

        return nAngle;
    }

    /**
     * Transforms an angle into its [-90º,90º] equivalent
     *
     * @param latitude
     * @return
     */
    protected static float[] normalize90(float longitude, float latitude) {
        float[] latLng = new float[2];

        latLng[0] = normalize180(latitude);

        if (latLng[0] < -90) {
            latLng[0] = -180 - latLng[0];
            latLng[1] = normalize180(longitude + 180);
            return latLng;
        }

        if (latLng[0] > 90) {
            latLng[0] = 180 - latLng[0];
            latLng[1] = normalize180(longitude + 180);
            return latLng;
        }

        latLng[1] = normalize180(longitude);
        return latLng;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Float.floatToIntBits(this.latitude);
        hash = 97 * hash + Float.floatToIntBits(this.longitude);
        hash = 97 * hash + Float.floatToIntBits(this.height);
        return hash;
    }

    @Override
    public String toString() {
        return latitude + "," + longitude + "," + height;
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
        final GPSLocation other = (GPSLocation) obj;
        if (Float.floatToIntBits(this.latitude) != Float.floatToIntBits(other.latitude)) {
            return false;
        }
        if (Float.floatToIntBits(this.longitude) == Float.floatToIntBits(other.longitude)) {
            return false;
        }
        return Float.floatToIntBits(this.height) == Float.floatToIntBits(other.height);
    }

}
