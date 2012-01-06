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
package com.raytheon.uf.viz.monitor.scan.tables;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.raytheon.uf.common.monitor.data.CommonTableConfig.SortDirection;
import com.raytheon.uf.common.monitor.scan.config.SCANConfig;
import com.raytheon.uf.common.monitor.scan.config.SCANConfigEnums.ScanColors;
import com.raytheon.uf.common.monitor.scan.config.SCANConfigEnums.ScanTables;
import com.raytheon.uf.viz.monitor.scan.data.ScanDataGenerator;

public abstract class SCANTable extends Composite {
    protected Table table;

    protected Composite parent;

    protected SCANTableData tableData;

    private Font tiFont;

    protected int tableIndex = -1;

    protected Font columnFont;

    private Color lineColor;

    private int defaultColWidth;

    protected boolean columnMinimumSize = false;

    protected int sortedColumnIndex = -1;

    private int previousSortedColumnIndex = -1;

    boolean reverseSort = false;

    public ScanTables scanTable;

    protected int textWidth = 0;

    protected int textHeight = 0;

    protected int imageWidth = 0;

    protected int imageHeight = 0;

    protected ITableAction tableActionCB = null;

    public SCANToolTipTextMgr toolTipMgr;

    public SCANConfig scanCfg;

    private boolean newConfigLoadedFlag = true;

    public Timer timer;

    private TimerTask timerTask;

    private Timer beepTimer;

    private TimerTask beepTimerTask;

    private int blinkColorInt = SWT.COLOR_RED;

    private Color blinkColor;

    private SCANAlarmAlertManager mgr = null;

    private final String noDataStr = "NO DETECTION";

    private Point extent;

    private String site;

    protected Point mouseMovePt = new Point(0, 0);

    protected Point mouseDownPt = new Point(0, 0);

    protected Point prevMousePt = new Point(-9999, -9999);

    public SCANTable(Composite parent, SCANTableData tableData,
            ITableAction tableActionCB, String site) {
        super(parent, 0);

        this.parent = parent;
        this.tableActionCB = tableActionCB;
        this.tableData = tableData;
        this.site = site;
        scanTable = tableData.getTableName();
        toolTipMgr = SCANToolTipTextMgr.getInstance();
        scanCfg = SCANConfig.getInstance();
        mgr = SCANAlarmAlertManager.getInstance(site);
    }

