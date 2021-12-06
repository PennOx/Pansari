package project.pansari.wholesalerPackage.WholesalerMainActivityPackage;

public interface WholesalerMainActivityDataLoadListener {

    void onProductsLoaded();

    void onWholesalerLoaded();

    void onPendingOrdersLoaded();

    void onCompletedOrdersLoaded();

    void onProfilePictureUpdated();
}
