package com.owsb.system.gui;

import com.owsb.system.utils.FileUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ManageInventoryPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable inventoryTable;

    public ManageInventoryPanel() {
        setLayout(new BorderLayout());

        // Setup table columns for inventory data
        tableModel = new DefaultTableModel(new String[]{"Item ID", "Name", "Supplier ID", "Quantity", "Location"}, 0);
        inventoryTable = new JTable(tableModel);
        add(new JScrollPane(inventoryTable), BorderLayout.CENTER);

        // Buttons panel for CRUD operations and refresh
        JPanel buttonPanel = new JPanel();

        JButton addBtn = new JButton("Add Inventory");
        JButton editBtn = new JButton("Edit Inventory");
        JButton deleteBtn = new JButton("Delete Inventory");
        JButton refreshBtn = new JButton("Refresh");
        JButton backBtn = new JButton("Back");

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // Initial load of inventory from file
        loadInventory();

        // Button actions
        addBtn.addActionListener(e -> addInventory());
        editBtn.addActionListener(e -> editInventory());
        deleteBtn.addActionListener(e -> deleteInventory());
        refreshBtn.addActionListener(e -> loadInventory());
        backBtn.addActionListener(e -> {
            // Close the frame/window that contains this panel
            SwingUtilities.getWindowAncestor(this).dispose();
        });
    }

    private void loadInventory() {
        tableModel.setRowCount(0); // Clear current rows
        List<String> lines = FileUtil.readLines("inventory.txt");
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

    private void addInventory() {
        JTextField itemIdField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField supplierIdField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField locationField = new JTextField();

        Object[] inputs = {
                "Item ID:", itemIdField,
                "Name:", nameField,
                "Supplier ID:", supplierIdField,
                "Quantity:", quantityField,
                "Location:", locationField
        };

        int result = JOptionPane.showConfirmDialog(this, inputs, "Add New Inventory", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String itemId = itemIdField.getText().trim();
            String name = nameField.getText().trim();
            String supplierId = supplierIdField.getText().trim();
            String quantity = quantityField.getText().trim();
            String location = locationField.getText().trim();

            if (itemId.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Item ID and Name are required!");
                return;
            }

            String newInventory = String.join(",", itemId, name, supplierId, quantity, location);
            FileUtil.appendLine("inventory.txt", newInventory);
            loadInventory();
        }
    }

    private void editInventory() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select an inventory item to edit.");
            return;
        }

        String oldItemId = (String) tableModel.getValueAt(selectedRow, 0);
        String oldName = (String) tableModel.getValueAt(selectedRow, 1);
        String oldSupplierId = (String) tableModel.getValueAt(selectedRow, 2);
        String oldQuantity = (String) tableModel.getValueAt(selectedRow, 3);
        String oldLocation = (String) tableModel.getValueAt(selectedRow, 4);

        JTextField itemIdField = new JTextField(oldItemId);
        JTextField nameField = new JTextField(oldName);
        JTextField supplierIdField = new JTextField(oldSupplierId);
        JTextField quantityField = new JTextField(oldQuantity);
        JTextField locationField = new JTextField(oldLocation);

        Object[] inputs = {
                "Item ID:", itemIdField,
                "Name:", nameField,
                "Supplier ID:", supplierIdField,
                "Quantity:", quantityField,
                "Location:", locationField
        };

        int result = JOptionPane.showConfirmDialog(this, inputs, "Edit Inventory", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String newItemId = itemIdField.getText().trim();
            String newName = nameField.getText().trim();
            String newSupplierId = supplierIdField.getText().trim();
            String newQuantity = quantityField.getText().trim();
            String newLocation = locationField.getText().trim();

            if (newItemId.isEmpty() || newName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Item ID and Name are required!");
                return;
            }

            updateInventoryInFile(oldItemId, newItemId, newName, newSupplierId, newQuantity, newLocation);
            loadInventory();
        }
    }

    private void updateInventoryInFile(String oldItemId, String newItemId, String newName, String newSupplierId, String newQuantity, String newLocation) {
        List<String> lines = FileUtil.readLines("inventory.txt");
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                String itemId = parts[0];
                if (itemId.equals(oldItemId)) {
                    updatedLines.add(String.join(",", newItemId, newName, newSupplierId, newQuantity, newLocation));
                } else {
                    updatedLines.add(line);
                }
            }
        }

        try {
            Path path = Path.of("src/inventory.txt");
            Files.write(path, updatedLines);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating inventory.txt");
            e.printStackTrace();
        }
    }

    private void deleteInventory() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select an inventory item to delete.");
            return;
        }

        String itemIdToDelete = (String) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Delete inventory item: " + itemIdToDelete + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            List<String> lines = FileUtil.readLines("inventory.txt");
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String itemId = parts[0];
                    if (!itemId.equals(itemIdToDelete)) {
                        updatedLines.add(line);
                    }
                }
            }

            try {
                Path path = Path.of("src/inventory.txt");
                Files.write(path, updatedLines);
                loadInventory();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error updating inventory.txt");
                e.printStackTrace();
            }
        }
    }
}
