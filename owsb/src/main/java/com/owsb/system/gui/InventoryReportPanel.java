package com.owsb.system.gui;

import com.owsb.system.utils.FileUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventoryReportPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable reportTable;

    public InventoryReportPanel() {
        setLayout(new BorderLayout());

        // Define columns for the report table
        tableModel = new DefaultTableModel(new String[]{"Item ID", "Name", "Supplier ID", "Quantity", "Report Date"}, 0);
        reportTable = new JTable(tableModel);
        add(new JScrollPane(reportTable), BorderLayout.CENTER);

        // Load data from report file into the table
        loadReportData();
    }

    private void loadReportData() {
        tableModel.setRowCount(0);  // Clear previous data

        List<String> lines = FileUtil.readLines("inventory_report.txt"); // Use the exact report file name
        for (String line : lines) {
            if (line.isBlank()) continue;
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                tableModel.addRow(new Object[]{
                        parts[0], parts[1], parts[2], parts[3], parts[4]
                });
            }
        }
    }
}
