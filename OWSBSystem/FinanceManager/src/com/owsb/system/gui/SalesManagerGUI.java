package com.owsb.system.gui;

import javax.swing.*;
import java.awt.*;

// Import ManageItemsGUI, SalesEntryPanel, SalesReportsGUI, and ManageSuppliersGUI classes
import com.owsb.system.gui.ManageItemsGUI;
import com.owsb.system.gui.SalesEntryPanel;
import com.owsb.system.gui.SalesReportsGUI;
import com.owsb.system.gui.ManageSuppliersGUI;

public class SalesManagerGUI extends JFrame {

    private String userID;

    // Constructor to accept username
    public SalesManagerGUI(String userID) {
        this.userID = userID;

        setTitle("Sales Manager");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        JLabel welcomeLabel = new JLabel("Welcome, Sales Manager: " + userID, SwingConstants.CENTER);
        add(welcomeLabel);

        JButton manageSalesBtn = new JButton("Manage Sales");
        JButton viewReportsBtn = new JButton("View Sales Reports");
        JButton manageItemsBtn = new JButton("Manage Items");
        JButton manageSuppliersBtn = new JButton("Manage Suppliers");
        JButton exitBtn = new JButton("Exit");

        add(manageSalesBtn);
        add(viewReportsBtn);
        add(manageItemsBtn);
        add(manageSuppliersBtn);
        add(exitBtn);

        // Button actions
        manageSalesBtn.addActionListener(e -> {
            JFrame salesEntryFrame = new JFrame("Sales Entry");
            salesEntryFrame.setSize(400, 300);
            salesEntryFrame.setLocationRelativeTo(null);
            salesEntryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            salesEntryFrame.add(new SalesEntryPanel());  // SalesEntryPanel is a JPanel
            salesEntryFrame.setVisible(true);
        });

        viewReportsBtn.addActionListener(e -> {
            JFrame salesReportsFrame = new JFrame("Sales Reports");
            salesReportsFrame.setSize(900, 400);
            salesReportsFrame.setLocationRelativeTo(null);
            salesReportsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            salesReportsFrame.add(new SalesReportsGUI());  // SalesReportsGUI should be JPanel too
            salesReportsFrame.setVisible(true);
        });

        manageItemsBtn.addActionListener(e -> {
            JFrame manageItemsFrame = new JFrame("Manage Items");
            manageItemsFrame.setSize(900, 400);
            manageItemsFrame.setLocationRelativeTo(null);
            manageItemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            manageItemsFrame.add(new ManageItemsGUI());  // ManageItemsGUI is JPanel
            manageItemsFrame.setVisible(true);
        });

        manageSuppliersBtn.addActionListener(e -> {
            JFrame manageSuppliersFrame = new JFrame("Manage Suppliers");
            manageSuppliersFrame.setSize(700, 400);
            manageSuppliersFrame.setLocationRelativeTo(null);
            manageSuppliersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            manageSuppliersFrame.add(new ManageSuppliersGUI());  // ManageSuppliersGUI is JPanel
            manageSuppliersFrame.setVisible(true);
        });

        exitBtn.addActionListener(e -> System.exit(0));
    }
}
