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
package com.raytheon.uf.common.pointdata.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.raytheon.uf.common.datastorage.records.IDataRecord;
import com.raytheon.uf.common.datastorage.records.StringDataRecord;
import com.raytheon.uf.common.pointdata.ParameterDescription;
import com.raytheon.uf.common.pointdata.PointDataContainer;
import com.raytheon.uf.common.serialization.annotations.DynamicSerialize;
import com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement;

/**
 * A string data container
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Apr 8, 2009            chammack     Initial creation
 * 
 * </pre>
 * 
 * @author chammack
 * @version 1.0
 */
@DynamicSerialize
@XmlAccessorType(XmlAccessType.NONE)
public class StringPointDataObject extends AbstractPointDataObject<String[]> {
    @DynamicSerializeElement
    @XmlElement
    protected String[] stringData;

    public StringPointDataObject() {
        super(null, (ParameterDescription) null, 1);
    }

    public StringPointDataObject(PointDataContainer container,
            ParameterDescription description, int dims) {
        super(container, description, dims);
        this.stringData = new String[0];
    }

    public StringPointDataObject(PointDataContainer container,
            StringDataRecord rec) {
        super(container, rec);
        this.stringData = rec.getStringData();
    }

    /**
     * @return the stringData
     */
    public String[] getStringData() {
        return stringData;
    }

    /**
     * @param stringData
     *            the stringData to set
     */
    public void setStringData(String[] stringData) {
        this.stringData = stringData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.raytheon.uf.common.pointData.IPointDataObject#getData()
     */
    @Override
    public String[] getData() {

        int curSz = container.getCurrentSz();
        if (this.dimensions > 1)
            curSz *= this.description.getDimensionAsInt();

        if (curSz < stringData.length) {
            String[] newData = new String[curSz];
            System.arraycopy(stringData, 0, newData, 0, curSz);
            return newData;
        } else if (curSz == stringData.length) {
            return stringData;
        } else {
            throw new IllegalStateException(
                    "Data length is greater than array size");
        }
    }

    public void setData(String[] data) {
        this.stringData = data;
    }

    @Override
    public void resize(int sz) {
        String[] newData = new String[sz];
        System.arraycopy(stringData, 0, newData, 0, stringData.length);
        String fill = "";
        for (int i = this.stringData.length; i < newData.length; i++) {
            newData[i] = fill;
        }

        this.stringData = newData;
    }

    @Override
    public IDataRecord getRecord() {
        StringDataRecord sdr = null;
        if (this.dimensions == 1) {
            sdr = new StringDataRecord(getParameterName(), "", getData());
        } else {
            String[] d = getData();
            sdr = new StringDataRecord(getParameterName(), "", d, 2,
                    new long[] { this.description.getDimensionAsInt(),
                            d.length / this.description.getDimensionAsInt() });

            long[] maxSizes = new long[] { 0,
                    this.description.getDimensionAsInt() };
            sdr.setMaxSizes(maxSizes);
            sdr.setMaxChunkSize(STORAGE_CHUNK_SIZE);
        }
        if (this.description.getMaxLength() > 0) {
            sdr.setMaxLength(this.description.getMaxLength());
        }
        setProperties(sdr);
        return sdr;
    }

    @Override
    public Number getNumber(int idx) {
        throw new UnsupportedOperationException(
                "Retrieving number from string field not supported");
    }

    public String getString(int idx) {
        // Assumes range checking is provided by the view
        return stringData[idx];
    }

    public void setString(int idx, String val) {
        if (val == null) {
            val = "";
        }

        // Assumes range checking is provided by the view
        stringData[idx] = val;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.raytheon.uf.common.pointData.elements.AbstractPointDataObject#setNumber
     * (int, java.lang.Number)
     */
    @Override
    public void setNumber(int idx, Number number) {
        throw new UnsupportedOperationException(
                "Setting number to string field not supported");
    }

    @Override
    public void combine(AbstractPointDataObject<?> obj) {
        StringPointDataObject intP = (StringPointDataObject) obj;
        String[] d = new String[intP.stringData.length + stringData.length];
        System.arraycopy(this.stringData, 0, d, 0, this.stringData.length);
        System.arraycopy(intP.stringData, 0, d, this.stringData.length,
                intP.stringData.length);
        this.stringData = d;
    }
}
