package project.pansari.ShopkeeperPackage.ShopkeeperMainActivityPackage;

import android.app.Application;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import project.pansari.Models.Product;
import project.pansari.R;
import project.pansari.ShopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments.ShopkeeperCart;
import project.pansari.ShopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments.ShopkeeperOrders;
import project.pansari.ShopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments.ShopkeeperProfile;
import project.pansari.ShopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments.ShopkeeperWholesalerList;

public class ShopkeeperMainActivityViewModel extends AndroidViewModel implements CartDataLoadListener {

    private final ShopkeeperWholesalerList wholesalerList;
    private final ShopkeeperOrders orders;
    private final ShopkeeperCart cart;
    private final ShopkeeperProfile profile;

    private ShopkeeperMainActivityRepo<ShopkeeperMainActivityViewModel> repo;

    private MutableLiveData<List<Product>> inCartProducts;
    private MutableLiveData<Fragment> activeFragment;

    public ShopkeeperMainActivityViewModel(Application app) {
        super(app);

        wholesalerList = new ShopkeeperWholesalerList();
        orders = new ShopkeeperOrders();
        cart = new ShopkeeperCart(this);
        profile = new ShopkeeperProfile();

        repo = new ShopkeeperMainActivityRepo<>(this, app.getApplicationContext());

        activeFragment = new MutableLiveData<>(wholesalerList);
        inCartProducts = new MutableLiveData<>(repo.getCartProducts());

    }

    public LiveData<Fragment> getActiveFragment() {
        return activeFragment;
    }

    public void updateMenuItemFragment(int itemId) {
        switch (itemId) {
            case R.id.shopkeeper_main_menu_wholesaler:
                activeFragment.setValue(wholesalerList);
                break;
            case R.id.shopkeeper_main_menu_orders:
                activeFragment.setValue(orders);
                break;
            case R.id.shopkeeper_main_menu_cart:
                activeFragment.setValue(cart);
                break;
            case R.id.shopkeeper_main_menu_profile:
                activeFragment.setValue(profile);
                break;
        }
    }

    public LiveData<List<Product>> getInCartProducts() {
        return inCartProducts;
    }

    @Override
    public void onProductsLoadedListener() {
        inCartProducts.postValue(repo.getCartProducts());
    }
}
