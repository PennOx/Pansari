package project.pansari.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {

    @PrimaryKey
    @ColumnInfo(name = "pid")
    String pid;

    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name = "brand")
    String brand;
    @ColumnInfo(name = "description")
    String description;
    @ColumnInfo(name = "image")
    String image;
    @ColumnInfo(name = "quantity")
    String quantity;

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
}