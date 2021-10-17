package project.pansari.wholesalerPackage.wholesalerOrderViewPackage;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

import project.pansari.database.Database;
import project.pansari.models.CartProduct;
import project.pansari.models.OrderWrap;
import project.pansari.models.Shopkeeper;

public class OrderViewRepo {

    private String oid;
    private OrderViewDataLoadListener listener;

    private OrderWrap order;
    private List<CartProduct> orderProducts;

    public OrderViewRepo(String oid, OrderViewDataLoadListener listener) {
        this.oid = oid;
        orderProducts = new LinkedList<>();
        this.listener = listener;

        loadOrder();

    }

    private void loadOrder() {

        Database.getOrderRefById(oid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                OrderWrap order = dataSnapshot.getValue(OrderWrap.class);

                Database.getShopkeeperRefById(order.getFrom()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        order.setUser(dataSnapshot.getValue(Shopkeeper.class));

                        OrderViewRepo.this.order = order;
                        listener.onOrderLoaded();
                        loadProducts();
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
    private void loadProducts() {
        orderProducts = new LinkedList<>();

        order.getProducts().forEach(new BiConsumer<String, CartProduct>() {
            @Override
            public void accept(String s, CartProduct cartProduct) {
                orderProducts.add(cartProduct);
            }
        });

        listener.onProductsLoaded();
    }

    public OrderWrap getOrder() {
        return order;
    }

    public List<CartProduct> getProducts() {
        return orderProducts;
    }


    public void acceptOrder() {
        Database.getOrderRefById(oid).child("status").setValue("Accepted").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                listener.onOrderAccepted();
            }
        });
    }


    public void declineOrder() {
        Database.getOrderRefById(oid).child("status").setValue("Declined").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                listener.onOrderDeclined();
            }
        });
    }
}
