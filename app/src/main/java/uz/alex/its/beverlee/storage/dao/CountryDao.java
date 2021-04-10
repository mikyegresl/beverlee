package uz.alex.its.beverlee.storage.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import uz.alex.its.beverlee.model.Country;

@Dao
public interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCountry(final Country country);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertCountryList(final List<Country> countryList);

    @Delete
    void deleteCountry(final Country country);

    @Delete
    void deleteCountryList(final List<Country> countryList);

    @Query("SELECT * FROM country ORDER BY title ASC")
    LiveData<List<Country>> selectAllCountries();

    @Query("SELECT * FROM country WHERE id == :countryId LIMIT 1")
    LiveData<Country> selectCountry(long countryId);
}
