package project.pansari.ShopkeeperPackage.ShopkeeperMainActivityPackage;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import project.pansari.R;
import project.pansari.ShopkeeperPackage.ShopkeeperMainFragments.ShopkeeperCart;
import project.pansari.ShopkeeperPackage.ShopkeeperMainFragments.ShopkeeperOrders;
import project.pansari.ShopkeeperPackage.ShopkeeperMainFragments.ShopkeeperProfile;
import project.pansari.ShopkeeperPackage.ShopkeeperMainFragments.ShopkeeperWholesalerList;

public class ShopkeeperMainActivityViewModel extends ViewModel {

    private final ShopkeeperWholesalerList wholesalerList;
    private final ShopkeeperOrders orders;
    private final ShopkeeperCart cart;
    private final ShopkeeperProfile profile;

    private MutableLiveData<Fragment> activeFragment;

    public ShopkeeperMainActivityViewModel() {
        wholesalerList = new ShopkeeperWholesalerList();
        orders = new ShopkeeperOrders();
        cart = new ShopkeeperCart();
        profile = new ShopkeeperProfile();

        activeFragment = new MutableLiveData<>(wholesalerList);
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
}
