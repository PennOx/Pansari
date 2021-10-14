package project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import project.pansari.auth.Auth;
import project.pansari.database.Database;
import project.pansari.models.CartItem;
import project.pansari.models.CartProduct;
import project.pansari.models.Order;
import project.pansari.models.OrderWrap;
import project.pansari.models.User;
import project.pansari.models.Wholesaler;

public class ShopkeeperMainActivityRepo<T extends ShopkeeperMainActivityDataLoadListener> {

    private ShopkeeperMainActivityDataLoadListener listener;

    private List<CartItem> cartItems;
    private List<Wholesaler> availableWholesalers;
    private List<Wholesaler> favoriteWholesalers;
    private List<OrderWrap> orders;

    public ShopkeeperMainActivityRepo(T listener, Context context) {
        this.listener = listener;
        cartItems = new LinkedList<>();
        availableWholesalers = new LinkedList<>();
        favoriteWholesalers = new LinkedList<>();
        orders = new LinkedList<>();
        loadAvailableWholesalers();
        loadCartProducts();
        loadFavoriteWholesalers();
        loadOrders();
    }

    public void loadAvailableWholesalers() {
        availableWholesalers = new LinkedList<>();
        Database.getShopkeepersRef().child(Auth.getCurrentUser().getUid()).child("pinCode").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long currentPin = snapshot.getValue(Long.class);

                Database.getPinRef().child(currentPin + "").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String wid = dataSnapshot.getKey();

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

    public void loadCartProducts() {
        cartItems = new LinkedList<>();


        Database.getCartRef().addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator wholesalersIt = dataSnapshot.getChildren().iterator();

                while (wholesalersIt.hasNext()) {
                    DataSnapshot wholesaler = (DataSnapshot) wholesalersIt.next();

                    CartItem item = new CartItem();
                    item.setProducts(new LinkedList<>());
                    cartItems.add(item);

                    String wid = wholesaler.getKey();

                    Database.getWholesalersRef().child(wid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            item.setWholesaler(dataSnapshot.getValue(Wholesaler.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    Iterator productIt = wholesaler.getChildren().iterator();

                    while (productIt.hasNext()) {
                        DataSnapshot product = (DataSnapshot) productIt.next();


                        String pId = product.getKey();
                        Integer quantity = product.getValue(Integer.class);

                        Database.getProductRefById(pId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                CartProduct product = dataSnapshot.getValue(CartProduct.class);
                                product.setInCartQuantity(quantity);

                                item.getProducts().add(product);

                                listener.onProductsLoaded();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void loadFavoriteWholesalers() {
        favoriteWholesalers = new LinkedList<>();
        Database.getShopkeeperRefById(Auth.getCurrentUserUid()).child("Fabs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String wid = snapshot.getValue(String.class);

                    Database.getWholesalerRefById(wid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Wholesaler w = snapshot.getValue(Wholesaler.class);
                            favoriteWholesalers.add(w);
                            listener.onFavoriteWholesalersLoaded();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void loadOrders() {
        orders = new LinkedList<>();

        Database.getShopkeeperOrdersRefBySid(Auth.getCurrentUserUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> oIdIterator = dataSnapshot.getChildren().iterator();

                while (oIdIterator.hasNext()) {
                    String oId = oIdIterator.next().getValue(String.class);

                    Database.getOrderRefById(oId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            OrderWrap wrap = dataSnapshot.getValue(OrderWrap.class);

                            Database.getWholesalerRefById(wrap.getTo()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    wrap.setUser(dataSnapshot.getValue(User.class));
                                    orders.add(wrap);
                                    listener.onOrdersLoaded();
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

    public List<CartItem> getCartProducts() {
        return cartItems;
    }

    public List<Wholesaler> getAvailableWholesalers() {
        return availableWholesalers;
    }

    public List<Wholesaler> getFavoriteWholesalers() {
        return favoriteWholesalers;
    }

    public void placeOrder() {

        for (CartItem item : cartItems) {
            Order od = new Order();
            od.setTimestamp(System.currentTimeMillis());
            od.setId("OD" + od.getTimestamp());
            od.setFrom(Auth.getCurrentUserUid());
            od.setTo(item.getWholesaler().getWid());
            od.setStatus("Pending");
            od.setProductsCount(item.getProducts().size());

            Map<String, CartProduct> products = new HashMap<>();

            for (CartProduct product : item.getProducts()) {
                products.put(product.getPid(), product);
            }
            od.setProducts(products);

            Database.getOrderRefById(od.getId()).setValue(od);
            Database.getCartRef().child(od.getTo()).setValue(null);
            Database.getShopkeeperOrderRefBySidAndOid(od.getFrom(), od.getId()).setValue(od.getId());
            Database.getWholesalerOrderRefByWidAndOid(od.getTo(), od.getId()).setValue(od.getId());
        }

        cartItems.clear();
        listener.onProductsLoaded();
        listener.onOrderPlaced();
    }

    public List<OrderWrap> getOrders() {
        return orders;
    }
}
