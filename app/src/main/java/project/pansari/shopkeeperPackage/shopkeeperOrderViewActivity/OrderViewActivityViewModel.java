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
    private MutableLiveData<Boolean> orderCancelFlag;

    public OrderViewActivityViewModel(String oid) {
        this.repo = new OrderViewActivityRepo(oid, this);

        this.orderWrap = new MutableLiveData<>(repo.getOrderWrap());
        this.orderProducts = new MutableLiveData<>(repo.getOrderProducts());
        this.orderCancelFlag = new MutableLiveData<>(false);
    }

    public LiveData<OrderWrap> getOrderWrap() {
        return orderWrap;
    }

    public LiveData<List<CartProduct>> getOrderProducts() {
        return orderProducts;
    }

    public LiveData<Boolean> getOrderCancelFlag() {
        return orderCancelFlag;
    }

    @Override
    public void onOrderLoaded() {
        orderWrap.postValue(repo.getOrderWrap());
    }

    @Override
    public void onOrderProductsLoaded() {
        orderProducts.postValue(repo.getOrderProducts());
    }

    @Override
    public void onOrderCancelled() {
        orderCancelFlag.postValue(true);
    }

    public void cancelOrder() {
        repo.cancelOrder();
    }
}
