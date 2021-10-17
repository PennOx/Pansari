package project.pansari.wholesalerPackage.wholesalerOrderViewPackage;

public interface OrderViewDataLoadListener {

    void onOrderLoaded();

    void onProductsLoaded();

    void onOrderAccepted();

    void onOrderDeclined();
}
