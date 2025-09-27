package com.owsb.system.gui;

import com.owsb.system.utils.FileUtil;

import javax.swing.*;
import java.awt.*;

public class SalesEntryPanel extends JPanel {

    private final JTextField itemIDField;
    private final JTextField quantityField;
    private final JTextField dateField;
    private final JButton saveBtn;
    private final JButton cancelBtn;

    public SalesEntryPanel() {
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Item ID:"));
        itemIDField = new JTextField();
        add(itemIDField);

        add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        add(quantityField);

        add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        add(dateField);

        saveBtn = new JButton("Save");
        cancelBtn = new JButton("Cancel");
        add(saveBtn);
        add(cancelBtn);

        saveBtn.addActionListener(e -> saveSale());
        cancelBtn.addActionListener(e -> clearFields());
    }

    private void saveSale() {
        String itemID = itemIDField.getText().trim();
        String quantityStr = quantityField.getText().trim();
        String saleDate = dateField.getText().trim();

        if (itemID.isEmpty() || quantityStr.isEmpty() || saleDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity must be a number!");
            return;
        }

        String saleRecord = itemID + "," + quantity + "," + saleDate;
        FileUtil.appendLine("sales.txt", saleRecord);
        JOptionPane.showMessageDialog(this, "Sale recorded successfully!");

        clearFields();
    }

    private void clearFields() {
        itemIDField.setText("");
        quantityField.setText("");
        dateField.setText("");
    }
}
