package com.owsb.system.gui;

import com.owsb.system.utils.FileUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PurchaseReportsGUI extends JPanel {

    private DefaultTableModel tableModel;
    private JTable reportTable;

    public PurchaseReportsGUI() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Report Type", "Details"}, 0);
        reportTable = new JTable(tableModel);

        add(new JScrollPane(reportTable), BorderLayout.CENTER);

        loadReports();
    }

    private void loadReports() {
        tableModel.setRowCount(0);

        // Example: Load all purchase orders as simple report rows
        List<String> poLines = FileUtil.readLines("po.txt");
        for (String line : poLines) {
            if (line.isBlank()) continue;
            tableModel.addRow(new Object[]{"Purchase Order", line});
        }

        // You can expand this to load other reports, summarize etc.
    }
}
