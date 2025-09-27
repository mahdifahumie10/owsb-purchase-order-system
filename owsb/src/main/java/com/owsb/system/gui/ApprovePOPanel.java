package com.owsb.system.gui;

import com.owsb.system.utils.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ApprovePOPanel extends JPanel {

    private JComboBox<String> poDropdown;
    private JButton approveBtn;
    private DefaultComboBoxModel<String> poModel;

    public ApprovePOPanel(String userID) {
        setLayout(new FlowLayout());

        poModel = new DefaultComboBoxModel<>();
        poDropdown = new JComboBox<>(poModel);
        approveBtn = new JButton("Approve Selected PO");

        add(new JLabel("Select Purchase Order ID:"));
        add(poDropdown);
        add(approveBtn);

        loadPOs();

        approveBtn.addActionListener(e -> approveSelectedPO());
    }

    private void loadPOs() {
        poModel.removeAllElements();
        List<String> lines = FileUtil.readLines("po.txt");
        for (String line : lines) {
            if (line.isBlank()) continue;
            String[] parts = line.split(",");
            if (parts.length >= 8 && parts[7].equalsIgnoreCase("Pending")) { // Only pending for approval
                poModel.addElement(parts[0]); // Add PO ID
            }
        }
    }

    private void approveSelectedPO() {
        String selectedPO = (String) poDropdown.getSelectedItem();
        if (selectedPO == null) {
            JOptionPane.showMessageDialog(this, "No PO selected.");
            return;
        }

        List<String> lines = FileUtil.readLines("po.txt");
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 8 && parts[0].equals(selectedPO)) {
                parts[7] = "Approved"; // Update approval status
                updatedLines.add(String.join(",", parts));
            } else {
                updatedLines.add(line);
            }
        }

        try {
            Path path = Path.of("src/po.txt");
            Files.write(path, updatedLines);
            JOptionPane.showMessageDialog(this, "Purchase Order " + selectedPO + " approved.");
            loadPOs(); // Reload dropdown
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating purchase orders.");
            e.printStackTrace();
        }
    }
}
