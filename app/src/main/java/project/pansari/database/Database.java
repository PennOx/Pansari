package project.pansari.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import project.pansari.auth.Auth;

public class Database {

    private static final DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    private static final DatabaseReference wholesalersRef = root.child("Wholesaler");
    private static final DatabaseReference shopkeeperRef = root.child("Shopkeeper");
    private static final DatabaseReference pinRef = root.child("PIN");
    private static final DatabaseReference productsRef = root.child("Products");
    private static final DatabaseReference cartRef = root.child("Cart");
    private static final DatabaseReference ordersRef = root.child("Orders");

    private static final StorageReference storageRoot = FirebaseStorage.getInstance().getReference();
    private static final StorageReference profilePicturesStorageRef = storageRoot.child("ProfilePictures");

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

    public static DatabaseReference getOrderRefById(String oId) {
        return ordersRef.child(oId);
    }

    public static DatabaseReference getShopkeeperOrdersRefBySid(String sId) {
        return getShopkeepersRef().child(sId).child("Orders");
    }

    public static DatabaseReference getShopkeeperOrderRefBySidAndOid(String sId, String oId) {
        return getShopkeeperOrdersRefBySid(sId).child(oId);
    }

    public static DatabaseReference getWholesalerOrdersRefByWid(String wId) {
        return getWholesalersRef().child(wId).child("Orders");
    }

    public static DatabaseReference getWholesalerOrderRefByWidAndOid(String wId, String oId) {
        return getWholesalerOrdersRefByWid(wId).child(oId);
    }

    public static DatabaseReference getCartRef() {
        return cartRef.child(Auth.getCurrentUserUid());
    }

    public static StorageReference getProfilePictureStorageRefById(String id) {
        return profilePicturesStorageRef.child(id);
    }
}
