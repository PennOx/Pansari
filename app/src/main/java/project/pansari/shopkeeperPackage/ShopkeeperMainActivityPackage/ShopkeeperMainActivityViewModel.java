package project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage;

import android.app.Application;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import project.pansari.R;
import project.pansari.models.CartItem;
import project.pansari.models.Wholesaler;
import project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments.ShopkeeperCart;
import project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments.ShopkeeperOrders;
import project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments.ShopkeeperProfile;
import project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments.ShopkeeperWholesalerList;

public class ShopkeeperMainActivityViewModel extends AndroidViewModel implements ShopkeeperMainActivityDataLoadListener {

    private final ShopkeeperWholesalerList wholesalerList;
    private final ShopkeeperOrders orders;
    private final ShopkeeperCart cart;
    private final ShopkeeperProfile profile;
    private MutableLiveData<Fragment> activeFragment;
    private ShopkeeperMainActivityRepo<ShopkeeperMainActivityViewModel> repo;

    //Cart Fragment
    private MutableLiveData<List<CartItem>> inCartProducts;

    //Wholesaler List Fragment
    private MutableLiveData<List<Wholesaler>> availableWholesalers;

    private MutableLiveData<List<Wholesaler>> favoriteWholesalers;


    public ShopkeeperMainActivityViewModel(Application app) {
        super(app);

        wholesalerList = new ShopkeeperWholesalerList(this);
        orders = new ShopkeeperOrders();
        cart = new ShopkeeperCart(this);
        profile = new ShopkeeperProfile();

        repo = new ShopkeeperMainActivityRepo<>(this, app.getApplicationContext());

        activeFragment = new MutableLiveData<>(wholesalerList);
        inCartProducts = new MutableLiveData<>(repo.getCartProducts());
        availableWholesalers = new MutableLiveData<>(repo.getAvailableWholesalers());
        favoriteWholesalers = new MutableLiveData<>(repo.getFavoriteWholesalers());

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

    public LiveData<Fragment> getActiveFragment() {
        return activeFragment;
    }

    public LiveData<List<CartItem>> getInCartProducts() {
        return inCartProducts;
    }

    public LiveData<List<Wholesaler>> getAvailableWholesalers() {
        return availableWholesalers;
    }

    public LiveData<List<Wholesaler>> getFavoriteWholesalers() {
        return favoriteWholesalers;
    }

    @Override
    public void onProductsLoaded() {
        inCartProducts.postValue(repo.getCartProducts());
    }

    @Override
    public void onAvailableWholesalersLoaded() {
        availableWholesalers.postValue(repo.getAvailableWholesalers());
    }

    @Override
    public void onFavoriteWholesalersLoaded() {
        favoriteWholesalers.postValue(repo.getFavoriteWholesalers());
    }

    public void refreshCartProducts() {
        repo.loadCartProducts();
    }

}
