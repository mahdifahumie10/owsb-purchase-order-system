package com.owsb.system.model;

public class Payment {
    private String paymentID;
    private String poID;
    private double amount;
    private String paymentDate;
    private String fmID;

    public Payment(String paymentID, String poID, double amount, String paymentDate, String fmID) {
        this.paymentID = paymentID;
        this.poID = poID;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.fmID = fmID;
    }

    public String getPaymentID() { return paymentID; }
    public String getPoID() { return poID; }
    public double getAmount() { return amount; }
    public String getPaymentDate() { return paymentDate; }
    public String getFmID() { return fmID; }

    public void setPaymentID(String paymentID) { this.paymentID = paymentID; }
    public void setPoID(String poID) { this.poID = poID; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }
    public void setFmID(String fmID) { this.fmID = fmID; }

    public String toCSV() {
        return paymentID + "," + poID + "," + amount + "," + paymentDate + "," + fmID;
    }
}
