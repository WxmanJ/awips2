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
package com.raytheon.hprof.data.heap.dump;

import com.raytheon.hprof.BigByteBuffer;
import com.raytheon.hprof.data.HeapDumpRecord;
import com.raytheon.hprof.data.heap.BasicType;

/**
 * 
 * Data for an array of primitives in a {@link HeapDumpRecord}.
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * 
 * Date          Ticket#  Engineer    Description
 * ------------- -------- ----------- --------------------------
 * Jan 08, 2014  2648     bsteffen    Initial doc
 * May 20, 2014  3093     bsteffen    Allow creation without reading full array.
 * 
 * </pre>
 * 
 * @author bsteffen
 * @version 1.0
 */
public class PrimitiveArrayDump extends AbstractDump {

    public static final int TAG = 0x23;

    private final BasicType[] array;

    public PrimitiveArrayDump(BigByteBuffer buffer, int idSize,
            boolean readArray) {
        super(buffer, idSize);
        int numElements = buffer.getInt();
        byte type = buffer.get();
        if (readArray) {
            array = new BasicType[numElements];
            for (int i = 0; i < numElements; i++) {
                array[i] = new BasicType(buffer, type, idSize);
            }
        } else {
            array = null;
            BasicType tmp = new BasicType(buffer, type, idSize);
            int skip = (numElements - 1) * tmp.getSize();
            buffer.position(buffer.position() + skip);
        }
    }

    public BasicType[] getArray() {
        return array;
    }

}
