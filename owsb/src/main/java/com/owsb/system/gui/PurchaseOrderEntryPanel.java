package com.owsb.system.gui;

import com.owsb.system.utils.FileUtil;

import javax.swing.*;
import java.awt.*;

public class PurchaseOrderEntryPanel extends JPanel {

    private JTextField poIdField;
    private JTextField prIdField;
    private JTextField itemIdField;
    private JTextField itemNameField;
    private JTextField quantityField;
    private JTextField supplierIdField;
    private JTextField purchaseManagerIdField;
    private JTextField approvalStatusField;
    private JTextField deliveryStatusField;
    private JTextField deliveryDateField;

    private JButton saveBtn;
    private JButton cancelBtn;

    public PurchaseOrderEntryPanel() {
        setLayout(new GridLayout(11, 2, 10, 10));

        add(new JLabel("PO ID:"));
        poIdField = new JTextField(generatePOID());
        poIdField.setEditable(false); // auto-generated, no manual input
        add(poIdField);

        add(new JLabel("PR ID:"));
        prIdField = new JTextField();
        add(prIdField);

        add(new JLabel("Item ID:"));
        itemIdField = new JTextField();
        add(itemIdField);

        add(new JLabel("Item Name:"));
        itemNameField = new JTextField();
        add(itemNameField);

        add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        add(quantityField);

        add(new JLabel("Supplier ID:"));
        supplierIdField = new JTextField();
        add(supplierIdField);

        add(new JLabel("Purchase Manager ID:"));
        purchaseManagerIdField = new JTextField();
        add(purchaseManagerIdField);

        add(new JLabel("Approval Status:"));
        approvalStatusField = new JTextField("Pending");
        add(approvalStatusField);

        add(new JLabel("Delivery Status:"));
        deliveryStatusField = new JTextField("Not Delivered");
        add(deliveryStatusField);

        add(new JLabel("Delivery Date (YYYY-MM-DD):"));
        deliveryDateField = new JTextField();
        add(deliveryDateField);

        saveBtn = new JButton("Save");
        cancelBtn = new JButton("Cancel");
        add(saveBtn);
        add(cancelBtn);

        saveBtn.addActionListener(e -> savePurchaseOrder());
        cancelBtn.addActionListener(e -> clearForm());
    }

    private String generatePOID() {
        return "PO" + System.currentTimeMillis();
    }

    private void savePurchaseOrder() {
        String poId = poIdField.getText().trim();
        String prId = prIdField.getText().trim();
        String itemId = itemIdField.getText().trim();
        String itemName = itemNameField.getText().trim();
        String quantity = quantityField.getText().trim();
        String supplierId = supplierIdField.getText().trim();
        String purchaseManagerId = purchaseManagerIdField.getText().trim();
        String approvalStatus = approvalStatusField.getText().trim();
        String deliveryStatus = deliveryStatusField.getText().trim();
        String deliveryDate = deliveryDateField.getText().trim();

        if (prId.isEmpty() || itemId.isEmpty() || itemName.isEmpty() || quantity.isEmpty() || supplierId.isEmpty() || purchaseManagerId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields except PO ID!");
            return;
        }

        String line = String.join(",", poId, prId, itemId, itemName, quantity, supplierId, purchaseManagerId, approvalStatus, deliveryStatus, deliveryDate);

        FileUtil.appendLine("po.txt", line);
        JOptionPane.showMessageDialog(this, "Purchase Order saved!");
        clearForm();
        // Regenerate new PO ID for next entry
        poIdField.setText(generatePOID());
    }

    private void clearForm() {
        prIdField.setText("");
        itemIdField.setText("");
        itemNameField.setText("");
        quantityField.setText("");
        supplierIdField.setText("");
        purchaseManagerIdField.setText("");
        approvalStatusField.setText("Pending");
        deliveryStatusField.setText("Not Delivered");
        deliveryDateField.setText("");
    }
}
