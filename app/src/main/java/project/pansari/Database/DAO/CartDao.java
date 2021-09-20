package project.pansari.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import project.pansari.Models.Product;

@Dao
public interface CartDao {

    @Insert
    void addToCart(Product product);

    @Update
    void updateCart(Product product);

    @Delete
    void remove(Product product);

    @Query("SELECT * FROM product ORDER BY sellerId")
    List<Product> getAllProducts();

}
