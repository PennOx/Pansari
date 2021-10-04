package project.pansari.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import project.pansari.models.CartProduct;

@Dao
public interface CartDao {

    @Insert
    void addToCart(CartProduct product);

    @Update
    void updateCart(CartProduct product);

    @Delete
    void remove(CartProduct product);

    @Query("SELECT * FROM cartproduct ORDER BY sellerId")
    List<CartProduct> getAllProducts();

}
