package project.pansari.ShopkeeperPackage.ShopkeeperMainActivityPackage;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import project.pansari.Auth.Auth;
import project.pansari.Database.Database;
import project.pansari.Database.OfflineDatabase;
import project.pansari.Models.Product;
import project.pansari.Models.Wholesaler;

public class ShopkeeperMainActivityRepo<T extends ShopkeeperMainActivityDataLoadListener> {

    private ShopkeeperMainActivityDataLoadListener listener;
    private Context context;

    private List<Product> cartProducts;
    private List<Wholesaler> availableWholesalers;

    public ShopkeeperMainActivityRepo(T listener, Context context) {
        this.listener = listener;
        this.context = context;
        cartProducts = new LinkedList<>();
        availableWholesalers = new LinkedList<>();
        loadCartProducts();
        loadAvailableWholesalers();
    }

    private void loadAvailableWholesalers() {
        Database.getShopkeeperRef().child(Auth.getCurrentUser().getUid()).child("pinCode").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long currentPin = snapshot.getValue(Long.class);

                Database.getPinRef().child(currentPin + "").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();

                        while (iterator.hasNext()) {
                            String wid = iterator.next().getKey();

                            Database.getWholesalersRef().child(wid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Wholesaler w = snapshot.getValue(Wholesaler.class);
                                    availableWholesalers.add(w);
                                    listener.onAvailableWholesalersLoaded();

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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadCartProducts() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                OfflineDatabase db = OfflineDatabase.getInstance(context);
                cartProducts = db.getCartDao().getAllProducts();
                listener.onProductsLoaded();
            }
        });
    }

    public List<Product> getCartProducts() {
        return cartProducts;
    }

    public List<Wholesaler> getAvailableWholesalers() {
        return availableWholesalers;
    }
}
