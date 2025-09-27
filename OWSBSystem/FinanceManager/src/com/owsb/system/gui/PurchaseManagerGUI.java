package com.owsb.system.gui;

import javax.swing.*;
import java.awt.*;

// ADD these imports for your panels
import com.owsb.system.gui.PurchaseRequisitionPanel;
import com.owsb.system.gui.PurchaseOrderPanel;
import com.owsb.system.gui.ApprovePOPanel;
import com.owsb.system.gui.PurchaseReportsGUI;
import com.owsb.system.gui.PurchaseOrderEntryPanel;  // <-- added import

public class PurchaseManagerGUI extends JFrame {

    private String userID;

    public PurchaseManagerGUI(String userID) {
        this.userID = userID;

        setTitle("Purchase Manager");
        setSize(500, 400);  // increased height for extra button
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1, 10, 10));  // 6 rows now for 6 buttons

        JLabel welcomeLabel = new JLabel("Welcome, Purchase Manager: " + userID, SwingConstants.CENTER);
        add(welcomeLabel);

        JButton createPRBtn = new JButton("Create Purchase Requisition");
        JButton createPOBtn = new JButton("Create Purchase Order");  // NEW BUTTON
        JButton viewPOBtn = new JButton("View Purchase Orders");
        JButton approvePOBtn = new JButton("Approve Purchase Orders");
        JButton reportBtn = new JButton("View Purchase Reports");
        JButton exitBtn = new JButton("Exit");

        add(createPRBtn);
        add(createPOBtn);  // add new button here
        add(viewPOBtn);
        add(approvePOBtn);
        add(reportBtn);
        add(exitBtn);

        createPRBtn.addActionListener(e -> {
            JFrame prFrame = new JFrame("Create Purchase Requisition");
            prFrame.setSize(600, 400);
            prFrame.setLocationRelativeTo(null);
            prFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            prFrame.add(new PurchaseRequisitionPanel(userID));
            prFrame.setVisible(true);
        });

        createPOBtn.addActionListener(e -> {  // new listener for new button
            JFrame poEntryFrame = new JFrame("Create Purchase Order");
            poEntryFrame.setSize(700, 500);
            poEntryFrame.setLocationRelativeTo(null);
            poEntryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            poEntryFrame.add(new PurchaseOrderEntryPanel());
            poEntryFrame.setVisible(true);
        });

        viewPOBtn.addActionListener(e -> {
            JFrame poFrame = new JFrame("View Purchase Orders");
            poFrame.setSize(700, 400);
            poFrame.setLocationRelativeTo(null);
            poFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            poFrame.add(new PurchaseOrderPanel());
            poFrame.setVisible(true);
        });

        approvePOBtn.addActionListener(e -> {
            JFrame approveFrame = new JFrame("Approve Purchase Orders");
            approveFrame.setSize(700, 400);
            approveFrame.setLocationRelativeTo(null);
            approveFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            approveFrame.add(new ApprovePOPanel(userID));
            approveFrame.setVisible(true);
        });

        reportBtn.addActionListener(e -> {
            JFrame reportFrame = new JFrame("Purchase Reports");
            reportFrame.setSize(800, 400);
            reportFrame.setLocationRelativeTo(null);
            reportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            reportFrame.add(new PurchaseReportsGUI());
            reportFrame.setVisible(true);
        });

        exitBtn.addActionListener(e -> System.exit(0));
    }
}
