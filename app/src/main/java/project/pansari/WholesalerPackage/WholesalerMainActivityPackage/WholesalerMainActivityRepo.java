package project.pansari.WholesalerPackage.WholesalerMainActivityPackage;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import project.pansari.Auth.Auth;
import project.pansari.Database.Database;
import project.pansari.Models.Product;
import project.pansari.Models.Wholesaler;
import project.pansari.ShopkeeperPackage.WholesalerProductOverviewPackage.DataLoadListener;

public class WholesalerMainActivityRepo<T extends DataLoadListener> {

    private DataLoadListener dataLoadListener;
    private Wholesaler wholesaler = new Wholesaler();
    private List<Product> productList = new LinkedList<>();

    public WholesalerMainActivityRepo(T context) {
        dataLoadListener = context;
        loadProducts();
        loadWholesaler();
    }

    public MutableLiveData<List<Product>> getProducts() {
        MutableLiveData<List<Product>> p = new MutableLiveData<>();
        p.setValue(productList);
        return p;
    }

    public MutableLiveData<Wholesaler> getWholesaler() {

        MutableLiveData<Wholesaler> w = new MutableLiveData<>();
        w.setValue(wholesaler);
        return w;
    }

    private void loadProducts() {
        Database.getWholesalerProductsRef(Auth.getCurrentUserUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();

                List<String> productIds = new LinkedList<>();

                while (iterator.hasNext()) {
                    productIds.add(iterator.next().getValue(String.class));
                }

                for (String id : productIds) {
                    Database.getProductsRef().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            productList.add(snapshot.getValue(Product.class));
                            dataLoadListener.onProductsLoaded();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadWholesaler() {

        Database.getWholesalersRef().child(Auth.getCurrentUserUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                wholesaler = snapshot.getValue(Wholesaler.class);
                dataLoadListener.onWholesalerLoaded();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