    protected void init() {
        // tiFont = new Font(parent.getDisplay(), "Courier", 9, SWT.BOLD);
        tiFont = new Font(parent.getDisplay(), "Arial", 9, SWT.NORMAL);

        columnFont = new Font(parent.getDisplay(), "Arial", 7, SWT.BOLD);

        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        GridLayout gl = new GridLayout(1, false);
        gl.verticalSpacing = 2;
        gl.marginHeight = 2;
        gl.marginWidth = 2;
        this.setLayout(gl);
        this.setLayoutData(gd);

        initData();

        createTable();

        createTableColumns();

        createTableItems();

        // packColumns();

        sortedColumnIndex = -1;

        sortTableUsingConfig();

        showHideTableColumns();

        this.addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent arg0) {
                if ((scanTable == ScanTables.CELL)
                        || (scanTable == ScanTables.DMD)) {
                    mgr.clearAlertedAlarms(site, scanTable);
                }
                tiFont.dispose();
                columnFont.dispose();
                lineColor.dispose();
            }
        });
    }

    private void initData() {
        makeImageCalculations();

        lineColor = new Color(parent.getDisplay(), 80, 80, 80);
        defaultColWidth = scanCfg.getDefaultColumnWidth(scanTable);
    }

    private void createTable() {
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        gd.heightHint = 175;
        gd.widthHint = scanCfg.getDefaultTableWidth(scanTable);

        // **********************************************************
        // **********************************************************
        // **********************************************************
        // **********************************************************

        boolean[] visCols = scanCfg.getVisibleColumns(scanTable);
        int tableWidth = 0;

        for (boolean b : visCols) {
            if (b) {
                tableWidth += defaultColWidth;
            }
        }

        System.out.println("widthHint  = " + gd.widthHint);
        System.out.println("tableWidth = " + tableWidth);

        // **********************************************************
        // **********************************************************
        // **********************************************************
        // **********************************************************
        // **********************************************************

        // table = new Table(this, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL
        // | SWT.FULL_SELECTION);
        table = new Table(this, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        table.setLayoutData(gd);
        table.setHeaderVisible(true);

        GC gc = new GC(table);
        extent = gc.stringExtent(noDataStr);
        gc.dispose();

        table.addListener(SWT.PaintItem, new Listener() {
            public void handleEvent(Event event) {

                // if (scanTable != ScanTables.CELL) {
                // // System.out.println("scanTable = " + scanTable.name());
                // // System.out.println("in paintListener");
                // }

                if (tableData.getTableRows().size() != 0) {
                    table.deselectAll();
                    event.gc.setForeground(lineColor);
                    event.gc.setLineWidth(2);
                    int currentCol = event.index;

                    Rectangle rect = ((TableItem) event.item)
                            .getBounds(currentCol);
                    event.gc.drawRectangle(rect.x - 1, rect.y - 1, rect.width,
                            rect.height);

                    // Draw an extra line on the edges of the table cell to hide
                    // the
                    // white lines
                    // dividing the columns;
                    // event.gc.setLineWidth(2);
                    event.gc.drawLine(rect.x + rect.width - 2, rect.y - 1,
                            rect.x + rect.width - 2, rect.y - 1 + rect.height);

                    event.gc.drawRectangle(rect.x - 1, rect.y - 1, rect.width,
                            rect.height);

                    if ((tableIndex >= 0)
                            && (tableIndex < table.getItemCount())) {
                        event.gc.setForeground(parent.getDisplay()
                                .getSystemColor(SWT.COLOR_BLUE));
                        event.gc.setLineWidth(3);
                        TableItem item = table.getItem(tableIndex);
                        rect = item.getBounds(currentCol);
                        event.gc.drawRectangle(rect.x - 1, rect.y - 1,
                                rect.width, rect.height);
                    }
                } else {

                    // System.out.println("scanTable = " + scanTable.name());
                    // System.out.println("event.index = " + event.index);

                    if ((event.index >= 0) || (event.index <= 6)) {
                        int offset = 0;

                        if (event.index != 0) {
                            for (int i = 0; i < event.index; i++) {
                                TableColumn column = table.getColumn(i);
                                offset += column.getWidth();
                            }
                        }

                        event.gc.setForeground(getParent().getShell()
                                .getDisplay().getSystemColor(SWT.COLOR_GREEN));
                        // event.gc.setBackground(display.getSystemColor(SWT.COLOR_BLACK));

                        int y = event.y + (event.height - extent.y) / 2;
                        event.gc.drawString(noDataStr, event.x - offset, y);
                    }
                }
            }
        });

        /*
         * Add a mouse listener to the table.
         */
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent event) {
                tableMouseDownAction(event);
            }

        });

        table.addMouseMoveListener(new MouseMoveListener() {
            @Override
            public void mouseMove(MouseEvent e) {
                tableMouseMoveAction(e);
            }
        });

        table.addMouseTrackListener(new MouseTrackAdapter() {
            @Override
            public void mouseEnter(MouseEvent e) {
                tableMouseEnterAction(e);
            }

            @Override
            public void mouseExit(MouseEvent e) {
                table.setToolTipText(null);
            }
        });
    }

    /**
     * Create the table columns.
     */
    private void createTableColumns() {
        TableColumn tc;

        String[] columns = scanCfg.getTableColumnNames(scanTable);

        for (int i = 0; i < columns.length; i++) {
            tc = new TableColumn(table, SWT.CENTER);

            tc.setResizable(false);

            tc.setData(columns[i]);
            tc.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent event) {
                    tableColumnSelectAction(event);

                }
            });

            if (columnMinimumSize == true) {
                tc.addControlListener(new ControlAdapter() {
                    @Override
                    public void controlResized(ControlEvent e) {
                        /*
                         * Prevent the user from resizing the column to be
                         * smaller than the default column size.
                         */
                        TableColumn tc = (TableColumn) e.getSource();
                        if ((tc.getWidth() < defaultColWidth)
                                && (tc.getWidth() != 0)) {
                            tc.setWidth(defaultColWidth);
                        }
                    }
                });
            }
        }

        setColumnImages();

        //
        // TODO : Rank is default so we have to sort by the default - fix this
        //
        // TableColumn stc = table.getColumn(0);

        // TODO : Will need to fix with the default ranking
        sortedColumnIndex = -1;
    }

    /**
     * Create the table items.
     */
    private void createTableItems() {
        table.removeAll();

        if (tableData == null) {
            return;
        }

        int countyIndex = -1;

        // Loop of the rows of data in the table data object.
        for (SCANTableRowData rowData : tableData.getTableRows()) {
            SCANTableCellData[] cellData = rowData.getTableCellDataArray();

            TableItem ti = new TableItem(table, SWT.NONE);
            ti.setFont(tiFont);

            for (int i = 0; i < cellData.length; i++) {
                if (cellData[i] == null) {
                    ti.setText(i, "NA");
                } else {
                    if (cellData[i].isCounty()) {
                        countyIndex = i;
                    }
                    ti.setText(i, cellData[i].displayString());
                    ti.setBackground(i, cellData[i].getBackgroundColor());
                    ti.setForeground(i, cellData[i].getForegroundColor());
                }
            }
        }

        showHideTableColumns();

        /*
         * Determine the width of the county column and then loop and calculate
         * how much of the county name can be display in the column and update
         * the column with the amount of characters to fill it up.
         */
        if (countyIndex >= 0) {
            GC gc = new GC(table);
            gc.setFont(tiFont);

            int countyColWidth = table.getColumn(countyIndex).getWidth();

            if (countyColWidth > 0) {
                String displayString = "";

                ArrayList<SCANTableRowData> rowDataArray = tableData
                        .getTableRows();
                for (int i = 0; i < rowDataArray.size(); i++) {
                    SCANTableRowData rowData = tableData.getTableRows().get(i);

                    SCANTableCellData[] cellData = rowData
                            .getTableCellDataArray();
                    displayString = cellData[countyIndex].displayString();

                    extent = gc.stringExtent(displayString);

                    while (extent.x > countyColWidth) {
                        displayString = displayString.substring(0,
                                displayString.length() - 1);
                        extent = gc.stringExtent(displayString);
                    }

                    table.getItem(i).setText(countyIndex, displayString);
                }
            }
            gc.dispose();
        }

    }

    public void sortTableColumnByIndex(int colIndex) {
        // If there is no data to sort then return.
        if (table.getItemCount() == 0) {
            return;
        }

        /*
         * Check if the column is sortable. If not then return.
         */
        if (colIndex != -1) {

            TableColumn tc = table.getColumn(colIndex);
            String sortColName = (String) tc.getData();

            int sortDir = scanCfg.getSortDirection(scanTable, sortColName);

            if (sortDir == SortDirection.None.getSortDir()) {
                return;
            }
            tc.pack();

            if (tc.getWidth() < defaultColWidth) {
                tc.setWidth(defaultColWidth);
            }

            sortedColumnIndex = table.indexOf(tc);

            reverseSort = false;

            previousSortedColumnIndex = sortedColumnIndex;
            sortTableData(sortedColumnIndex);
        } else {
            sortedColumnIndex = colIndex;
            sortTableData(colIndex);
        }
    }

    private void tableColumnSelectAction(SelectionEvent event) {
        // If there is no data to sort then return.
        if (table.getItemCount() == 0) {
            return;
        }

        /*
         * Check if the column is sortable. If not then return.
         */
        TableColumn tc = (TableColumn) event.getSource();
        String sortColName = (String) tc.getData();

        int sortDir = scanCfg.getSortDirection(scanTable, sortColName);

        if (sortDir == SortDirection.None.getSortDir()) {
            return;
        }

        tc.pack();

        if (tc.getWidth() < defaultColWidth) {
            tc.setWidth(defaultColWidth);
        }

        sortedColumnIndex = table.indexOf(tc);

        if (previousSortedColumnIndex == sortedColumnIndex) {
            reverseSort = !reverseSort;
        } else {
            reverseSort = false;
        }

        previousSortedColumnIndex = sortedColumnIndex;

        tableActionCB.sortedColumn((String) tc.getData());

        sortTableData(sortedColumnIndex);
    }

    public void setTableData(SCANTableData td) {
        clearTableSelection();
        tableData = td;

        // System.out.println("--- scanTable = " + scanTable.name());
        // System.out.println("--- setting table data in SCANTable");
        //
        // System.out.println("tableData.getTableRows().size() = "
        // + tableData.getTableRows().size());

        if ((tableData == null) || (tableData.getTableRows().size() == 0)) {
            // System.out.println("*** no data");
            // table.setEnabled(true);
            table.removeAll();

            /*
             * This TableItem is needed to draw "No Detections" on. Do not
             * remove it.
             */
            TableItem item = new TableItem(table, SWT.NONE);

            table.setBackground(getParent().getShell().getDisplay()
                    .getSystemColor(SWT.COLOR_BLACK));
            table.setEnabled(false);

        } else {
            if (newConfigLoadedFlag == true) {
                sortTableUsingConfig();
            } else {
                sortTableData(sortedColumnIndex);
            }
            table.setEnabled(true);
            table.setBackground(getParent().getShell().getDisplay()
                    .getSystemColor(SWT.COLOR_WHITE));
        }

        if (!table.isDisposed()) {
            table.redraw();
        }
    }

    public void columnVisiblityChanged(boolean[] visCols) {
        // Sorted column index is default when value is -1
        if ((sortedColumnIndex != -1) && (visCols[sortedColumnIndex] == false)) {
            for (int i = 0; i < visCols.length; i++) {
                if (visCols[i] == true) {
                    sortedColumnIndex = i;
                    break;
                }
            }
        }

        showHideTableColumns(visCols);
    }

    public void newConfigLoaded() {
        newConfigLoadedFlag = true;
    }

    /**
     * Sort the table using the configuration data.
     */
    private void sortTableUsingConfig() {
        TableColumn tc;

        if (sortedColumnIndex < 0) {
            int defRankIdx = scanCfg.getDefaultRankColumnIndex(scanTable);
            if (defRankIdx == -1) {
                sortTableData(defRankIdx);
                return;
            }
            previousSortedColumnIndex = defRankIdx;
            tc = table.getColumn(defRankIdx);
        } else {
            tc = table.getColumn(sortedColumnIndex);
        }

        tc.pack();

        if (tc.getWidth() < defaultColWidth) {
            tc.setWidth(defaultColWidth);
        }

        sortTableData(sortedColumnIndex);
        newConfigLoadedFlag = false;
    }

    private void sortTableData(int sortedIndex) {

        String ident = null;

        if (table.isDisposed() == true) {
            return;
        }

        // get the ident, if a row is outlined in blue
        if (tableIndex >= 0) {
            SCANTableRowData stdr = tableData.getTableRows().get(tableIndex);
            ident = stdr.getIdent();
        }

        if (sortedIndex != -1) {
            TableColumn tc = table.getColumn(sortedIndex);

            String sortColName = (String) tc.getData();

            int sortDir = scanCfg.getSortDirection(scanTable, sortColName);

            // Return if we do not sort this column
            if (sortDir == SortDirection.None.getSortDir()) {
                return;
            }

            if (reverseSort == true) {
                if (sortDir == SortDirection.Ascending.getSortDir()) {
                    sortDir = SortDirection.Decending.getSortDir();
                } else {
                    sortDir = SortDirection.Ascending.getSortDir();
                }
            }

            int columnIndex = scanCfg.getColumnIndex(scanTable, sortColName);

            tableData.setSortColumnAndDirection(columnIndex, sortDir);
        }

        setColumnImages();
        if (sortedIndex != -1) {
            tableData.sortData();
        } else {
            tableData.sortDefault();
        }
        createTableItems();

        // packColumns();

        showHideTableColumns();

        if (timer != null) {
            timer.cancel();
        }
        if (beepTimer != null) {
            beepTimer.cancel();
        }

        ScanDataGenerator sdg = new ScanDataGenerator(site);
        if ((scanTable == ScanTables.CELL) || (scanTable == ScanTables.DMD)
                && !mgr.getAlertedAlarms(site, scanTable).isEmpty()) {
            checkBlink(sdg);
        }

        // loop thru rows to find ident identified above and set new tableIndex
        // in order to outline same row
        int count = 0;
        ArrayList<SCANTableRowData> rowList = tableData.getTableRows();
        for (SCANTableRowData scanTableRowData : rowList) {
            if (scanTableRowData.getIdent().equals(ident)) {

                tableIndex = count;
                break;
            }
            count++;
        }

    }

    public void clearTableSelection() {
        tableIndex = -1;

        if (table.isDisposed() == false) {
            table.deselectAll();
        }
    }

    /**
     * Pack the table columns.
     */
    // private void packColumns() {
    //
    // for (int i = 0; i < table.getColumnCount(); i++) {
    //
    // System.out.println("tc width = " + table.getColumn(i).getWidth());
    //
    // if (table.getColumn(i).getWidth() > 0) {
    // packSingleColumn(table.getColumn(i), i);
    // }
    // }
    // }

    public boolean[] getVisibleColumns() {
        TableColumn[] tCols = table.getColumns();
        boolean[] visibleCols = new boolean[tCols.length];

        for (int i = 0; i < tCols.length; i++) {
            if (tCols[i].getWidth() == 0) {
                visibleCols[i] = false;
            } else {
                visibleCols[i] = true;
            }
        }

        return visibleCols;
    }

    /**
     * Show/Hide table columns.
     * 
     * @param visCols
     *            Boolean array indicating which table columns are visible.
     */
    public void showHideTableColumns(boolean[] visCols) {
        TableColumn[] tCols = table.getColumns();

        for (int i = 0; i < visCols.length; i++) {
            if (visCols[i] == false) {
                tCols[i].setWidth(0);
            } else {
                packSingleColumn(tCols[i], i);
            }
        }
    }

    private void packSingleColumn(TableColumn tc, int index) {
        tc.pack();
        // tCols[i].setWidth(table.getColumn(i).getWidth() + 5);
        tc.setWidth(table.getColumn(index).getWidth() + 2);

        System.out.println("tc.getWidth() = " + tc.getWidth());

        if (tc.getWidth() > defaultColWidth) {
            tc.setWidth(defaultColWidth);
        }
    }

    /**
     * Show and hide the columns based on the configuration data.
     */
    public void showHideTableColumns() {
        boolean[] visCols = scanCfg.getVisibleColumns(scanTable);

        showHideTableColumns(visCols);
    }

    private void makeImageCalculations() {
        Image image = new Image(this.getDisplay(), 100, 100);
        GC gc = new GC(image);
        gc.setFont(columnFont);

        int maxTextLength = -1;

        int maxColNameExtent = 0;

        // TODO - use font extents

        textWidth = gc.getFontMetrics().getAverageCharWidth();
        textHeight = gc.getFontMetrics().getHeight();

        String[] colNames = scanCfg.getTableColumnNames(scanTable);

        for (int i = 0; i < colNames.length; i++) {
            if (colNames[i].length() > maxTextLength) {
                maxTextLength = colNames[i].length();
            }

            // System.out.println("--- column name = " + colNames[i]);
            // System.out.println("--- extent      = " +
            // gc.stringExtent(colNames[i]));

            if (gc.stringExtent(colNames[i]).x > maxColNameExtent) {
                maxColNameExtent = gc.stringExtent(colNames[i]).x;
            }
        }

        // System.out.println("maxColNameExtent = " + maxColNameExtent + "  -  "
        // + scanTable.name());

        // imageWidth = maxTextLength * textWidth + 6;
        // imageWidth = maxColNameExtent + 2;
        imageWidth = maxColNameExtent;
        imageHeight = textHeight + 4;

        gc.dispose();
        image.dispose();
    }

    public void updateColumnTips() {
        if (scanCfg.showTips(scanTable) == false) {
            TableColumn[] tCols = table.getColumns();

            for (int i = 0; i < tCols.length; i++) {
                tCols[i].setToolTipText(null);
            }
        } else {
            TableColumn[] tCols = table.getColumns();

            for (int i = 0; i < tCols.length; i++) {
                tCols[i].setToolTipText(toolTipMgr.getTableColumnTip(scanTable,
                        (String) tCols[i].getData()));
            }
        }

        // TODO - need the column tool tip text to be set
    }

    /**
     * Get all of the idents in the table data.
     * 
     * @return String array
     */
    public String[] getIdentList() {
        String[] idents = new String[tableData.getTableRows().size()];

        ArrayList<SCANTableRowData> trd = tableData.getTableRows();

        for (int i = 0; i < trd.size(); i++) {
            idents[i] = trd.get(i).getTableCellData(0).getCellText();
        }

        return idents;
    }

    public void updateTableColumnImages() {
        setColumnImages();
    }

    public void updateThresholds(String colName) {
        int index = scanCfg.getColumnIndex(scanTable, colName);
        ArrayList<SCANTableRowData> rowData = tableData.getTableRows();
        for (int i = 0; i < rowData.size(); i++) {
            rowData.get(i).getTableCellData(index).setColor();
        }
        if ((scanTable == ScanTables.CELL) || (scanTable == ScanTables.DMD)) {
            mgr.clearToReset(site, scanTable);
        }
        sortTableUsingConfig();
    }

    public void updateThresholds(int row, int col) {
        ArrayList<SCANTableRowData> rowData = tableData.getTableRows();
        rowData.get(row).getTableCellData(col).setColor();
        table.getItem(row).setBackground(col,
                rowData.get(row).getTableCellData(col).getBackgroundColor());
    }

    /**
     * Set the dependent attribute's background color based on the independent
     * attribute's background color. Also set the cell text in the independent
     * attribute.
     * 
     * @param colNameInd
     * @param colNameDep
     */
    public void updateThresholds(String colNameInd, String colNameDep) {
        int indexInd = scanCfg.getColumnIndex(scanTable, colNameInd);
        int indexDep = scanCfg.getColumnIndex(scanTable, colNameDep);
        final Color upperColor = new Color(Display.getDefault(), 187, 34, 34);
        Color indColColor = null;

        ArrayList<SCANTableRowData> rowData = tableData.getTableRows();
        for (int i = 0; i < rowData.size(); i++) {
            indColColor = rowData.get(i).getTableCellData(indexInd)
                    .getBackgroundColor();
            rowData.get(i).getTableCellData(indexDep)
                    .setBackgroundColor(indColColor);

            if (indColColor.equals(upperColor)) {
                rowData.get(i).getTableCellData(indexDep).setCellText("MESO");
            } else {
                rowData.get(i).getTableCellData(indexDep).setCellText("circ");
            }
        }

        sortTableUsingConfig();
    }

    /**
     * Changes the color of the cells on the fly, also determines whether to
     * beep or not on the fly
     */
    private void blinkCells() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (beepTimer != null) {
            beepTimer.cancel();
            beepTimer = null;
        }

        if (tableData.getTableRows().size() > 0) {
            timer = getBlinkTimer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    Display.getDefault().asyncExec(new Runnable() {
                        public void run() {

                            // Fail-safe check to determine if the we have
                            // no
                            // data
                            // in the table data.
                            if (timer != null && tableData != null) {

                                if (tableData.getTableRows().size() == 0) {
                                    if (timer != null) {
                                        timer.cancel();
                                        timer.purge();
                                    }
                                    return;
                                }

                                boolean[][] indices = mgr.getIndices(site,
                                        scanTable);
                                if (table.isDisposed()) {
                                    timer.cancel();
                                    timer.purge();
                                } else {
                                    setBlinkColor();
                                    boolean allClear = true;
                                    // for (int i = 0; i < table.getItemCount();
                                    // i++) {
                                    for (int i = 0; i < tableData
                                            .getNumberOfDataRows(); i++) {
                                        TableItem ti = table.getItem(i);
                                        if (ti == null) {
                                            continue;
                                        }

                                        if (i < indices.length) {
                                            for (int j = 0; j < indices[i].length; j++) {

                                                if (indices[i][j] == true) {
                                                    allClear = false;
                                                    ti.setBackground(j,
                                                            blinkColor);

                                                    // handle the beep while
                                                    // looking at
                                                    // all the cells
                                                    if (SCANConfig
                                                            .getInstance()
                                                            .getAlarmBell(
                                                                    scanTable)) {
                                                        mgr.setRing(true);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    List<Point> points = mgr.getPointsToReset(
                                            site, scanTable);
                                    for (int i = 0; i < points.size(); i++) {
                                        updateThresholds(points.get(i).x,
                                                points.get(i).y);
                                    }
                                    points.clear();
                                    // checks if there are no more alarms
                                    // and then
                                    // will cancel the timer
                                    if (allClear == true) {
                                        timer.cancel();
                                        timer.purge();
                                    }
                                }
                            }
                        }
                    });
                }
            };
            timer.schedule(timerTask, 0, 1000);

            beepTimer = new Timer();
            beepTimerTask = new TimerTask() {
                @Override
                public void run() {
                    Display.getDefault().asyncExec(new Runnable() {
                        public void run() {

                            // Fail-safe check to determine if the we have no
                            // data
                            // in the table data.
                            if (beepTimer != null && tableData != null) {

                                if (tableData.getTableRows().size() == 0) {
                                    beepTimer.cancel();
                                    beepTimer.purge();
                                    return;
                                }

                                if (table.isDisposed()) {
                                    beepTimer.cancel();
                                    beepTimer.purge();
                                }
                                if (mgr.isRing()) {
                                    Toolkit.getDefaultToolkit().beep();
                                } else {
                                    beepTimer.cancel();
                                    beepTimer.purge();
                                }
                            }
                        }
                    });
                }
            };
            beepTimer.schedule(beepTimerTask, 0, 1500);
        }
    }

    public Timer getBlinkTimer() {
        if (timer == null) {
            timer = new Timer();
        }
        return timer;
    }

    public void checkBlink(ScanDataGenerator sdg) {
        if (!scanCfg.getAlarmsDisabled(scanTable)) {
            mgr.calculateScanCells(tableData, scanTable, sdg);
            blinkCells();
        }
    }

    public void setBlinkColor() {
        if (blinkColorInt == SWT.COLOR_YELLOW) {
            blinkColorInt = SWT.COLOR_RED;
        } else {
            blinkColorInt = SWT.COLOR_YELLOW;
        }
        blinkColor = Display.getDefault().getSystemColor(blinkColorInt);
    }

    public void redrawTable() {
        table.redraw();
    }

    /**
     * This method will create the images displayed in the table columns. This
     * method can be overridden to draw the table column differently (CELL and
     * DMD do this).
     */
    protected void setColumnImages() {
        TableColumn[] tCols = table.getColumns();

        for (int i = 0; i < tCols.length; i++) {
            String colName = (String) tCols[i].getData();
            Image img = new Image(this.getDisplay(), imageWidth, imageHeight);

            GC gc = new GC(img);
            gc.setFont(columnFont);
            gc.setAntialias(SWT.ON);

            // Set the initial foreground and background colors.
            gc.setForeground(this.getDisplay().getSystemColor(SWT.COLOR_WHITE));
            gc.setBackground(this.getDisplay().getSystemColor(SWT.COLOR_BLACK));

            // Set the background color to the sort color if that column is
            // sorted.
            if (sortedColumnIndex == -1) {
                scanCfg.getDefaultName();
                String sortColName = scanCfg.getDefaultRank(this.scanTable);
                int colIndex = scanCfg.getColumnIndex(scanTable, sortColName);
                sortedColumnIndex = colIndex;
            }
            if (table.indexOf(tCols[i]) == sortedColumnIndex) {
                gc.setBackground(scanCfg.getScanColor(ScanColors.Sort));
            }

            gc.fillRectangle(0, 0, imageWidth, imageHeight);

            int xCoord = (imageWidth / 2) - (colName.length() * textWidth / 2);

            gc.drawText(colName, xCoord, 3, true);

            gc.dispose();
            tCols[i].setImage(img);

            img.dispose();
        }
    }

    /**
     * When the mouse enters the cell on the table this method is call to
     * determine how to handle the action.
     * 
     * @param event
     */
    protected void tableMouseEnterAction(MouseEvent event) {
        mouseMovePt.x = event.x;
        mouseMovePt.y = event.y;

        TableItem item = table.getItem(mouseMovePt);

        if (item == null) {
            table.setToolTipText(null);
            return;
        }

        Rectangle rect;
        // rect = item.getBounds(table.getColumnCount() - 1);
        rect = item.getBounds(scanCfg.getCountyColumnIndex(scanTable));

        if ((scanCfg.showTips(scanTable) == false)
                && (rect.contains(mouseMovePt) == false)) {
            prevMousePt.x = -9999;
            prevMousePt.y = -9999;
            table.setToolTipText(null);
            return;
        }

        for (int i = 0; i < table.getColumnCount(); i++) {
            rect = item.getBounds(i);

            if (rect.contains(mouseMovePt) && rect.contains(prevMousePt)) {
                prevMousePt.x = -9999;
                prevMousePt.y = -9999;
                return;
            }

            if (rect.contains(mouseMovePt)) {
                prevMousePt.x = mouseMovePt.x;
                prevMousePt.y = mouseMovePt.y;

                String toolTip = null;

                if (((String) table.getColumn(i).getData()).equals("county")) {
                    toolTip = tableData.getTableRows().get(table.indexOf(item))
                            .getTableCellData(i).getCellText();
                }

                table.setToolTipText(toolTip);
                return;
            }
        }

        prevMousePt.x = -9999;
        prevMousePt.y = -9999;
        table.setToolTipText(null);
    }

    /**
     * Handle the mouse hover on the table.
     * 
     * @param event
     *            Mouse event.
     */
    protected void tableMouseMoveAction(MouseEvent event) {
        mouseMovePt.x = event.x;
        mouseMovePt.y = event.y;

        TableItem item = table.getItem(mouseMovePt);

        if (item == null) {
            table.setToolTipText(null);
            return;
        }

        Rectangle rect;
        // rect = item.getBounds(table.getColumnCount() - 1);
        rect = item.getBounds(scanCfg.getCountyColumnIndex(scanTable));

        if ((scanCfg.showTips(scanTable) == false)
                && (rect.contains(mouseMovePt) == false)) {
            prevMousePt.x = -9999;
            prevMousePt.y = -9999;
            table.setToolTipText(null);
            return;
        }

        for (int i = 0; i < table.getColumnCount(); i++) {
            rect = item.getBounds(i);

            if (rect.contains(mouseMovePt) && rect.contains(prevMousePt)) {
                return;
            }

            if (rect.contains(mouseMovePt)) {
                prevMousePt.x = mouseMovePt.x;
                prevMousePt.y = mouseMovePt.y;

                String toolTip = null;

                if (((String) table.getColumn(i).getData()).equals("county")) {
                    toolTip = tableData.getTableRows().get(table.indexOf(item))
                            .getTableCellData(i).getCellText();
                }

                table.setToolTipText(toolTip);
                return;
            }
        }

        prevMousePt.x = -9999;
        prevMousePt.y = -9999;
        table.setToolTipText(null);
    }

    /**
     * Handle the mouse button down on the table.
     * 
     * @param event
     *            Mouse event.
     */
    protected abstract void tableMouseDownAction(MouseEvent event);

}
