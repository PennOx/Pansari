package project.pansari.ShopkeeperPackage.WholesalerProductOverviewPackage;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import project.pansari.Database.Database;
import project.pansari.Models.Product;
import project.pansari.Models.Wholesaler;

public class WholesalerProductOverviewRepo<T extends WholesalerProductOverviewDataLoadListener> {

    private String wid;
    private WholesalerProductOverviewDataLoadListener wholesalerProductOverviewDataLoadListener;
    private Wholesaler wholesaler = new Wholesaler();
    private List<Product> productList = new LinkedList<>();

    public WholesalerProductOverviewRepo(T context, String wid) {
        this.wid = wid;
        wholesalerProductOverviewDataLoadListener = context;
        loadWholesaler(wid);
        loadProducts();
    }

    public MutableLiveData<Wholesaler> getWholesaler() {

        MutableLiveData<Wholesaler> w = new MutableLiveData<>();
        w.setValue(wholesaler);
        return w;
    }

    public MutableLiveData<List<Product>> getProducts() {
        MutableLiveData<List<Product>> p = new MutableLiveData<>();
        p.setValue(productList);
        return p;
    }

    private void loadProducts() {
        Database.getWholesalerProductsRef(wid).addListenerForSingleValueEvent(new ValueEventListener() {
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
                            wholesalerProductOverviewDataLoadListener.onProductsLoaded();
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

    private void loadWholesaler(String wid) {

        Database.getWholesalersRef().child(wid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                wholesaler = snapshot.getValue(Wholesaler.class);
                wholesalerProductOverviewDataLoadListener.onWholesalerLoaded();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void addProductToCart(String pid) {
        //TODO add to cart
    }
}
