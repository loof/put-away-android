package ch.saryve.put_away;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import ch.saryve.put_away.entities.Category;
import ch.saryve.put_away.entities.Item;
import ch.saryve.put_away.entities.Owner;

public class ItemsActivity extends AppCompatActivity {

    private static final String TAG = "Firestore";
    private TextView txtName, txtEmail;
    private Button btnCreate;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private Map<String, Category> categories;
    private Map<String, Owner> owners;
    private Map<String, Item> items;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            finish();
        }

        db = FirebaseFirestore.getInstance();
        downloadOwners();
    }

    private void downloadOwners() {
        db.collection("owners").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Map<String, Owner> ownerMap = new HashMap<>();
                    QuerySnapshot querySnapshot = task.getResult();

                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        Owner owners = new Owner();
                        owners.setDocumentId(document.getId());
                        owners.setName(document.getString("name"));
                        ownerMap.put(owners.getDocumentId(), owners);
                    }
                    ItemsActivity.this.owners = ownerMap;
                    downloadCategories();
                } else {
                    showErrorToast();
                }
            }
        });
    }


    private void downloadCategories() {
        db.collection("categories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Map<String, Category> categoryMap = new HashMap<>();
                    QuerySnapshot querySnapshot = task.getResult();

                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        Category category = new Category();
                        category.setDocumentId(document.getId());
                        category.setName(document.getString("name"));
                        categoryMap.put(category.getDocumentId(), category);
                    }
                    ItemsActivity.this.categories = categoryMap;
                    downloadItems();
                } else {
                    showErrorToast();
                }
            }
        });
    }

    private void downloadItems() {
        db.collection("items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Map<String, Item> itemsMap = new HashMap<>();
                    QuerySnapshot querySnapshot = task.getResult();
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        Item item = new Item();

                        item.setDocumentId(document.getId());

                        if (document.getString("title") != null) {
                            item.setTitle(document.getString("title"));
                        }

                        if (document.getDocumentReference("category").getId() != null) {
                            item.setCategory(categories.get(document.getDocumentReference("category").getId()));
                        }

                        if (document.getString("description") != null) {
                            item.setDescription(document.getString("description"));
                        }

                        if (document.getGeoPoint("bought_from") != null) {
                            item.setBoughtFrom(document.getGeoPoint("bought_from"));
                        }

                        if (document.getDate("buying_date") != null) {
                            item.setBuyingDate(document.getDate("buying_date"));
                        }

                        if (document.getDocumentReference("owner") != null && document.getDocumentReference("owner").getId() != null) {
                            item.setOwner(owners.get(categories.get(document.getDocumentReference("owner").getId())));
                        }

                        if (document.getDouble("price") != null) {
                            item.setPrice(document.getDouble("price"));
                        }

                        if (document.getString("website") != null) {
                            item.setWebsite(document.getString("website"));
                        }

                        if (document.getDouble("warranty_years") != null) {
                            item.setWarrantyYears(document.getDouble("warranty_years"));
                        }

                        itemsMap.put(item.getDocumentId(), item);
                    }
                    ItemsActivity.this.items = itemsMap;

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void showErrorToast() {
        Toast.makeText(ItemsActivity.this, "Something went wrong", Toast.LENGTH_SHORT);
    }


    private void createItem() {

        // Create a new user with a first and last name
        Map<String, Object> item = new HashMap<>();
        item.put("first", "Ada");
        item.put("last", "Lovelace");
        item.put("born", 1815);

        // Add a new document with a generated ID
        db.collection("users")
                .add(item)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

}