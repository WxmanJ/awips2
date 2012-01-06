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
package com.raytheon.viz.gfe.smarttool.script;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Shell;

import com.raytheon.viz.gfe.core.DataManager;
import com.raytheon.viz.gfe.smartscript.FieldDefinition;
import com.raytheon.viz.gfe.ui.runtimeui.SelectionDlg;

/**
 * TODO Add Description
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * 
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Nov 8, 2010            dgilling     Initial creation
 * 
 * </pre>
 * 
 * @author dgilling
 * @version 1.0
 */

public class SmartToolBlockingSelectionDlg extends SelectionDlg {

    private boolean run = false;

    /**
     * @param parent
     * @param title
     * @param dataMgr
     * @param fieldDefs
     */
    public SmartToolBlockingSelectionDlg(Shell parent, String title,
            DataManager dataMgr, List<FieldDefinition> fieldDefs) {
        super(parent, title, dataMgr, fieldDefs);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.raytheon.viz.gfe.ui.runtimeui.SelectionDlg#run()
     */
    @Override
    public void run() {
        run = true;
    }

    public Map<String, Object> getVarDictResult() {
        Map<String, Object> varDictResult = null;
        if (run) {
            varDictResult = this.getValues();
        }
        return varDictResult;
    }

}
