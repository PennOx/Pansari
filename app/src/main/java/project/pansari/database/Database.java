package project.pansari.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import project.pansari.auth.Auth;

public class Database {

    private static final DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    private static final DatabaseReference wholesalersRef = root.child("Wholesaler");
    private static final DatabaseReference shopkeeperRef = root.child("Shopkeeper");
    private static final DatabaseReference pinRef = root.child("PIN");
    private static final DatabaseReference productsRef = root.child("Products");
    private static final DatabaseReference cartRef = root.child("Cart");

    static {
        root.keepSynced(true);
    }

    public static DatabaseReference getWholesalersRef() {
        return wholesalersRef;
    }

    public static DatabaseReference getWholesalerRefById(String wid) {
        return getWholesalersRef().child(wid);
    }

    public static DatabaseReference getWholesalerProductsRefByWid(String wid) {
        return getWholesalersRef().child(wid).child("Products");
    }

    public static DatabaseReference getShopkeepersRef() {
        return shopkeeperRef;
    }

    public static DatabaseReference getShopkeeperRefById(String sid) {
        return getShopkeepersRef().child(sid);
    }

    public static DatabaseReference getPinRef() {
        return pinRef;
    }

    public static DatabaseReference getPinRef(int pinCode) {
        return getPinRef().child("" + pinCode);
    }

    public static DatabaseReference getProductsRef() {
        return productsRef;
    }

    public static DatabaseReference getProductRefById(String id) {
        return getProductsRef().child(id);
    }

    public static DatabaseReference getCartRef() {
        return cartRef.child(Auth.getCurrentUserUid());
    }
}