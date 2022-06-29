package ch.saryve.put_away.entities;


import androidx.recyclerview.widget.SortedList;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.local.ReferenceSet;
import com.google.j2objc.annotations.Property;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Item {
    @DocumentId
    private String documentId; // Firestorm database document id
    private int amount;
    private DocumentReference category;
    private String title;
    private DocumentReference owner;
    private double price;
    private Date buyingDate;
    private double warrantyYears;
    private GeoPoint boughtFrom;
    private String website;
    private String description;
    private ArrayList<DocumentReference> items;
    private ArrayList<String> images;

    public String getDocumentId() {
        return documentId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    public DocumentReference getCategory() {
        return category;
    }

    public void setCategory(DocumentReference category) {
        this.category = category;
    }

    public ArrayList<DocumentReference> getItems() {
        return items;
    }

    public void setItems(ArrayList<DocumentReference> items) {
        this.items = items;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public DocumentReference getOwner() {
        return owner;
    }

    public void setOwner(DocumentReference owner) {
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
