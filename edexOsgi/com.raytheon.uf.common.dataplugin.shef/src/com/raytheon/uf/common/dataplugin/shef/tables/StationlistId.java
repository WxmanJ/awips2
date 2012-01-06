/**
* This software was developed and / or modified by Raytheon Company,
* pursuant to Contract DG133W-05-CQ-1067 with the US Government.
* 
* U.S. EXPORT CONTROLLED TECHNICAL DATA
* This software product contains export-restricted data whose
* export/transfer/disclosure is restricted by U.S. law. Dissemination
* to non-U.S. persons whether in the United States or abroad requires
* an export license or other authorization.
* 
* Contractor Name:        Raytheon Company
* Contractor Address:     6825 Pine Street, Suite 340
*                         Mail Stop B8
*                         Omaha, NE 68106
*                         402.291.0100
* 
* See the AWIPS II Master Rights File ("Master Rights File.pdf") for
* further licensing information.
**/
package com.raytheon.uf.common.dataplugin.shef.tables;
// default package
// Generated Oct 17, 2008 2:22:17 PM by Hibernate Tools 3.2.2.GA

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * StationlistId generated by hbm2java
 * 
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Oct 17, 2008                        Initial generation by hbm2java
 * Aug 19, 2011      10672     jkorman Move refactor to new project
 * 
 * </pre>
 * 
 * @author jkorman
 * @version 1.1
 */
@Embeddable
@javax.xml.bind.annotation.XmlRootElement
@javax.xml.bind.annotation.XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.NONE)
@com.raytheon.uf.common.serialization.annotations.DynamicSerialize
public class StationlistId extends com.raytheon.uf.common.dataplugin.persist.PersistableDataObject implements java.io.Serializable, com.raytheon.uf.common.serialization.ISerializableObject {

    private static final long serialVersionUID = 1L;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private String lid;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private String name;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private String firstname;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private String lastname;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private String rb;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private String county;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private String wfo;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private String hphone;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private String ophone;

    public StationlistId() {
    }

    public StationlistId(String lid, String name, String firstname,
            String lastname, String rb, String county, String wfo,
            String hphone, String ophone) {
        this.lid = lid;
        this.name = name;
        this.firstname = firstname;
        this.lastname = lastname;
        this.rb = rb;
        this.county = county;
        this.wfo = wfo;
        this.hphone = hphone;
        this.ophone = ophone;
    }

    @Column(name = "lid", length = 8)
    public String getLid() {
        return this.lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    @Column(name = "name", length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "firstname", length = 12)
    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "lastname", length = 28)
    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Column(name = "rb", length = 30)
    public String getRb() {
        return this.rb;
    }

    public void setRb(String rb) {
        this.rb = rb;
    }

    @Column(name = "county", length = 20)
    public String getCounty() {
        return this.county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Column(name = "wfo", length = 3)
    public String getWfo() {
        return this.wfo;
    }

    public void setWfo(String wfo) {
        this.wfo = wfo;
    }

    @Column(name = "hphone", length = 18)
    public String getHphone() {
        return this.hphone;
    }

    public void setHphone(String hphone) {
        this.hphone = hphone;
    }

    @Column(name = "ophone", length = 18)
    public String getOphone() {
        return this.ophone;
    }

    public void setOphone(String ophone) {
        this.ophone = ophone;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof StationlistId))
            return false;
        StationlistId castOther = (StationlistId) other;

        return ((this.getLid() == castOther.getLid()) || (this.getLid() != null
                && castOther.getLid() != null && this.getLid().equals(
                castOther.getLid())))
                && ((this.getName() == castOther.getName()) || (this.getName() != null
                        && castOther.getName() != null && this.getName()
                        .equals(castOther.getName())))
                && ((this.getFirstname() == castOther.getFirstname()) || (this
                        .getFirstname() != null
                        && castOther.getFirstname() != null && this
                        .getFirstname().equals(castOther.getFirstname())))
                && ((this.getLastname() == castOther.getLastname()) || (this
                        .getLastname() != null
                        && castOther.getLastname() != null && this
                        .getLastname().equals(castOther.getLastname())))
                && ((this.getRb() == castOther.getRb()) || (this.getRb() != null
                        && castOther.getRb() != null && this.getRb().equals(
                        castOther.getRb())))
                && ((this.getCounty() == castOther.getCounty()) || (this
                        .getCounty() != null
                        && castOther.getCounty() != null && this.getCounty()
                        .equals(castOther.getCounty())))
                && ((this.getWfo() == castOther.getWfo()) || (this.getWfo() != null
                        && castOther.getWfo() != null && this.getWfo().equals(
                        castOther.getWfo())))
                && ((this.getHphone() == castOther.getHphone()) || (this
                        .getHphone() != null
                        && castOther.getHphone() != null && this.getHphone()
                        .equals(castOther.getHphone())))
                && ((this.getOphone() == castOther.getOphone()) || (this
                        .getOphone() != null
                        && castOther.getOphone() != null && this.getOphone()
                        .equals(castOther.getOphone())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result
                + (getLid() == null ? 0 : this.getLid().hashCode());
        result = 37 * result
                + (getName() == null ? 0 : this.getName().hashCode());
        result = 37 * result
                + (getFirstname() == null ? 0 : this.getFirstname().hashCode());
        result = 37 * result
                + (getLastname() == null ? 0 : this.getLastname().hashCode());
        result = 37 * result + (getRb() == null ? 0 : this.getRb().hashCode());
        result = 37 * result
                + (getCounty() == null ? 0 : this.getCounty().hashCode());
        result = 37 * result
                + (getWfo() == null ? 0 : this.getWfo().hashCode());
        result = 37 * result
                + (getHphone() == null ? 0 : this.getHphone().hashCode());
        result = 37 * result
                + (getOphone() == null ? 0 : this.getOphone().hashCode());
        return result;
    }

}
