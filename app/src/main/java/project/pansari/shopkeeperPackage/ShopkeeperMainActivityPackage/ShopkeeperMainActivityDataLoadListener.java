package project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage;

public interface ShopkeeperMainActivityDataLoadListener {

    void onProductsLoaded();

    void onAvailableWholesalersLoaded();

    void onFavoriteWholesalersLoaded();

    void onOrderPlaced();

    void onOrdersLoaded();
}
