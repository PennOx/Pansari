package project.pansari.ShopkeeperPackage.WholesalerProductOverviewPackage;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import project.pansari.Models.Product;
import project.pansari.Models.Wholesaler;

public class WholesalerProductOverviewViewModel extends AndroidViewModel implements WholesalerProductOverviewDataLoadListener {

    private WholesalerProductOverviewRepo<WholesalerProductOverviewViewModel> repo;
    private MutableLiveData<Wholesaler> wholesaler;
    private MutableLiveData<List<Product>> wholesalerProducts;

    public WholesalerProductOverviewViewModel(Application app, String wid) {
        super(app);

        this.repo = new WholesalerProductOverviewRepo<>(app, this, wid);
        wholesalerProducts = repo.getProducts();
        wholesaler = repo.getWholesaler();
    }

    public LiveData<Wholesaler> getCurrentWholesaler() {
        return wholesaler;
    }

    public LiveData<List<Product>> getWholesalerProducts() {
        return wholesalerProducts;
    }

    public void addToCart(String pid) {
        repo.addProductToCart(pid);
    }

    @Override
    public void onProductsLoaded() {
        wholesalerProducts.setValue(repo.getProducts().getValue());
    }

    @Override
    public void onWholesalerLoaded() {
        wholesaler.setValue((Wholesaler) repo.getWholesaler().getValue());
    }
}
