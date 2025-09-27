package com.owsb.system.gui;

import com.owsb.system.utils.FileUtil;

import javax.swing.*;
import java.awt.*;

public class PurchaseRequisitionPanel extends JPanel {

    private String userID; // To track who created the PR

    private JTextField itemIdField;
    private JTextField quantityField;
    private JTextField requiredDateField;
    private JButton saveBtn;
    private JButton cancelBtn;

    public PurchaseRequisitionPanel(String userID) {
        this.userID = userID;

        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Item ID:"));
        itemIdField = new JTextField();
        add(itemIdField);

        add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        add(quantityField);

        add(new JLabel("Required Date (YYYY-MM-DD):"));
        requiredDateField = new JTextField();
        add(requiredDateField);

        saveBtn = new JButton("Save");
        cancelBtn = new JButton("Cancel");
        add(saveBtn);
        add(cancelBtn);

        saveBtn.addActionListener(e -> savePurchaseRequisition());
        cancelBtn.addActionListener(e -> clearForm());
    }

    private void savePurchaseRequisition() {
        String itemId = itemIdField.getText().trim();
        String quantity = quantityField.getText().trim();
        String requiredDate = requiredDateField.getText().trim();

        if (itemId.isEmpty() || quantity.isEmpty() || requiredDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        // Create PR ID (could be improved with unique ID logic)
        String prId = "PR" + System.currentTimeMillis();

        // Format: prID,itemID,quantity,requiredDate,createdBy
        String line = String.join(",", prId, itemId, quantity, requiredDate, userID);

        FileUtil.appendLine("pr.txt", line);

        JOptionPane.showMessageDialog(this, "Purchase Requisition saved!");

        clearForm();
    }

    private void clearForm() {
        itemIdField.setText("");
        quantityField.setText("");
        requiredDateField.setText("");
    }
}
