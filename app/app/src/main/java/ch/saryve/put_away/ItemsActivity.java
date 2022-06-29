package ch.saryve.put_away;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private EndlessRecyclerViewScrollListener scrollListener;


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
                        Owner owner = document.toObject(Owner.class);
                        owner.setDocumentId(document.getId());
                        ownerMap.put(owner.getDocumentId(), owner);
                    }
                    ItemsActivity.this.owners = ownerMap;
                    downloadCategories();
                } else {
                    showErrorToast("Error getting documents: " + task.getException());
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
                        Category category = document.toObject(Category.class);
                        categoryMap.put(category.getDocumentId(), category);
                    }

                    ItemsActivity.this.categories = categoryMap;

                    downloadItems();
                } else {
                    showErrorToast("Error getting documents: " + task.getException());
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
                      try {
                          Item item = document.toObject(Item.class);
                          itemsMap.put(item.getDocumentId(), item);
                      } catch (Exception e) {
                          Log.d(TAG, e.getLocalizedMessage());
                      }


                    }
                    ItemsActivity.this.items = itemsMap;

                } else {
                    showErrorToast("Error getting documents: " + task.getException());
                }
            }
        });

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

    private void showErrorToast(String message) {
        Toast.makeText(ItemsActivity.this, message, Toast.LENGTH_SHORT);
    }
}