package project.pansari.WholesalerPackage.WholesalerAddProductPackage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import project.pansari.Models.Product;

public class WholesalerAddProductViewModel extends ViewModel implements WholesalerAddProductDataLoadListener {

    private String pid;
    private MutableLiveData<Boolean> newProduct;
    private WholesalerAddProductRepo repo;
    private MutableLiveData<Product> product;

    WholesalerAddProductViewModel(String pid) {
        this.pid = pid;
        product = new MutableLiveData<>();
        repo = new WholesalerAddProductRepo(this);
        newProduct = new MutableLiveData<>();
        if (pid != null) {
            newProduct.setValue(true);
            loadProduct(pid);
        } else {
            newProduct.setValue(false);
        }
    }

    public LiveData<Boolean> isNewProduct() {
        return newProduct;
    }

    private void loadProduct(String pid) {
        repo.loadProduct(pid);
    }

    public String getPid() {
        return pid;
    }

    public LiveData<Product> getProduct() {
        return product;
    }

    @Override
    public void onProductLoaded() {
        product.setValue(repo.getProduct());
    }

    public void editProduct(Product p) {

        p.setPid(pid);
        repo.editProduct(p);
    }

    public void addProduct(Product p) {

        p.setPid(System.currentTimeMillis() + "");
        repo.addProduct(p);
    }
}
