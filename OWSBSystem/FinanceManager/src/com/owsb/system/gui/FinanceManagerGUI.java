package com.owsb.system.gui;

import com.owsb.system.controles.FinanceManager;
import com.owsb.system.model.PurchaseOrder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FinanceManagerGUI extends JFrame {
    private FinanceManager fmCtrl;
    private JTable poTable;
    private DefaultTableModel poModel;
    private JButton btnApprovePay, btnBack;

    public FinanceManagerGUI(String fmID) {
        fmCtrl = new FinanceManager();
        setTitle("Finance Manager Dashboard");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setup layout
        setLayout(new BorderLayout());

        // Table model and JTable for pending purchase orders
        poModel = new DefaultTableModel(new String[]{"PO ID", "PR ID", "Supplier", "Qty", "Date"}, 0);
        poTable = new JTable(poModel);
        JScrollPane scrollPane = new JScrollPane(poTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        btnApprovePay = new JButton("Approve & Pay");
        btnBack = new JButton("Return to Dashboard");
        buttonPanel.add(btnApprovePay);
        buttonPanel.add(btnBack);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load pending purchase orders into table
        loadPendingPOs();

        // Button actions
        btnApprovePay.addActionListener(e -> approveAndPay(fmID));
        btnBack.addActionListener(e -> {
            // Close this window; you can add navigation back to MainMenuGUI later
            dispose();
        });
    }

    private void loadPendingPOs() {
        poModel.setRowCount(0); // clear table
        List<PurchaseOrder> pendingPOs = fmCtrl.getPendingPOs();
        for (PurchaseOrder po : pendingPOs) {
            poModel.addRow(new Object[]{
                    po.getPoID(),
                    po.getPrID(),
                    po.getSupplierCode(),
                    po.getQty(),
                    po.getPoDate()  // Fixed here for Date column
            });
        }
    }

    private void approveAndPay(String fmID) {
        int selectedRow = poTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select a PO first, bro.");
            return;
        }

        PurchaseOrder po = fmCtrl.getPendingPOs().get(selectedRow);

        String amountStr = JOptionPane.showInputDialog(this, "Enter payment amount:");
        if (amountStr == null) return; // user cancelled

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount entered.");
            return;
        }

        fmCtrl.approveAndPay(po, amount, fmID);

        // Reload table data after approval
        loadPendingPOs();

        JOptionPane.showMessageDialog(this, "PO " + po.getPoID() + " approved and payment recorded!");
    }
}
