package uz.alex.its.beverlee.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.List;

import uz.alex.its.beverlee.model.Country;
import uz.alex.its.beverlee.storage.LocalDatabase;
import uz.alex.its.beverlee.storage.dao.CountryDao;
import uz.alex.its.beverlee.worker.GetCountriesWorker;

public class CountryRepository {
    private final Context context;
    private final CountryDao countryDao;

    public CountryRepository(final Context context) {
        this.context = context;
        final LocalDatabase database = LocalDatabase.getInstance(context);
        this.countryDao = database.countryDao();
    }

    void insertCountry(final Country country) {
        new Thread(() -> countryDao.insertCountry(country)).start();
    }

    void insertCountryList(final List<Country> countryList) {
        new Thread(() -> countryDao.insertCountryList(countryList)).start();
    }

    void deleteCountry(final Country country) {
        new Thread(() -> countryDao.deleteCountry(country)).start();
    }

    void deleteCountryList(final List<Country> countryList) {
        new Thread(() -> countryDao.deleteCountryList(countryList));
    }

    public LiveData<List<Country>> getCountryList() {
        return LocalDatabase.getInstance(context).countryDao().selectAllCountries();
    }

    public void fetchCountryList() {
        final Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresDeviceIdle(false)
                .setRequiresStorageNotLow(false)
                .setRequiresCharging(false)
                .setRequiresBatteryNotLow(false)
                .build();
        final OneTimeWorkRequest getCountryListRequest = new OneTimeWorkRequest.Builder(GetCountriesWorker.class)
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance(context).enqueue(getCountryListRequest);
    }

    public LiveData<Country> getCountry(final long countryId) {
        return countryDao.selectCountry(countryId);
    }
}
