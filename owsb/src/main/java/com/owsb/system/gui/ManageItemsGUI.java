package com.owsb.system.gui;

import com.owsb.system.utils.FileUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ManageItemsGUI extends JPanel {

    private DefaultTableModel tableModel;
    private JTable itemTable;

    public ManageItemsGUI() {
        setLayout(new BorderLayout());

        // Setup table columns
        tableModel = new DefaultTableModel(new String[]{"Item ID", "Name", "Supplier ID", "Description", "Price", "Stock", "Reorder Level", "Category", "Discontinued"}, 0);
        itemTable = new JTable(tableModel);
        add(new JScrollPane(itemTable), BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel();

        JButton addBtn = new JButton("Add Item");
        JButton editBtn = new JButton("Edit Item");
        JButton deleteBtn = new JButton("Delete Item");
        JButton refreshBtn = new JButton("Refresh");
        JButton backBtn = new JButton("Back to Sales Manager");

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load items from file
        loadItems();

        // Button actions
        addBtn.addActionListener(e -> addItem());
        editBtn.addActionListener(e -> editItem());
        deleteBtn.addActionListener(e -> deleteItem());
        refreshBtn.addActionListener(e -> loadItems());
        backBtn.addActionListener(e -> {
            // Close the parent window (assumes this panel is inside a JFrame)
            SwingUtilities.getWindowAncestor(this).dispose();
        });
    }

    private void loadItems() {
        tableModel.setRowCount(0); // clear existing rows
        List<String> lines = FileUtil.readLines("items.txt");
        for (String line : lines) {
            if (line.isBlank()) continue;
            String[] parts = line.split(",");
            if (parts.length >= 9) {
                // parse and add row
                tableModel.addRow(new Object[]{
                        parts[0], parts[1], parts[2], parts[3],
                        parts[4], parts[5], parts[6], parts[7], parts[8]
                });
            }
        }
    }

    private void addItem() {
        JTextField itemIdField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField supplierIdField = new JTextField();
        JTextField descField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField stockField = new JTextField();
        JTextField reorderLevelField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField discontinuedField = new JTextField();

        Object[] inputs = {
                "Item ID:", itemIdField,
                "Name:", nameField,
                "Supplier ID:", supplierIdField,
                "Description:", descField,
                "Price:", priceField,
                "Stock:", stockField,
                "Reorder Level:", reorderLevelField,
                "Category:", categoryField,
                "Discontinued (true/false):", discontinuedField
        };

        int result = JOptionPane.showConfirmDialog(this, inputs, "Add New Item", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String itemId = itemIdField.getText().trim();
            String name = nameField.getText().trim();
            String supplierId = supplierIdField.getText().trim();
            String desc = descField.getText().trim();
            String price = priceField.getText().trim();
            String stock = stockField.getText().trim();
            String reorderLevel = reorderLevelField.getText().trim();
            String category = categoryField.getText().trim();
            String discontinued = discontinuedField.getText().trim();

            if (itemId.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Item ID and Name are required!");
                return;
            }

            String newItem = String.join(",", itemId, name, supplierId, desc, price, stock, reorderLevel, category, discontinued);
            FileUtil.appendLine("items.txt", newItem);
            loadItems();
        }
    }

    private void editItem() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select an item to edit.");
            return;
        }

        String oldItemId = (String) tableModel.getValueAt(selectedRow, 0);
        String oldName = (String) tableModel.getValueAt(selectedRow, 1);
        String oldSupplierId = (String) tableModel.getValueAt(selectedRow, 2);
        String oldDesc = (String) tableModel.getValueAt(selectedRow, 3);
        String oldPrice = (String) tableModel.getValueAt(selectedRow, 4);
        String oldStock = (String) tableModel.getValueAt(selectedRow, 5);
        String oldReorder = (String) tableModel.getValueAt(selectedRow, 6);
        String oldCategory = (String) tableModel.getValueAt(selectedRow, 7);
        String oldDiscontinued = (String) tableModel.getValueAt(selectedRow, 8);

        JTextField itemIdField = new JTextField(oldItemId);
        JTextField nameField = new JTextField(oldName);
        JTextField supplierIdField = new JTextField(oldSupplierId);
        JTextField descField = new JTextField(oldDesc);
        JTextField priceField = new JTextField(oldPrice);
        JTextField stockField = new JTextField(oldStock);
        JTextField reorderLevelField = new JTextField(oldReorder);
        JTextField categoryField = new JTextField(oldCategory);
        JTextField discontinuedField = new JTextField(oldDiscontinued);

        Object[] inputs = {
                "Item ID:", itemIdField,
                "Name:", nameField,
                "Supplier ID:", supplierIdField,
                "Description:", descField,
                "Price:", priceField,
                "Stock:", stockField,
                "Reorder Level:", reorderLevelField,
                "Category:", categoryField,
                "Discontinued (true/false):", discontinuedField
        };

        int result = JOptionPane.showConfirmDialog(this, inputs, "Edit Item", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String newItemId = itemIdField.getText().trim();
            String newName = nameField.getText().trim();
            String newSupplierId = supplierIdField.getText().trim();
            String newDesc = descField.getText().trim();
            String newPrice = priceField.getText().trim();
            String newStock = stockField.getText().trim();
            String newReorderLevel = reorderLevelField.getText().trim();
            String newCategory = categoryField.getText().trim();
            String newDiscontinued = discontinuedField.getText().trim();

            if (newItemId.isEmpty() || newName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Item ID and Name are required!");
                return;
            }

            updateItemInFile(oldItemId, newItemId, newName, newSupplierId, newDesc, newPrice, newStock, newReorderLevel, newCategory, newDiscontinued);
            loadItems();
        }
    }

    private void updateItemInFile(String oldItemId, String newItemId, String newName, String newSupplierId, String newDesc,
                                  String newPrice, String newStock, String newReorderLevel, String newCategory, String newDiscontinued) {
        List<String> lines = FileUtil.readLines("items.txt");
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 9) {
                String itemId = parts[0];
                if (itemId.equals(oldItemId)) {
                    updatedLines.add(String.join(",", newItemId, newName, newSupplierId, newDesc, newPrice, newStock, newReorderLevel, newCategory, newDiscontinued));
                } else {
                    updatedLines.add(line);
                }
            }
        }

        try {
            Path path = Path.of("src/items.txt");
            Files.write(path, updatedLines);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating items.txt");
            e.printStackTrace();
        }
    }

    private void deleteItem() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select an item to delete.");
            return;
        }

        String itemIdToDelete = (String) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Delete item: " + itemIdToDelete + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            List<String> lines = FileUtil.readLines("items.txt");
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length >= 9) {
                    String itemId = parts[0];
                    if (!itemId.equals(itemIdToDelete)) {
                        updatedLines.add(line);
                    }
                }
            }

            try {
                Path path = Path.of("src/items.txt");
                Files.write(path, updatedLines);
                loadItems();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error updating items.txt");
                e.printStackTrace();
            }
        }
    }
}
