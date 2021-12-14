package project.pansari.wholesalerPackage.WholesalerProductOverviewPackage;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import project.pansari.auth.Auth;
import project.pansari.models.Product;

public class WholesalerProductOverviewVM extends ViewModel implements WholesalerProductOverviewDataListener {

    private final String pid;
    private final MutableLiveData<Boolean> newProduct;
    private final WholesalerProductOverviewRepo repo;
    private final MutableLiveData<Product> product;
    private Uri imageUri;

    WholesalerProductOverviewVM(String pid) {
        product = new MutableLiveData<>();
        repo = new WholesalerProductOverviewRepo(this);
        newProduct = new MutableLiveData<>();
        if (pid != null) {
            this.pid = pid;
            newProduct.setValue(false);
            loadProduct(pid);
        } else {
            this.pid = "PD" + System.currentTimeMillis();
            newProduct.setValue(true);
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
        p.setPid(pid);
        p.setSellerId(Auth.getCurrentUserUid());
        repo.addProduct(p, imageUri);
    }

    public void setNewImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
