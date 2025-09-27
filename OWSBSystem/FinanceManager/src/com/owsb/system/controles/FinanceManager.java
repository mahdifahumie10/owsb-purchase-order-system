package com.owsb.system.controles;

import com.owsb.system.model.Payment;
import com.owsb.system.model.PurchaseOrder;
import com.owsb.system.utils.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class FinanceManager {
    private final String paymentsFile = "payments.txt";
    private POManager poManager;
    private List<Payment> payments;

    public FinanceManager() {
        poManager = new POManager();
        payments = loadPayments();
    }

    // Load payments from file
    private List<Payment> loadPayments() {
        List<Payment> list = new ArrayList<>();
        List<String> lines = FileUtil.readLines(paymentsFile);
        for (String line : lines) {
            if (line.isBlank()) continue;

            String[] parts = line.split(",");
            if (parts.length < 5) continue;

            String paymentID = parts[0];
            String poID = parts[1];
            double amount = Double.parseDouble(parts[2]);
            String paymentDate = parts[3];
            String fmID = parts[4];

            list.add(new Payment(paymentID, poID, amount, paymentDate, fmID));
        }
        return list;
    }

    // Save a payment to file (append)
    private void savePayment(Payment payment) {
        FileUtil.appendLine(paymentsFile, payment.toCSV());
        payments.add(payment);
    }

    // Get all pending POs (approvalStatus == "Pending")
    public List<PurchaseOrder> getPendingPOs() {
        List<PurchaseOrder> allPOs = poManager.getAllPOs();
        List<PurchaseOrder> pending = new ArrayList<>();
        for (PurchaseOrder po : allPOs) {
            if ("Pending".equalsIgnoreCase(po.getApprovalStatus())) {
                pending.add(po);
            }
        }
        return pending;
    }

    // Approve PO and create a payment record
    public void approveAndPay(PurchaseOrder po, double amount, String fmID) {
        po.setApprovalStatus("Approved");
        poManager.updatePO(po);

        String payID = "PAY" + String.format("%03d", payments.size() + 1);
        Payment payment = new Payment(payID, po.getPoID(), amount, FileUtil.today(), fmID);
        savePayment(payment);
    }
}
