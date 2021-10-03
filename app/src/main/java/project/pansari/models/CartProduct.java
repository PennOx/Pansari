package project.pansari.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class CartProduct extends Product {

    @ColumnInfo(name = "inCart")
    private int inCart;

    public int getInCart() {
        return inCart;
    }

    public void setInCart(int inCart) {
        this.inCart = inCart;
    }
}
