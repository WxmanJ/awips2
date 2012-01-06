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

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Fcstptwatsup generated by hbm2java
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
@Entity
@Table(name = "fcstptwatsup")
@javax.xml.bind.annotation.XmlRootElement
@javax.xml.bind.annotation.XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.NONE)
@com.raytheon.uf.common.serialization.annotations.DynamicSerialize
public class Fcstptwatsup extends com.raytheon.uf.common.dataplugin.persist.PersistableDataObject implements java.io.Serializable, com.raytheon.uf.common.serialization.ISerializableObject {

    private static final long serialVersionUID = 1L;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private FcstptwatsupId id;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private Fcstptservice fcstptservice;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private Watsupcriterion watsupcriterion;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private Watsupcoordagency watsupcoordagency;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private Requiredperiod requiredperiod;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private Watsupmethod watsupmethod;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private Frequencyupdate frequencyupdate;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private Watsuprespagency watsuprespagency;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private String customerDesc;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private Date implDate;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private Date webDate;

    public Fcstptwatsup() {
    }

    public Fcstptwatsup(FcstptwatsupId id, Fcstptservice fcstptservice,
            Watsupcriterion watsupcriterion,
            Watsupcoordagency watsupcoordagency, Requiredperiod requiredperiod,
            Watsupmethod watsupmethod, Frequencyupdate frequencyupdate) {
        this.id = id;
        this.fcstptservice = fcstptservice;
        this.watsupcriterion = watsupcriterion;
        this.watsupcoordagency = watsupcoordagency;
        this.requiredperiod = requiredperiod;
        this.watsupmethod = watsupmethod;
        this.frequencyupdate = frequencyupdate;
    }

    public Fcstptwatsup(FcstptwatsupId id, Fcstptservice fcstptservice,
            Watsupcriterion watsupcriterion,
            Watsupcoordagency watsupcoordagency, Requiredperiod requiredperiod,
            Watsupmethod watsupmethod, Frequencyupdate frequencyupdate,
            Watsuprespagency watsuprespagency, String customerDesc,
            Date implDate, Date webDate) {
        this.id = id;
        this.fcstptservice = fcstptservice;
        this.watsupcriterion = watsupcriterion;
        this.watsupcoordagency = watsupcoordagency;
        this.requiredperiod = requiredperiod;
        this.watsupmethod = watsupmethod;
        this.frequencyupdate = frequencyupdate;
        this.watsuprespagency = watsuprespagency;
        this.customerDesc = customerDesc;
        this.implDate = implDate;
        this.webDate = webDate;
    }

    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name = "lid", column = @Column(name = "lid", nullable = false, length = 8)),
            @AttributeOverride(name = "watsupMethod", column = @Column(name = "watsup_method", nullable = false, length = 50)),
            @AttributeOverride(name = "watsupCoordAgency", column = @Column(name = "watsup_coord_agency", nullable = false, length = 64)),
            @AttributeOverride(name = "frequpdNormal", column = @Column(name = "frequpd_normal", nullable = false, length = 30)),
            @AttributeOverride(name = "periodReq", column = @Column(name = "period_req", nullable = false, length = 30)),
            @AttributeOverride(name = "watsupCrit", column = @Column(name = "watsup_crit", nullable = false, length = 30)) })
    public FcstptwatsupId getId() {
        return this.id;
    }

    public void setId(FcstptwatsupId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lid", nullable = false, insertable = false, updatable = false)
    public Fcstptservice getFcstptservice() {
        return this.fcstptservice;
    }

    public void setFcstptservice(Fcstptservice fcstptservice) {
        this.fcstptservice = fcstptservice;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "watsup_crit", nullable = false, insertable = false, updatable = false)
    public Watsupcriterion getWatsupcriterion() {
        return this.watsupcriterion;
    }

    public void setWatsupcriterion(Watsupcriterion watsupcriterion) {
        this.watsupcriterion = watsupcriterion;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "watsup_coord_agency", nullable = false, insertable = false, updatable = false)
    public Watsupcoordagency getWatsupcoordagency() {
        return this.watsupcoordagency;
    }

    public void setWatsupcoordagency(Watsupcoordagency watsupcoordagency) {
        this.watsupcoordagency = watsupcoordagency;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "period_req", nullable = false, insertable = false, updatable = false)
    public Requiredperiod getRequiredperiod() {
        return this.requiredperiod;
    }

    public void setRequiredperiod(Requiredperiod requiredperiod) {
        this.requiredperiod = requiredperiod;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "watsup_method", nullable = false, insertable = false, updatable = false)
    public Watsupmethod getWatsupmethod() {
        return this.watsupmethod;
    }

    public void setWatsupmethod(Watsupmethod watsupmethod) {
        this.watsupmethod = watsupmethod;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "frequpd_normal", nullable = false, insertable = false, updatable = false)
    public Frequencyupdate getFrequencyupdate() {
        return this.frequencyupdate;
    }

    public void setFrequencyupdate(Frequencyupdate frequencyupdate) {
        this.frequencyupdate = frequencyupdate;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "watsup_resp_agency")
    public Watsuprespagency getWatsuprespagency() {
        return this.watsuprespagency;
    }

    public void setWatsuprespagency(Watsuprespagency watsuprespagency) {
        this.watsuprespagency = watsuprespagency;
    }

    @Column(name = "customer_desc", length = 80)
    public String getCustomerDesc() {
        return this.customerDesc;
    }

    public void setCustomerDesc(String customerDesc) {
        this.customerDesc = customerDesc;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "impl_date", length = 13)
    public Date getImplDate() {
        return this.implDate;
    }

    public void setImplDate(Date implDate) {
        this.implDate = implDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "web_date", length = 13)
    public Date getWebDate() {
        return this.webDate;
    }

    public void setWebDate(Date webDate) {
        this.webDate = webDate;
    }

}
