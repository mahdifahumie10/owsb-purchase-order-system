package com.owsb.system.gui;

import com.owsb.system.utils.FileUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PurchaseOrderPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable poTable;

    public PurchaseOrderPanel() {
        setLayout(new BorderLayout());

        // Columns based on po.txt file format
        String[] columns = {
                "PO ID", "PR ID", "Item ID", "Item Name", "Quantity",
                "Supplier ID", "Purchase Manager ID", "Approval Status", "Delivery Status", "Delivery Date"
        };
        tableModel = new DefaultTableModel(columns, 0);
        poTable = new JTable(tableModel);

        add(new JScrollPane(poTable), BorderLayout.CENTER);

        loadPurchaseOrders();
    }

    private void loadPurchaseOrders() {
        tableModel.setRowCount(0); // Clear previous rows
        List<String> lines = FileUtil.readLines("po.txt");
        for (String line : lines) {
            if (line.isBlank()) continue;

            String[] parts = line.split(",");
            if (parts.length >= 10) {
                tableModel.addRow(new Object[]{
                        parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7], parts[8], parts[9]
                });
            }
        }
    }
}
