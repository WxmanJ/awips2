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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Fpinfo generated by hbm2java
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
@Table(name = "fpinfo")
@javax.xml.bind.annotation.XmlRootElement
@javax.xml.bind.annotation.XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.NONE)
@com.raytheon.uf.common.serialization.annotations.DynamicSerialize
public class Fpinfo extends com.raytheon.uf.common.dataplugin.persist.PersistableDataObject implements java.io.Serializable, com.raytheon.uf.common.serialization.ISerializableObject {

    private static final long serialVersionUID = 1L;

    @javax.xml.bind.annotation.XmlElement
    @com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement
    private FpinfoId id;

    public Fpinfo() {
    }

    public Fpinfo(FpinfoId id) {
        this.id = id;
    }

    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name = "lid", column = @Column(name = "lid", length = 8)),
            @AttributeOverride(name = "name", column = @Column(name = "name", length = 50)),
            @AttributeOverride(name = "county", column = @Column(name = "county", length = 20)),
            @AttributeOverride(name = "state", column = @Column(name = "state", length = 2)),
            @AttributeOverride(name = "hsa", column = @Column(name = "hsa", length = 3)),
            @AttributeOverride(name = "primaryBack", column = @Column(name = "primary_back", length = 3)),
            @AttributeOverride(name = "secondaryBack", column = @Column(name = "secondary_back", length = 3)),
            @AttributeOverride(name = "stream", column = @Column(name = "stream", length = 32)),
            @AttributeOverride(name = "bf", column = @Column(name = "bf", precision = 17, scale = 17)),
            @AttributeOverride(name = "wstg", column = @Column(name = "wstg", precision = 17, scale = 17)),
            @AttributeOverride(name = "fs", column = @Column(name = "fs", precision = 17, scale = 17)),
            @AttributeOverride(name = "fq", column = @Column(name = "fq", precision = 17, scale = 17)),
            @AttributeOverride(name = "actionFlow", column = @Column(name = "action_flow", precision = 17, scale = 17)),
            @AttributeOverride(name = "pe", column = @Column(name = "pe", length = 2)),
            @AttributeOverride(name = "useLatestFcst", column = @Column(name = "use_latest_fcst", length = 1)),
            @AttributeOverride(name = "proximity", column = @Column(name = "proximity", length = 6)),
            @AttributeOverride(name = "reach", column = @Column(name = "reach", length = 80)),
            @AttributeOverride(name = "groupId", column = @Column(name = "group_id", length = 8)),
            @AttributeOverride(name = "ordinal", column = @Column(name = "ordinal")),
            @AttributeOverride(name = "chgThreshold", column = @Column(name = "chg_threshold", precision = 17, scale = 17)),
            @AttributeOverride(name = "recType", column = @Column(name = "rec_type", length = 3)),
            @AttributeOverride(name = "backhrs", column = @Column(name = "backhrs")),
            @AttributeOverride(name = "forwardhrs", column = @Column(name = "forwardhrs")),
            @AttributeOverride(name = "adjustendhrs", column = @Column(name = "adjustendhrs", precision = 17, scale = 17)),
            @AttributeOverride(name = "minorStage", column = @Column(name = "minor_stage", precision = 17, scale = 17)),
            @AttributeOverride(name = "moderateStage", column = @Column(name = "moderate_stage", precision = 17, scale = 17)),
            @AttributeOverride(name = "majorStage", column = @Column(name = "major_stage", precision = 17, scale = 17)),
            @AttributeOverride(name = "minorFlow", column = @Column(name = "minor_flow", precision = 17, scale = 17)),
            @AttributeOverride(name = "moderateFlow", column = @Column(name = "moderate_flow", precision = 17, scale = 17)),
            @AttributeOverride(name = "majorFlow", column = @Column(name = "major_flow", precision = 17, scale = 17)) })
    public FpinfoId getId() {
        return this.id;
    }

    public void setId(FpinfoId id) {
        this.id = id;
    }

}
