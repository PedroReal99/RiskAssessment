/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskMatrixs;

import eapli.framework.domain.model.ValueObject; 
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 *
 * @author CarloS
 */
@Embeddable
public class MatrixVersion implements ValueObject , Comparable<MatrixVersion> {

    private String version;

    @Transient
    private boolean isEdit;

    protected MatrixVersion() {
        //Precistence
    }


    public MatrixVersion(String version) {
        this.isEdit = false;
        this.version = version;
    }

    /**
     * Creates a new instance of MatrixVersion with the specifiedID, this method
     * thi method is not recommended unless you are familiar with this class
     *
     * @param versionId
     * @return
     */
    public static MatrixVersion create(String versionId) {
        return new MatrixVersion(versionId);
    }

    /**
     * Creates a temporary version
     *
     * @return the temporary version
     */
    public static MatrixVersion tempVersion() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        return new MatrixVersion(dateFormat.format(new Date()));
    }

    /**
     * Returns a copy of this version
     *
     * @return
     */
    public MatrixVersion getEditVersion() {
        MatrixVersion ver = new MatrixVersion(this.version + "_edit");
        ver.isEdit = true;
        return ver;
    }

    /**
     * <<<<<<< HEAD Upgrades the version to published
     *
     * @param date date with yyyy/mm/dd format
     * @return false if the version was already published, the date was a past
     * date or had an invalid format
     */
    public boolean publishVersion(String date) {
        if (isFinal()) {
            return false;
        }
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date dateParsed = dateFormat.parse(date);
            if (dateParsed.before(new Date()) && !dateFormat.format(new Date()).equals(date)) {
                return false;
            }
            this.version = date;
        } catch (ParseException ex) {
            return false;
        }

        return true;
    }

    /**
     * Verifies if a matrix is valid for being in force
     *
     * @return True if and only if the publication date is today or today
     */
    public boolean canBeInForce() {
        if (!isFinal()) {
            return false;
        }
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            if (dateFormat.parse(version).before(new Date())) {
                return true;
            }
        } catch (ParseException ex) {
            return false;
        }
        return false;
    }

    /**
     * Upgrades the version to final
     *
     * @return false if the version was already final, else false
     */
    public boolean upgradeVersion() {
        if (isFinal()) {
            return false;
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        this.version = dateFormat.format(new Date());
        return true;
    }


    /**
     * Returns if the MatrixVersion is a copy
     *
     * @return
     */
    public boolean isClone() {
        return isEdit;
    }

/**
             * Returns if the MatrixVersion is final
             *
             * @return
             */

    private boolean isFinal() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            dateFormat.parse(version);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }
    
    @Override

    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.version);
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
        final MatrixVersion other = (MatrixVersion) obj;
        if (!Objects.equals(this.version, other.version)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return version;
    }

    @Override
    public int compareTo(MatrixVersion t) {
        return this.toString().compareTo(t.toString());
    }
}
