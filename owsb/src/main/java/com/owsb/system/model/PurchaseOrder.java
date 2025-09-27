package com.owsb.system.model;

public class PurchaseOrder {
    private String poID;
    private String prID;
    private String itemID;
    private String itemName;
    private int qty;
    private String supplierCode;
    private String pmID;
    private String approvalStatus;
    private String deliveryStatus;
    private String poDate;  // renamed here

    // Constructor with all 10 fields
    public PurchaseOrder(String poID, String prID, String itemID, String itemName, int qty, String supplierCode, String pmID,
                         String approvalStatus, String deliveryStatus, String poDate) {  // changed param name
        this.poID = poID;
        this.prID = prID;
        this.itemID = itemID;
        this.itemName = itemName;
        this.qty = qty;
        this.supplierCode = supplierCode;
        this.pmID = pmID;
        this.approvalStatus = approvalStatus;
        this.deliveryStatus = deliveryStatus;
        this.poDate = poDate;  // changed here
    }

    // Getters
    public String getPoID() { return poID; }
    public String getPrID() { return prID; }
    public String getItemID() { return itemID; }
    public String getItemName() { return itemName; }
    public int getQty() { return qty; }
    public String getSupplierCode() { return supplierCode; }
    public String getPmID() { return pmID; }
    public String getApprovalStatus() { return approvalStatus; }
    public String getDeliveryStatus() { return deliveryStatus; }
    public String getPoDate() { return poDate; }  // added this getter

    // Setters
    public void setPoID(String poID) { this.poID = poID; }
    public void setPrID(String prID) { this.prID = prID; }
    public void setItemID(String itemID) { this.itemID = itemID; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setQty(int qty) { this.qty = qty; }
    public void setSupplierCode(String supplierCode) { this.supplierCode = supplierCode; }
    public void setPmID(String pmID) { this.pmID = pmID; }
    public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }
    public void setDeliveryStatus(String deliveryStatus) { this.deliveryStatus = deliveryStatus; }
    public void setPoDate(String poDate) { this.poDate = poDate; }  // added this setter

    // Convert object to CSV line for file saving
    public String toCSV() {
        return poID + "," + prID + "," + itemID + "," + itemName + "," + qty + "," + supplierCode + "," + pmID + "," +
                approvalStatus + "," + deliveryStatus + "," + poDate;  // changed here
    }
}
