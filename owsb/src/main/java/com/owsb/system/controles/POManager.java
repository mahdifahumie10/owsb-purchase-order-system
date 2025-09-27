package com.owsb.system.controles;

import com.owsb.system.model.PurchaseOrder;
import com.owsb.system.utils.FileUtil;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class POManager {
    private final String poFile = "po.txt";

    // Load all Purchase Orders from file
    public List<PurchaseOrder> loadPOs() {
        List<PurchaseOrder> list = new ArrayList<>();
        List<String> lines = FileUtil.readLines(poFile);

        System.out.println("Lines loaded from po.txt: " + lines.size());

        for (String line : lines) {
            System.out.println("Line: " + line);
            if (line.isBlank()) continue;

            String[] parts = line.split(",");
            if (parts.length < 10) {
                System.out.println("Skipped malformed line: " + line);
                continue;
            }

            try {
                String poID = parts[0];
                String prID = parts[1];
                String itemID = parts[2];
                String itemName = parts[3];
                int qty = Integer.parseInt(parts[4]);
                String supplierCode = parts[5];
                String pmID = parts[6];
                String approvalStatus = parts[7];
                String deliveryStatus = parts[8];
                String deliveryDate = parts[9];

                PurchaseOrder po = new PurchaseOrder(poID, prID, itemID, itemName, qty, supplierCode, pmID,
                        approvalStatus, deliveryStatus, deliveryDate);
                list.add(po);
            } catch (Exception e) {
                System.out.println("Error parsing line: " + line);
                e.printStackTrace();
            }
        }
        return list;
    }

    // Save all Purchase Orders back to file (overwrite)
    public void savePOs(List<PurchaseOrder> poList) {
        List<String> lines = new ArrayList<>();
        for (PurchaseOrder po : poList) {
            lines.add(po.toCSV());
        }
        try {
            Files.write(Paths.get("src/" + poFile), lines);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all purchase orders
    public List<PurchaseOrder> getAllPOs() {
        return loadPOs();
    }

    // Get all pending (approvalStatus == "Pending") POs
    public List<PurchaseOrder> getAllPendingPOs() {
        List<PurchaseOrder> allPOs = loadPOs();
        List<PurchaseOrder> pending = new ArrayList<>();
        for (PurchaseOrder po : allPOs) {
            if ("Pending".equalsIgnoreCase(po.getApprovalStatus())) {
                pending.add(po);
            }
        }
        return pending;
    }

    // Update a PO approval status
    public void updatePO(PurchaseOrder updatedPO) {
        List<PurchaseOrder> allPOs = loadPOs();
        for (int i = 0; i < allPOs.size(); i++) {
            if (allPOs.get(i).getPoID().equals(updatedPO.getPoID())) {
                allPOs.set(i, updatedPO);
                break;
            }
        }
        savePOs(allPOs);
    }
}
