package project.pansari.WholesalerPackage.WholesalerMainActivityPackage;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import project.pansari.Models.Product;
import project.pansari.Models.Wholesaler;
import project.pansari.R;
import project.pansari.ShopkeeperPackage.WholesalerProductOverviewPackage.WholesalerProductOverviewDataLoadListener;
import project.pansari.WholesalerPackage.WholesalerMainActivityPackage.WholesalerMainFragments.WholesalerCompletedOrders;
import project.pansari.WholesalerPackage.WholesalerMainActivityPackage.WholesalerMainFragments.WholesalerPendingOrders;
import project.pansari.WholesalerPackage.WholesalerMainActivityPackage.WholesalerMainFragments.WholesalerProducts;
import project.pansari.WholesalerPackage.WholesalerMainActivityPackage.WholesalerMainFragments.WholesalerProfile;

public class WholesalerMainActivityViewModel extends ViewModel implements WholesalerProductOverviewDataLoadListener {

    private final WholesalerPendingOrders pendingOrders;
    private final WholesalerCompletedOrders completedOrders;
    private final WholesalerProducts products;
    private final WholesalerProfile profile;
    private MutableLiveData<Fragment> activeFragment;
    private WholesalerMainActivityRepo repo;
    private MutableLiveData<Wholesaler> wholesaler;
    private MutableLiveData<List<Product>> wholesalerProducts;

    public WholesalerMainActivityViewModel() {
        pendingOrders = new WholesalerPendingOrders();
        completedOrders = new WholesalerCompletedOrders();
        products = new WholesalerProducts(this);
        profile = new WholesalerProfile();
        activeFragment = new MutableLiveData<>(pendingOrders);

        repo = new WholesalerMainActivityRepo(this);

        wholesalerProducts = repo.getProducts();
        wholesaler = repo.getWholesaler();
    }

    public void updateMenuItemFragment(int itemId) {
        switch (itemId) {
            case R.id.wholesaler_main_menu_pending:
                activeFragment.setValue(pendingOrders);
                break;
            case R.id.wholesaler_main_menu_completed:
                activeFragment.setValue(completedOrders);
                break;
            case R.id.wholesaler_main_menu_products:
                activeFragment.setValue(products);
                break;
            case R.id.wholesaler_main_menu_profile:
                activeFragment.setValue(profile);
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

    @Override
    public void onProductsLoaded() {
        wholesalerProducts.setValue((List<Product>) repo.getProducts().getValue());
    }

    @Override
    public void onWholesalerLoaded() {
        wholesaler.setValue((Wholesaler) repo.getWholesaler().getValue());
    }
}
