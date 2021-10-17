package project.pansari.wholesalerPackage.wholesalerOrderViewPackage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import project.pansari.models.CartProduct;
import project.pansari.models.OrderWrap;

public class OrderViewViewModel extends ViewModel implements OrderViewDataLoadListener {

    private OrderViewRepo repo;

    private MutableLiveData<OrderWrap> orderWrap;
    private MutableLiveData<List<CartProduct>> orderProducts;
    private MutableLiveData<Boolean> orderResponseFlag;

    public OrderViewViewModel(String oid) {
        repo = new OrderViewRepo(oid, this);

        orderWrap = new MutableLiveData<>(repo.getOrder());
        orderProducts = new MutableLiveData<>(repo.getProducts());
        orderResponseFlag = new MutableLiveData<>(false);
    }

    public LiveData<OrderWrap> getOrder() {
        return orderWrap;
    }

    public LiveData<List<CartProduct>> getOrderProducts() {
        return orderProducts;
    }

    public LiveData<Boolean> getOrderResponseFlag() {
        return orderResponseFlag;
    }


    @Override
    public void onOrderLoaded() {
        orderWrap.postValue(repo.getOrder());
    }

    @Override
    public void onProductsLoaded() {
        orderProducts.postValue(repo.getProducts());
    }

    @Override
    public void onOrderAccepted() {
        orderResponseFlag.postValue(true);
    }

    @Override
    public void onOrderDeclined() {
        orderResponseFlag.postValue(true);
    }

    public void acceptOrder() {
        //TODO
        repo.acceptOrder();
    }

    public void declineOrder() {
        //TODO
        repo.declineOrder();
    }
}
