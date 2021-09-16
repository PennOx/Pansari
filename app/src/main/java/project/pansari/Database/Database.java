package project.pansari.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {

    private static final DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    private static final DatabaseReference wholesalersRef = root.child("Wholesaler");
    private static final DatabaseReference shopkeeperRef = root.child("Shopkeeper");
    private static final DatabaseReference pinRef = root.child("PIN");
    private static final DatabaseReference productsRef = root.child("Products");

    static {
        root.keepSynced(true);
    }

    public static DatabaseReference getWholesalersRef() {
        return wholesalersRef;
    }

    public static DatabaseReference getShopkeeperRef() {
        return shopkeeperRef;
    }

    public static DatabaseReference getPinRef() {
        return pinRef;
    }

    public static DatabaseReference getProductsRef() {
        return productsRef;
    }

    public static DatabaseReference getPinRef(int pinCode) {
        return getPinRef().child("" + pinCode);
    }

    public static DatabaseReference getWholesalerProductsRef(String wid) {
        return getWholesalersRef().child(wid).child("Products");
    }
}
