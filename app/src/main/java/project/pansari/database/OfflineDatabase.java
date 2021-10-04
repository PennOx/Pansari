package project.pansari.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import project.pansari.database.dao.CartDao;
import project.pansari.models.CartProduct;

@Database(entities = {CartProduct.class}, version = 1)
public abstract class OfflineDatabase extends RoomDatabase {
    private static OfflineDatabase instance;

    public static OfflineDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    OfflineDatabase.class, "pansari-data").build();
        }

        return instance;
    }

    public abstract CartDao getCartDao();
}
