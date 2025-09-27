package com.owsb.system.gui;

import com.owsb.system.utils.FileUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ManageSuppliersGUI extends JPanel {  // Changed JFrame -> JPanel

    private DefaultTableModel tableModel;
    private JTable supplierTable;

    public ManageSuppliersGUI() {
        setLayout(new BorderLayout());  // Keep your layout

        // Setup table columns
        tableModel = new DefaultTableModel(new String[]{"Supplier ID", "Name", "Phone", "Email"}, 0);
        supplierTable = new JTable(tableModel);
        add(new JScrollPane(supplierTable), BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel();

        JButton addBtn = new JButton("Add Supplier");
        JButton editBtn = new JButton("Edit Supplier");
        JButton deleteBtn = new JButton("Delete Supplier");
        JButton refreshBtn = new JButton("Refresh");
        JButton backBtn = new JButton("Back to Sales Manager");

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load suppliers from file
        loadSuppliers();

        // Button actions
        addBtn.addActionListener(e -> addSupplier());
        editBtn.addActionListener(e -> editSupplier());
        deleteBtn.addActionListener(e -> deleteSupplier());
        refreshBtn.addActionListener(e -> loadSuppliers());
        backBtn.addActionListener(e -> SwingUtilities.getWindowAncestor(this).dispose());
    }

    private void loadSuppliers() {
        tableModel.setRowCount(0); // clear existing rows
        List<String> lines = FileUtil.readLines("suppliers.txt");
        for (String line : lines) {
            if (line.isBlank()) continue;
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                tableModel.addRow(new Object[]{
                        parts[0], parts[1], parts[2], parts[3]
                });
            }
        }
    }

    private void addSupplier() {
        JTextField supplierIdField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();

        Object[] inputs = {
                "Supplier ID:", supplierIdField,
                "Name:", nameField,
                "Phone:", phoneField,
                "Email:", emailField
        };

        int result = JOptionPane.showConfirmDialog(this, inputs, "Add New Supplier", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String supplierId = supplierIdField.getText().trim();
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();

            if (supplierId.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Supplier ID and Name are required!");
                return;
            }

            String newSupplier = String.join(",", supplierId, name, phone, email);
            FileUtil.appendLine("suppliers.txt", newSupplier);
            loadSuppliers();
        }
    }

    private void editSupplier() {
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select a supplier to edit.");
            return;
        }

        String oldSupplierId = (String) tableModel.getValueAt(selectedRow, 0);
        String oldName = (String) tableModel.getValueAt(selectedRow, 1);
        String oldPhone = (String) tableModel.getValueAt(selectedRow, 2);
        String oldEmail = (String) tableModel.getValueAt(selectedRow, 3);

        JTextField supplierIdField = new JTextField(oldSupplierId);
        JTextField nameField = new JTextField(oldName);
        JTextField phoneField = new JTextField(oldPhone);
        JTextField emailField = new JTextField(oldEmail);

        Object[] inputs = {
                "Supplier ID:", supplierIdField,
                "Name:", nameField,
                "Phone:", phoneField,
                "Email:", emailField
        };

        int result = JOptionPane.showConfirmDialog(this, inputs, "Edit Supplier", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String newSupplierId = supplierIdField.getText().trim();
            String newName = nameField.getText().trim();
            String newPhone = phoneField.getText().trim();
            String newEmail = emailField.getText().trim();

            if (newSupplierId.isEmpty() || newName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Supplier ID and Name are required!");
                return;
            }

            updateSupplierInFile(oldSupplierId, newSupplierId, newName, newPhone, newEmail);
            loadSuppliers();
        }
    }

    private void updateSupplierInFile(String oldSupplierId, String newSupplierId, String newName, String newPhone, String newEmail) {
        List<String> lines = FileUtil.readLines("suppliers.txt");
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                String supplierId = parts[0];
                if (supplierId.equals(oldSupplierId)) {
                    updatedLines.add(String.join(",", newSupplierId, newName, newPhone, newEmail));
                } else {
                    updatedLines.add(line);
                }
            }
        }

        try {
            Path path = Path.of("src/suppliers.txt");
            Files.write(path, updatedLines);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating suppliers.txt");
            e.printStackTrace();
        }
    }

    private void deleteSupplier() {
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select a supplier to delete.");
            return;
        }

        String supplierIdToDelete = (String) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Delete supplier: " + supplierIdToDelete + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            List<String> lines = FileUtil.readLines("suppliers.txt");
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String supplierId = parts[0];
                    if (!supplierId.equals(supplierIdToDelete)) {
                        updatedLines.add(line);
                    }
                }
            }

            try {
                Path path = Path.of("src/suppliers.txt");
                Files.write(path, updatedLines);
                loadSuppliers();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error updating suppliers.txt");
                e.printStackTrace();
            }
        }
    }
}
