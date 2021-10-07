package project.pansari.models;

public class CartProduct extends Product {

    private int inCartQuantity;

    public int getInCartQuantity() {
        return inCartQuantity;
    }

    public void setInCartQuantity(int inCartQuantity) {
        this.inCartQuantity = inCartQuantity;
    }
}
