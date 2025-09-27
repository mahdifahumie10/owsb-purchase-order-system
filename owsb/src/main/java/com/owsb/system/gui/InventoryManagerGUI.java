package com.owsb.system.gui;

import javax.swing.*;
import java.awt.*;

public class InventoryManagerGUI extends JFrame {

    private String userID;

    public InventoryManagerGUI(String userID) {
        this.userID = userID;

        setTitle("Inventory Manager");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel welcomeLabel = new JLabel("Welcome, Inventory Manager: " + userID, SwingConstants.CENTER);
        add(welcomeLabel);

        JButton manageInventoryBtn = new JButton("Manage Inventory");
        JButton viewReportsBtn = new JButton("View Inventory Reports");
        JButton exitBtn = new JButton("Exit");

        add(manageInventoryBtn);
        add(viewReportsBtn);
        add(exitBtn);

        manageInventoryBtn.addActionListener(e -> {
            // Open Manage Inventory panel
            JFrame manageFrame = new JFrame("Manage Inventory");
            manageFrame.setSize(900, 400);
            manageFrame.setLocationRelativeTo(null);
            manageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            manageFrame.add(new ManageInventoryPanel());
            manageFrame.setVisible(true);
        });

        viewReportsBtn.addActionListener(e -> {
            // Open Inventory Reports panel
            JFrame reportFrame = new JFrame("Inventory Reports");
            reportFrame.setSize(800, 400);
            reportFrame.setLocationRelativeTo(null);
            reportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            reportFrame.add(new InventoryReportPanel());
            reportFrame.setVisible(true);
        });

        exitBtn.addActionListener(e -> System.exit(0));  // Exit application
    }
}
