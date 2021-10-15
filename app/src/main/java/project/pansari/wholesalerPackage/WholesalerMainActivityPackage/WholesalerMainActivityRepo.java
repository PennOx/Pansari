package project.pansari.wholesalerPackage.WholesalerMainActivityPackage;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import project.pansari.auth.Auth;
import project.pansari.database.Database;
import project.pansari.models.OrderWrap;
import project.pansari.models.Product;
import project.pansari.models.Shopkeeper;
import project.pansari.models.Wholesaler;

public class WholesalerMainActivityRepo<T extends WholesalerMainActivityDataLoadListener> {

    private final WholesalerMainActivityDataLoadListener listener;

    private Wholesaler wholesaler = new Wholesaler();

    private List<Product> productList;
    private List<OrderWrap> pendingOrders;
    private List<OrderWrap> completedOrders;

    public WholesalerMainActivityRepo(T context) {
        listener = context;

        productList = new LinkedList<>();
        pendingOrders = new LinkedList<>();
        completedOrders = new LinkedList<>();

        loadProducts();
        loadWholesaler();
        loadOrders();
    }

    public List<Product> getProducts() {
        return productList;
    }

    public Wholesaler getWholesaler() {
        return wholesaler;
    }

    public List<OrderWrap> getPendingOrders() {
        return pendingOrders;
    }

    public List<OrderWrap> getCompletedOrders() {
        return completedOrders;
    }

    void loadProducts() {
        productList = new LinkedList<>();
        Database.getWholesalerProductsRefByWid(Auth.getCurrentUserUid()).addListenerForSingleValueEvent(new ValueEventListener() {
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
                            listener.onProductsLoaded();
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

    void loadWholesaler() {
        Database.getWholesalersRef().child(Auth.getCurrentUserUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                wholesaler = snapshot.getValue(Wholesaler.class);
                listener.onWholesalerLoaded();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    void loadOrders() {
        pendingOrders = new LinkedList<>();
        completedOrders = new LinkedList<>();

        Database.getWholesalerOrdersRefByWid(Auth.getCurrentUserUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String oId = snapshot.getValue(String.class);

                    Database.getOrderRefById(oId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            OrderWrap order = dataSnapshot.getValue(OrderWrap.class);

                            Database.getShopkeeperRefById(order.getFrom()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    order.setUser(dataSnapshot.getValue(Shopkeeper.class));

                                    if (order.getStatus().equals("Completed") || order.getStatus().equals("Cancelled")) {
                                        completedOrders.add(order);
                                        listener.onCompletedOrdersLoaded();
                                    } else {
                                        pendingOrders.add(order);
                                        listener.onPendingOrdersLoaded();
                                    }
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
