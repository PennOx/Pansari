package project.pansari.shopkeeperPackage.shopkeeperOrderViewActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import project.pansari.models.CartProduct;
import project.pansari.models.OrderWrap;

public class OrderViewActivityViewModel extends ViewModel implements OrderViewDataLoadListener {

    private OrderViewActivityRepo repo;

    private MutableLiveData<OrderWrap> orderWrap;
    private MutableLiveData<List<CartProduct>> orderProducts;

    public OrderViewActivityViewModel(String oid) {
        this.repo = new OrderViewActivityRepo(oid, this);

        this.orderWrap = new MutableLiveData<>(repo.getOrderWrap());
        this.orderProducts = new MutableLiveData<>(repo.getOrderProducts());
    }

    public LiveData<OrderWrap> getOrderWrap() {
        return orderWrap;
    }

    public LiveData<List<CartProduct>> getOrderProducts() {
        return orderProducts;
    }

    @Override
    public void onOrderLoaded() {
        orderWrap.postValue(repo.getOrderWrap());
    }

    @Override
    public void onOrderProductsLoaded() {
        orderProducts.postValue(repo.getOrderProducts());
    }
}
