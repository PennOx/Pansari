package project.pansari.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import java.util.List;

import project.pansari.Models.Product;
import project.pansari.Models.User;

@Dao
public interface ProductsDao {

    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Delete
    void remove(User user);
}
