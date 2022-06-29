package ch.saryve.put_away.entities;

import com.google.firebase.firestore.DocumentId;

public class Category {

    @DocumentId
    private String documentId; // Firestorm database document id
    private String name;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
