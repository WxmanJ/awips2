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
package com.raytheon.viz.gfe.textformatter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import jep.JepException;

import com.raytheon.uf.common.dataplugin.gfe.exception.GfeException;
import com.raytheon.uf.common.dataplugin.gfe.python.GfePyIncludeUtil;
import com.raytheon.uf.common.dataplugin.gfe.request.SaveCombinationsFileRequest;
import com.raytheon.uf.common.localization.IPathManager;
import com.raytheon.uf.common.localization.LocalizationContext;
import com.raytheon.uf.common.localization.LocalizationContext.LocalizationLevel;
import com.raytheon.uf.common.localization.LocalizationContext.LocalizationType;
import com.raytheon.uf.common.localization.LocalizationFile;
import com.raytheon.uf.common.localization.PathManagerFactory;
import com.raytheon.uf.common.localization.exception.LocalizationException;
import com.raytheon.uf.common.localization.exception.LocalizationOpFailedException;
import com.raytheon.uf.common.python.PyUtil;
import com.raytheon.uf.common.python.PythonScript;
import com.raytheon.uf.common.serialization.ISerializableObject;
import com.raytheon.uf.common.serialization.SerializationException;
import com.raytheon.uf.common.serialization.SerializationUtil;
import com.raytheon.uf.common.status.IUFStatusHandler;
import com.raytheon.uf.common.status.UFStatus;
import com.raytheon.uf.common.util.FileUtil;
import com.raytheon.viz.gfe.core.DataManagerUIFactory;
import com.raytheon.viz.gfe.core.internal.IFPClient;
import com.raytheon.viz.gfe.textformatter.CombinationsFileUtil.ComboData.Entry;

/**
 * Creates a combinations file used to keep track of the areas in text formatter
 * 
 * <pre>
 * SOFTWARE HISTORY
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Jul 25, 2008            mnash       Initial creation
 * Sep 05, 2013     #2329  randerso    Moved genereateAutoCombinationsFile here
 *                                     Cleaned up error handling
 * 
 * </pre>
 * 
 * @author mnash
 * @version 1.0
 */

public class CombinationsFileUtil {
    private static final transient IUFStatusHandler statusHandler = UFStatus
            .getHandler(CombinationsFileUtil.class);

    public static String COMBO_DIR_PATH = FileUtil.join("gfe", "combinations");

    public static String SAVED_COMBO_DIR = FileUtil.join("gfe", "comboData");

    @XmlRootElement
    @XmlAccessorType(XmlAccessType.NONE)
    public static class ComboData implements ISerializableObject {

        @XmlRootElement
        @XmlAccessorType(XmlAccessType.NONE)
        public static class Entry {
            @XmlAttribute
            public int group;

            @XmlAttribute
            public String zone;

            public Entry() {
            }

            public Entry(String zone, int group) {
                this.zone = zone;
                this.group = group;
            }
        }

        @XmlElement(name = "entry")
        private List<Entry> combos;

        public ComboData() {
        }

        public ComboData(Map<String, Integer> comboDict) {
            this.combos = new ArrayList<CombinationsFileUtil.ComboData.Entry>(
                    comboDict.size());
            for (java.util.Map.Entry<String, Integer> entry : comboDict
                    .entrySet()) {
                this.combos.add(new Entry(entry.getKey(), entry.getValue()));
            }
        }
    }

    /**
     * Gets the saved combo files for the menus in text formatter
     * 
     * @return the saved combo files
     */

    public static LocalizationFile[] getSavedCombos() {
        IPathManager pm = PathManagerFactory.getPathManager();
        LocalizationFile[] combos = pm.listStaticFiles(SAVED_COMBO_DIR,
                new String[] { ".xml" }, false, true);

        return combos;
    }

    public static String fileToId(LocalizationFile file) {
        File f = new File(file.getName());
        String id = f.getName().replace(".xml", "");
        id = FileUtil.unmangle(id);

        return id;
    }

