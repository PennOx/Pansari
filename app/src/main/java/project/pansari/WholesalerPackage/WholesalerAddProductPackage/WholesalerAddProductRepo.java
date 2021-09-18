package project.pansari.WholesalerPackage.WholesalerAddProductPackage;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import project.pansari.Auth.Auth;
import project.pansari.Database.Database;
import project.pansari.Models.Product;

public class WholesalerAddProductRepo {

    private Product product;
    private WholesalerAddProductDataLoadListener loadListener;

    public WholesalerAddProductRepo(WholesalerAddProductDataLoadListener loadListener) {
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

    public void addProduct(Product p) {
        Database.getProductsRef().child(p.getPid()).setValue(p);
        Database.getWholesalerProductsRef(Auth.getCurrentUserUid()).child(p.getPid()).setValue(p.getPid());
    }
}
