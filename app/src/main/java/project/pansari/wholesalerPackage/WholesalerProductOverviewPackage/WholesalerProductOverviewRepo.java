package project.pansari.wholesalerPackage.WholesalerProductOverviewPackage;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import project.pansari.auth.Auth;
import project.pansari.database.Database;
import project.pansari.models.Product;

public class WholesalerProductOverviewRepo {

    private Product product;
    private final WholesalerProductOverviewDataListener loadListener;

    public WholesalerProductOverviewRepo(WholesalerProductOverviewDataListener loadListener) {
        this.loadListener = loadListener;
    }

    public Product getProduct() {
        return product;
    }

    public void loadProduct(String pid) {

        Database.getProductsRef().child(pid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                product = snapshot.getValue(Product.class);
                loadListener.onProductLoaded();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void editProduct(Product p) {
        Database.getProductsRef().child(p.getPid()).setValue(p);
    }

    public void addProduct(Product p, Uri imageUri) {
        Database.getProductImageStorageRefById(p.getPid()).child("image.jpg").putFile(imageUri).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                Database.getProductImageStorageRefById(p.getPid()).child("image.jpg").getDownloadUrl().addOnSuccessListener(uri -> {
                    p.setImage(uri.toString());
                    addProduct(p);
                });
            } else {
                addProduct(p);
            }
        });
    }

    public void editProduct(Product p, Uri imageUri) {
        Database.getProductImageStorageRefById(p.getPid()).child("image.jpg").putFile(imageUri).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                Database.getProductImageStorageRefById(p.getPid()).child("image.jpg").getDownloadUrl().addOnSuccessListener(uri -> {
                    p.setImage(uri.toString());
                    editProduct(p);
                });
            } else {
                editProduct(p);
            }
        });
    }

    public void addProduct(Product p) {
        Database.getProductsRef().child(p.getPid()).setValue(p);
        Database.getWholesalerProductsRefByWid(Auth.getCurrentUserUid()).child(p.getPid()).setValue(p.getPid());
    }
}
