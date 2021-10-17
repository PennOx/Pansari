package project.pansari.shopkeeperPackage.shopkeeperOrderViewActivity;

public interface OrderViewDataLoadListener {

    void onOrderLoaded();

    void onOrderProductsLoaded();

    void onOrderCancelled();
}
