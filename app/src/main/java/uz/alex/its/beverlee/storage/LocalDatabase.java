package uz.alex.its.beverlee.storage;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import uz.alex.its.beverlee.model.transaction.Card;
import uz.alex.its.beverlee.model.actor.Contact;
import uz.alex.its.beverlee.model.Country;
import uz.alex.its.beverlee.model.actor.User;
import uz.alex.its.beverlee.model.Push;
import uz.alex.its.beverlee.storage.converters.DateConverter;
import uz.alex.its.beverlee.storage.dao.CardDao;
import uz.alex.its.beverlee.storage.dao.ContactDao;
import uz.alex.its.beverlee.storage.dao.CountryDao;
import uz.alex.its.beverlee.storage.dao.PushDao;
import uz.alex.its.beverlee.utils.Constants;

@Database(entities = {
        Contact.class,
        Card.class,
        Country.class,
        Push.class}, version = 4, exportSchema = false)
@TypeConverters({ DateConverter.class })
public abstract class LocalDatabase extends RoomDatabase {
    /* Declare dao objects here */
    public abstract CountryDao countryDao();
    public abstract CardDao cardDao();
    public abstract ContactDao contactDao();
    public abstract PushDao pushDao();

    private static volatile LocalDatabase instance;

    public static LocalDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (LocalDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, Constants.DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                }
                            }).build();
                }
            }
        }
        return instance;
    }

    private static final String TAG = LocalDatabase.class.toString();
}