    public static LocalizationFile idToFile(String id) {
        String s = FileUtil.mangle(id) + ".xml";
        IPathManager pm = PathManagerFactory.getPathManager();
        LocalizationFile lf = pm.getLocalizationFile(pm.getContext(
                LocalizationType.CAVE_STATIC, LocalizationLevel.SITE), FileUtil
                .join(SAVED_COMBO_DIR, s));
        return lf;
    }

    public static void saveComboData(String id, Map<String, Integer> combos)
            throws LocalizationException, SerializationException {
        LocalizationFile lf = idToFile(id);
        File file = lf.getFile(false);
        ComboData comboData = new ComboData(combos);
        SerializationUtil.jaxbMarshalToXmlFile(comboData, file.getPath());
        lf.save();
    }

    public static void deleteComboData(String id)
            throws LocalizationOpFailedException {
        LocalizationFile lf = idToFile(id);
        lf.delete();
    }

    public static String nameToFN(List<String> mapNames, String name) {
        StringBuilder s = new StringBuilder();
        for (String mapName : mapNames) {
            s.append(mapName);
        }
        s.append(':').append(name);

        return s.toString();
    }

    public static String fnToName(List<String> mapNames, String fn) {
        StringBuilder s = new StringBuilder();
        for (String mapName : mapNames) {
            s.append(mapName);
        }
        s.append(':');

        if (fn.length() > s.length() && fn.indexOf(s.toString()) == 0) {
            return fn.substring(s.length());
        } else {
            return "";
        }
    }

    public static Map<String, Integer> loadComboData(String id)
            throws SerializationException {
        LocalizationFile lf = idToFile(id);
        File file = lf.getFile();
        ComboData comboData = (ComboData) SerializationUtil
                .jaxbUnmarshalFromXmlFile(file);

        Map<String, Integer> comboDict = new HashMap<String, Integer>(
                comboData.combos.size());
        for (Entry entry : comboData.combos) {
            comboDict.put(entry.zone, entry.group);
        }

        return comboDict;
    }

    @SuppressWarnings("unchecked")
    public static List<List<String>> init(String comboName) throws GfeException {

        IPathManager pm = PathManagerFactory.getPathManager();

        LocalizationContext baseContext = pm.getContext(
                LocalizationType.CAVE_STATIC, LocalizationLevel.BASE);

        // Strip any path components from comboName
        File comboFile = new File(comboName);
        comboName = comboFile.getName();

        String scriptPath = FileUtil.join(
                GfePyIncludeUtil.getUtilitiesLF(baseContext).getFile()
                        .getPath(), "CombinationsInterface.py");

        List<List<String>> combos = null;
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("comboName", comboName);
        PythonScript python = null;
        try {
            python = new PythonScript(scriptPath, PyUtil.buildJepIncludePath(
                    GfePyIncludeUtil.getCombinationsIncludePath(),
                    GfePyIncludeUtil.getCommonPythonIncludePath()),
                    CombinationsFileUtil.class.getClassLoader());
            Object com = python.execute("getCombinations", map);
            combos = (List<List<String>>) com;
        } catch (JepException e) {
            throw new GfeException("Error loading combinations file: "
                    + comboName, e);
        } finally {
            if (python != null) {
                python.dispose();
            }
        }
        return combos;
    }

    /**
     * Generates combinations files based on just running the formatter
     * 
     * @param zoneGroupList
     * @param filename
     * @throws Exception
     * @throws IOException
     */
    public static void generateAutoCombinationsFile(
            List<List<String>> zoneGroupList, String filename) throws Exception {
        IFPClient ifpc = DataManagerUIFactory.getCurrentInstance().getClient();
        SaveCombinationsFileRequest req = new SaveCombinationsFileRequest();
        req.setFileName(filename);
        req.setCombos(zoneGroupList);
        try {
            statusHandler.info("Saving combinations file: " + filename);
            ifpc.makeRequest(req);
            statusHandler.info("Successfully saved combinations file: "
                    + filename);
        } catch (Exception e) {
            statusHandler.error("Error saving combinations file: " + filename,
                    e);
            throw e;
        }
    }
}
