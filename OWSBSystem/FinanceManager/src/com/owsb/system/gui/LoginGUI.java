package com.owsb.system.gui;

import com.owsb.system.utils.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoginGUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginGUI() {
        setTitle("Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(30, 144, 255)); // Dodger Blue bg
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setLayout(new BorderLayout(0, 20));
        add(mainPanel);

        // Top label "Group31 GUI"
        JLabel titleLabel = new JLabel("Group31 GUI", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Center form panel with GridLayout
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setOpaque(false);  // transparent so bg shows
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Username label & field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(userLabel);

        usernameField = new JTextField();
        formPanel.add(usernameField);

        // Password label & field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(passLabel);

        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        // Buttons panel
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(60, 179, 113)); // Medium Sea Green
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        formPanel.add(loginButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(220, 20, 60)); // Crimson Red
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        formPanel.add(exitButton);

        // Button actions
        loginButton.addActionListener(e -> doLogin());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void doLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.");
            return;
        }

        String role = authenticate(username, password);
        if (role == null) {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        } else {
            JOptionPane.showMessageDialog(this, "Login successful! Role: " + role);
            openRoleDashboard(username);
            this.dispose();
        }
    }

    private String authenticate(String username, String password) {
        List<String> lines = FileUtil.readLines("users.txt");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                String user = parts[0].trim();
                String pass = parts[1].trim();
                String role = parts[2].trim();
                if (user.equals(username) && pass.equals(password)) {
                    return role;
                }
            }
        }
        return null;
    }

    private void openRoleDashboard(String username) {
        new MainMenuGUI(username).setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginGUI().setVisible(true);
        });
    }
}
