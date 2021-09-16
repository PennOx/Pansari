package project.pansari.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import project.pansari.Database.DAO.ProductsDao;
import project.pansari.Models.Product;

@Database(entities = {Product.class}, version = 1)
public abstract class OfflineDatabase extends RoomDatabase {
    private static OfflineDatabase instance;
    public ProductsDao productsDao;

    public OfflineDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    OfflineDatabase.class, "pansari-data").build();
        }

        return instance;
    }
}
