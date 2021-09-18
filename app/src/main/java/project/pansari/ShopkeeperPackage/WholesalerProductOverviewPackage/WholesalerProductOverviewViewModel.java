package project.pansari.ShopkeeperPackage.WholesalerProductOverviewPackage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import project.pansari.Models.Product;
import project.pansari.Models.Wholesaler;

public class WholesalerProductOverviewViewModel extends ViewModel implements WholesalerProductOverviewDataLoadListener {

    private String wid;
    private WholesalerProductOverviewRepo<WholesalerProductOverviewViewModel> repo;
    private MutableLiveData<Wholesaler> wholesaler;
    private MutableLiveData<List<Product>> wholesalerProducts;

    public WholesalerProductOverviewViewModel(String wid) {

        this.wid = wid;
        this.repo = new WholesalerProductOverviewRepo<>(this, wid);
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
