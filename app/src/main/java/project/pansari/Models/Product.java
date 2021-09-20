package project.pansari.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

@Entity
public class Product {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "pid")
    private String pid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "brand")
    private String brand;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "quantity")
    private String quantity;

    @Exclude
    @ColumnInfo(name = "inCart")
    private int inCart;

    @ColumnInfo(name = "sellerId")
    private String sellerId;

    public Product() {
        //for firebase;
    }

    public Product(String name, String brand, String description, String image, String quantity) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Exclude
    public int getInCart() {
        return inCart;
    }

    public void setInCart(int inCart) {
        this.inCart = inCart;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
