package project.pansari.wholesalerPackage.WholesalerMainActivityPackage;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import project.pansari.R;
import project.pansari.models.OrderWrap;
import project.pansari.models.Product;
import project.pansari.models.Wholesaler;
import project.pansari.wholesalerPackage.WholesalerMainActivityPackage.WholesalerMainFragments.WholesalerCompletedOrders;
import project.pansari.wholesalerPackage.WholesalerMainActivityPackage.WholesalerMainFragments.WholesalerPendingOrders;
import project.pansari.wholesalerPackage.WholesalerMainActivityPackage.WholesalerMainFragments.WholesalerProducts;
import project.pansari.wholesalerPackage.WholesalerMainActivityPackage.WholesalerMainFragments.WholesalerProfile;

public class WholesalerMainActivityViewModel extends ViewModel implements WholesalerMainActivityDataLoadListener {

    private final WholesalerPendingOrders pendingOrdersFragment;
    private final WholesalerCompletedOrders completedOrdersFragment;
    private final WholesalerProducts productsFragment;
    private final WholesalerProfile profileFragment;
    private MutableLiveData<Fragment> activeFragment;

    private WholesalerMainActivityRepo<WholesalerMainActivityDataLoadListener> repo;

    private MutableLiveData<Wholesaler> wholesaler;
    private MutableLiveData<List<Product>> wholesalerProducts;
    private MutableLiveData<List<OrderWrap>> pendingOrders;
    private MutableLiveData<List<OrderWrap>> completedOrders;


    public WholesalerMainActivityViewModel() {
        pendingOrdersFragment = new WholesalerPendingOrders();
        completedOrdersFragment = new WholesalerCompletedOrders();
        productsFragment = new WholesalerProducts();
        profileFragment = new WholesalerProfile();
        activeFragment = new MutableLiveData<>(pendingOrdersFragment);

        repo = new WholesalerMainActivityRepo<>(this);

        wholesalerProducts = new MutableLiveData<>(repo.getProducts());
        wholesaler = new MutableLiveData<>(repo.getWholesaler());
        pendingOrders = new MutableLiveData<>(repo.getPendingOrders());
        completedOrders = new MutableLiveData<>(repo.getCompletedOrders());
    }

    public void updateMenuItemFragment(int itemId) {
        switch (itemId) {
            case R.id.wholesaler_main_menu_pending:
                activeFragment.setValue(pendingOrdersFragment);
                break;
            case R.id.wholesaler_main_menu_completed:
                activeFragment.setValue(completedOrdersFragment);
                break;
            case R.id.wholesaler_main_menu_products:
                activeFragment.setValue(productsFragment);
                break;
            case R.id.wholesaler_main_menu_profile:
                activeFragment.setValue(profileFragment);
                break;
        }
    }

    public LiveData<Fragment> getActiveFragment() {
        return activeFragment;
    }

    public LiveData<List<Product>> getWholesalerProducts() {
        return wholesalerProducts;
    }

    public LiveData<Wholesaler> getWholesaler() {
        return wholesaler;
    }

    public LiveData<List<OrderWrap>> getPendingOrders() {
        return pendingOrders;
    }

    public LiveData<List<OrderWrap>> getCompletedOrders() {
        return completedOrders;
    }

    @Override
    public void onProductsLoaded() {
        wholesalerProducts.postValue(repo.getProducts());
    }

    @Override
    public void onWholesalerLoaded() {
        wholesaler.postValue(repo.getWholesaler());
    }

    @Override
    public void onPendingOrdersLoaded() {
        pendingOrders.postValue(repo.getPendingOrders());
    }

    @Override
    public void onCompletedOrdersLoaded() {
        completedOrders.postValue(repo.getCompletedOrders());
    }

    public void refreshPendingOrders() {
        repo.loadOrders();
    }
}
