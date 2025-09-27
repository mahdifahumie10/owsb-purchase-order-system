package com.owsb.system.gui;

import com.owsb.system.utils.FileUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SalesReportsGUI extends JPanel {

    private DefaultTableModel tableModel;
    private JTable salesTable;

    public SalesReportsGUI() {
        setLayout(new BorderLayout());

        String[] columns = {"Item ID", "Quantity Sold", "Sale Date"};
        tableModel = new DefaultTableModel(columns, 0);
        salesTable = new JTable(tableModel);
        add(new JScrollPane(salesTable), BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Refresh");
        add(refreshBtn, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> loadSales());

        loadSales();
    }

    private void loadSales() {
        tableModel.setRowCount(0);
        List<String> lines = FileUtil.readLines("sales.txt");
        for (String line : lines) {
            if (line.isBlank()) continue;
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                tableModel.addRow(new Object[]{parts[0], parts[1], parts[2]});
            }
        }
    }
}
