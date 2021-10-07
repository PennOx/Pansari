package project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import project.pansari.auth.Auth;
import project.pansari.database.Database;
import project.pansari.models.CartItem;
import project.pansari.models.CartProduct;
import project.pansari.models.Wholesaler;

public class ShopkeeperMainActivityRepo<T extends ShopkeeperMainActivityDataLoadListener> {

    private ShopkeeperMainActivityDataLoadListener listener;

    private List<CartItem> cartItems;
    private List<Wholesaler> availableWholesalers;
    private List<Wholesaler> favoriteWholesalers;

    public ShopkeeperMainActivityRepo(T listener, Context context) {
        this.listener = listener;
        cartItems = new LinkedList<>();
        availableWholesalers = new LinkedList<>();
        favoriteWholesalers = new LinkedList<>();
        loadAvailableWholesalers();
        loadCartProducts();
        loadFavoriteWholesalers();
    }


    private void loadAvailableWholesalers() {
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
        //TODO load cart products

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

    private void loadFavoriteWholesalers() {
        //TODO
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

    public List<CartItem> getCartProducts() {
        return cartItems;
    }

    public List<Wholesaler> getAvailableWholesalers() {
        return availableWholesalers;
    }

    public List<Wholesaler> getFavoriteWholesalers() {
        return favoriteWholesalers;
    }
}
