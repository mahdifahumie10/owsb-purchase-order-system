package com.owsb.system.gui;

import com.owsb.system.utils.FileUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserManagementGUI extends JFrame {

    private DefaultTableModel tableModel;
    private JTable userTable;

    public UserManagementGUI() {
        setTitle("User Management");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new String[]{"Username", "Password", "Role"}, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();

        JButton addBtn = new JButton("Add User");
        JButton editBtn = new JButton("Edit User");
        JButton deleteBtn = new JButton("Delete User");
        JButton refreshBtn = new JButton("Refresh");
        JButton backBtn = new JButton("Back to Admin Dashboard");

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load users into table
        loadUsers();

        // Button actions
        addBtn.addActionListener(e -> addUser());
        editBtn.addActionListener(e -> editUser());
        deleteBtn.addActionListener(e -> deleteUser());
        refreshBtn.addActionListener(e -> loadUsers());
        backBtn.addActionListener(e -> dispose());
    }

    private void loadUsers() {
        tableModel.setRowCount(0); // clear table
        List<String> lines = FileUtil.readLines("users.txt");
        for (String line : lines) {
            if (line.isBlank()) continue;
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                tableModel.addRow(new Object[]{parts[0], parts[1], parts[2]});
            }
        }
    }

    private void addUser() {
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField roleField = new JTextField();

        Object[] inputs = {
                "Username:", usernameField,
                "Password:", passwordField,
                "Role:", roleField
        };

        int result = JOptionPane.showConfirmDialog(this, inputs, "Add New User", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String role = roleField.getText().trim();

            if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            // Append new user to file
            String newUser = username + "," + password + "," + role;
            FileUtil.appendLine("users.txt", newUser);
            loadUsers();
        }
    }

    private void editUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select a user to edit.");
            return;
        }

        String oldUsername = (String) tableModel.getValueAt(selectedRow, 0);
        String oldPassword = (String) tableModel.getValueAt(selectedRow, 1);
        String oldRole = (String) tableModel.getValueAt(selectedRow, 2);

        JTextField usernameField = new JTextField(oldUsername);
        JTextField passwordField = new JTextField(oldPassword);
        JTextField roleField = new JTextField(oldRole);

        Object[] inputs = {
                "Username:", usernameField,
                "Password:", passwordField,
                "Role:", roleField
        };

        int result = JOptionPane.showConfirmDialog(this, inputs, "Edit User", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String newUsername = usernameField.getText().trim();
            String newPassword = passwordField.getText().trim();
            String newRole = roleField.getText().trim();

            if (newUsername.isEmpty() || newPassword.isEmpty() || newRole.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            // Update file
            updateUserInFile(oldUsername, newUsername, newPassword, newRole);
            loadUsers();
        }
    }

    private void updateUserInFile(String oldUsername, String newUsername, String newPassword, String newRole) {
        List<String> lines = FileUtil.readLines("users.txt");
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                String username = parts[0];
                if (username.equals(oldUsername)) {
                    updatedLines.add(newUsername + "," + newPassword + "," + newRole);
                } else {
                    updatedLines.add(line);
                }
            }
        }

        try {
            Path path = Paths.get("src/users.txt");
            Files.write(path, updatedLines);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating users.txt");
            e.printStackTrace();
        }
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select a user to delete.");
            return;
        }

        String usernameToDelete = (String) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Delete user: " + usernameToDelete + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            List<String> lines = FileUtil.readLines("users.txt");
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String username = parts[0];
                    if (!username.equals(usernameToDelete)) {
                        updatedLines.add(line);
                    }
                }
            }

            try {
                Path path = Paths.get("src/users.txt");
                Files.write(path, updatedLines);
                loadUsers();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error updating users.txt");
                e.printStackTrace();
            }
        }
    }
}
