package ch.saryve.put_away.entities;


import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

public class Item {
    private String documentId; // Firestorm database document id
    private Category category;
    private String title;
    private Owner owner;
    private double price;
    private Date buyingDate;
    private double warrantyYears;
    private GeoPoint boughtFrom;
    private String website;
    private String description;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getBuyingDate() {
        return buyingDate;
    }

    public void setBuyingDate(Date buyingDate) {
        this.buyingDate = buyingDate;
    }

    public double getWarrantyYears() {
        return warrantyYears;
    }

    public void setWarrantyYears(double warrantyYears) {
        this.warrantyYears = warrantyYears;
    }

    public GeoPoint getBoughtFrom() {
        return boughtFrom;
    }

    public void setBoughtFrom(GeoPoint boughtFrom) {
        this.boughtFrom = boughtFrom;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
