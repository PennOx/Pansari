package project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage;

import android.app.Application;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import project.pansari.R;
import project.pansari.models.CartItem;
import project.pansari.models.OrderWrap;
import project.pansari.models.Wholesaler;
import project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments.ShopkeeperCart;
import project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments.ShopkeeperOrders;
import project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments.ShopkeeperProfile;
import project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments.ShopkeeperWholesalerList;

public class ShopkeeperMainActivityViewModel extends AndroidViewModel implements ShopkeeperMainActivityDataLoadListener {

    private final ShopkeeperWholesalerList wholesalerListFragment;
    private final ShopkeeperOrders ordersFragment;
    private final ShopkeeperCart cartFragment;
    private final ShopkeeperProfile profileFragment;
    private MutableLiveData<Fragment> activeFragment;
    private ShopkeeperMainActivityRepo<ShopkeeperMainActivityViewModel> repo;
    private MutableLiveData<Boolean> isLoading;

    //Cart Fragment
    private MutableLiveData<List<CartItem>> inCartProducts;

    //Wholesaler List Fragment
    private MutableLiveData<List<Wholesaler>> availableWholesalers;
    private MutableLiveData<List<Wholesaler>> favoriteWholesalers;

    //Orders Fragment
    private MutableLiveData<List<OrderWrap>> orders;


    public ShopkeeperMainActivityViewModel(Application app) {
        super(app);

        wholesalerListFragment = new ShopkeeperWholesalerList();
        ordersFragment = new ShopkeeperOrders();
        cartFragment = new ShopkeeperCart();
        profileFragment = new ShopkeeperProfile();

        isLoading = new MutableLiveData<>(false);

        repo = new ShopkeeperMainActivityRepo<>(this, app.getApplicationContext());

        activeFragment = new MutableLiveData<>(wholesalerListFragment);
        inCartProducts = new MutableLiveData<>(repo.getCartProducts());
        availableWholesalers = new MutableLiveData<>(repo.getAvailableWholesalers());
        favoriteWholesalers = new MutableLiveData<>(repo.getFavoriteWholesalers());
        orders = new MutableLiveData<>(repo.getOrders());

    }

    public void updateMenuItemFragment(int itemId) {
        switch (itemId) {
            case R.id.shopkeeper_main_menu_wholesaler:
                activeFragment.setValue(wholesalerListFragment);
                break;
            case R.id.shopkeeper_main_menu_orders:
                activeFragment.setValue(ordersFragment);
                break;
            case R.id.shopkeeper_main_menu_cart:
                activeFragment.setValue(cartFragment);
                break;
            case R.id.shopkeeper_main_menu_profile:
                activeFragment.setValue(profileFragment);
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

    public LiveData<List<OrderWrap>> getOrders() {
        return orders;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
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

    public void placeOrder() {
        isLoading.setValue(true);

        repo.placeOrder();
    }

    @Override
    public void onOrderPlaced() {
        isLoading.setValue(false);
    }

    @Override
    public void onOrdersLoaded() {
        orders.postValue(repo.getOrders());
    }

    public void refreshOrders() {
        repo.loadOrders();
    }

    public void refreshAvailableWholesalers() {
        repo.loadAvailableWholesalers();
    }

    public void refreshFavoriteWholesalers() {
        repo.loadFavoriteWholesalers();
    }
}
