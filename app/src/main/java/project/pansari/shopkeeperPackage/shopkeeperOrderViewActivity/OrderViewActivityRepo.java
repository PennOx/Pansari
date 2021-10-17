package project.pansari.shopkeeperPackage.shopkeeperOrderViewActivity;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

import project.pansari.database.Database;
import project.pansari.models.CartProduct;
import project.pansari.models.OrderWrap;
import project.pansari.models.User;

public class OrderViewActivityRepo {

    private final String oId;
    private final OrderViewDataLoadListener listener;
    private OrderWrap orderWrap;
    private List<CartProduct> orderProducts;

    public OrderViewActivityRepo(String oId, OrderViewDataLoadListener listener) {
        this.oId = oId;
        this.listener = listener;

        orderProducts = new LinkedList<>();

        loadOrder();
    }

    private void loadOrder() {

        Database.getOrderRefById(oId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                OrderWrap wrap = dataSnapshot.getValue(OrderWrap.class);

                Database.getWholesalerRefById(wrap.getTo()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        wrap.setUser(dataSnapshot.getValue(User.class));
                        orderWrap = wrap;
                        loadOrderProducts();
                        listener.onOrderLoaded();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadOrderProducts() {
        orderProducts = new LinkedList<>();

        orderWrap.getProducts().forEach(new BiConsumer<String, CartProduct>() {
            @Override
            public void accept(String s, CartProduct cartProduct) {
                orderProducts.add(cartProduct);
            }
        });

        listener.onOrderProductsLoaded();
    }

    public OrderWrap getOrderWrap() {
        return orderWrap;
    }

    public List<CartProduct> getOrderProducts() {
        return orderProducts;
    }
}
