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
package com.raytheon.uf.common.dataplugin.grib.subgrid;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;

import com.raytheon.uf.common.serialization.ISerializableObject;

/**
 * A sub grid definition
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Jun 25, 2010            rjpeter     Initial creation
 * 
 * </pre>
 * 
 * @author rjpeter
 * @version 1.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class SubGridDef implements ISerializableObject {
    /** The model names this sub grid applies to */
    @XmlElement(required = true)
    @XmlList
    private List<String> modelNames;
    
    @XmlElement(required = true)
    private String referenceModel;

    /** the lower left latitude */
    @XmlElement
    private double lowerLeftLat;

    /** the lower left longitude */
    @XmlElement
    private double lowerLeftLon;

    /** the upper right latitude */
    @XmlElement
    private double upperRightLat;

    /** the upper right longitude */
    @XmlElement
    private double upperRightLon;
    
    @XmlElement(required = true)
    private double nx;
    
    @XmlElement(required = true)
    private double ny;

    public List<String> getModelNames() {
        return modelNames;
    }

    public void setModelNames(List<String> modelNames) {
        this.modelNames = modelNames;
    }

    public double getLowerLeftLat() {
        return lowerLeftLat;
    }

    public void setLowerLeftLat(double lowerLeftLat) {
        this.lowerLeftLat = lowerLeftLat;
    }

    public double getLowerLeftLon() {
        return lowerLeftLon;
    }

    public void setLowerLeftLon(double lowerLeftLon) {
        this.lowerLeftLon = lowerLeftLon;
    }

    public double getUpperRightLat() {
        return upperRightLat;
    }

    public void setUpperRightLat(double upperRightLat) {
        this.upperRightLat = upperRightLat;
    }

    public double getUpperRightLon() {
        return upperRightLon;
    }

    public void setUpperRightLon(double upperRightLon) {
        this.upperRightLon = upperRightLon;
    }

    public double getNx() {
        return nx;
    }

    public void setNx(double nx) {
        this.nx = nx;
    }

    public double getNy() {
        return ny;
    }

    public void setNy(double ny) {
        this.ny = ny;
    }

    public String getReferenceModel() {
        return referenceModel;
    }

    public void setReferenceModel(String referenceModel) {
        this.referenceModel = referenceModel;
    }
    
    
}