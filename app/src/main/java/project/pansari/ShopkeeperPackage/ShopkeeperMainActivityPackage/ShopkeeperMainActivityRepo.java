package project.pansari.ShopkeeperPackage.ShopkeeperMainActivityPackage;

import android.content.Context;
import android.os.AsyncTask;

import java.util.LinkedList;
import java.util.List;

import project.pansari.Database.OfflineDatabase;
import project.pansari.Models.Product;

public class ShopkeeperMainActivityRepo<T extends CartDataLoadListener> {

    private CartDataLoadListener cartDataListener;
    private Context context;

    private List<Product> cartProducts;

    public ShopkeeperMainActivityRepo(T listener, Context context) {
        cartDataListener = listener;
        this.context = context;
        cartProducts = new LinkedList<>();
        loadCartProducts();
    }

    private void loadCartProducts() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                OfflineDatabase db = OfflineDatabase.getInstance(context);
                cartProducts = db.getCartDao().getAllProducts();
                cartDataListener.onProductsLoadedListener();
            }
        });
    }

    public List<Product> getCartProducts() {
        return cartProducts;
    }
}
