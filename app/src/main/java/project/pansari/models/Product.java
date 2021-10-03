package project.pansari.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    @ColumnInfo(name = "unitQuantity")
    private String unitQuantity;

    @ColumnInfo(name = "packQuantity")
    private String packQuantity;

    @ColumnInfo(name = "sellerId")
    private String sellerId;

    public Product() {
        //for firebase;
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

    public String getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(String unitQuantity) {
        this.unitQuantity = unitQuantity;
    }

    public String getPackQuantity() {
        return packQuantity;
    }

    public void setPackQuantity(String packQuantity) {
        this.packQuantity = packQuantity;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }


}
