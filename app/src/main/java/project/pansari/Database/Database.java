package project.pansari.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {

    private static final DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    private static final DatabaseReference wholesalerRef = root.child("Wholesaler");
    private static final DatabaseReference shopkeeperRef = root.child("Shopkeeper");
    private static final DatabaseReference pinRef = root.child("PIN");

    public static DatabaseReference getWholesalerRef() {
        return wholesalerRef;
    }

    public static DatabaseReference getShopkeeperRef() {
        return shopkeeperRef;
    }

    public static DatabaseReference getPinRef() {
        return pinRef;
    }

}
