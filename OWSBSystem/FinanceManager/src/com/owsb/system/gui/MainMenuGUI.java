package com.owsb.system.gui;

import com.owsb.system.utils.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainMenuGUI extends JFrame {

    private String userID;
    private String role;

    public MainMenuGUI(String userID) {
        this.userID = userID;
        this.role = getRoleFromUsername(userID);

        System.out.println("UserID: " + userID + ", Role: " + role); // debug print

        setTitle("Main Menu");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Using BorderLayout for more control
        setLayout(new BorderLayout());

        // Create a panel for buttons and label
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7, 1, 15, 15)); // rows, cols, hgap, vgap
        mainPanel.setBackground(new Color(32, 47, 90)); // dark blue background

        // Styled welcome label
        JLabel welcomeLabel = new JLabel("Welcome, User: " + userID, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial Black", Font.BOLD, 22));
        welcomeLabel.setForeground(Color.WHITE);
        mainPanel.add(welcomeLabel);

        // Create buttons with custom colors
        JButton financeBtn = createStyledButton("Finance Manager");
        JButton salesBtn = createStyledButton("Sales Manager");
        JButton purchaseBtn = createStyledButton("Purchase Manager");
        JButton inventoryBtn = createStyledButton("Inventory Manager");
        JButton adminBtn = createStyledButton("Administrator");
        JButton exitBtn = createStyledButton("Exit");

        mainPanel.add(financeBtn);
        mainPanel.add(salesBtn);
        mainPanel.add(purchaseBtn);
        mainPanel.add(inventoryBtn);
        mainPanel.add(adminBtn);
        mainPanel.add(exitBtn);

        // Add panel to frame center
        add(mainPanel, BorderLayout.CENTER);

        // Button actions
        financeBtn.addActionListener(e -> {
            dispose();
            new FinanceManagerGUI(userID).setVisible(true);
        });

        salesBtn.addActionListener(e -> {
            dispose();
            new SalesManagerGUI(userID).setVisible(true);
        });

        purchaseBtn.addActionListener(e -> {
            dispose();
            new PurchaseManagerGUI(userID).setVisible(true);
        });

        if ("IM".equals(role) || "Admin".equals(role)) {
            inventoryBtn.addActionListener(e -> {
                dispose();
                new InventoryManagerGUI(userID).setVisible(true);
            });
        } else {
            inventoryBtn.setEnabled(false);
            inventoryBtn.setBackground(new Color(100, 100, 100));
        }

        if ("Admin".equals(role)) {
            adminBtn.addActionListener(e -> {
                dispose();
                new UserManagementGUI().setVisible(true);
            });
        } else {
            adminBtn.setEnabled(false);
            adminBtn.setBackground(new Color(100, 100, 100));
        }

        exitBtn.addActionListener(e -> System.exit(0));
    }

    // Helper to create styled buttons
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setBackground(new Color(72, 126, 176));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    private String getRoleFromUsername(String username) {
        List<String> lines = FileUtil.readLines("users.txt");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                String user = parts[0].trim();
                String role = parts[2].trim();
                if (user.equals(username)) {
                    return role;
                }
            }
        }
        return null;
    }
}
