package uz.alex.its.beverlee.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import retrofit2.Response;
import uz.alex.its.beverlee.model.Country;
import uz.alex.its.beverlee.model.requestParams.PinParams;
import uz.alex.its.beverlee.repository.AuthRepository;
import uz.alex.its.beverlee.repository.CountryRepository;

public class AuthViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final CountryRepository countryRepository;

    public AuthViewModel(final Context context) {
        this.authRepository = new AuthRepository(context);
        this.countryRepository = new CountryRepository(context);
    }

    public UUID register(final String firstName,
                         final String lastName,
                         final String phone,
                         final String email,
                         final long countryId,
                         final String city,
                         final String password,
                         final String passwordConfirmation) {
        return authRepository.register(firstName, lastName, phone, email, countryId, city, password, passwordConfirmation);
    }

    public UUID login(final String phone, final String password) {
        return authRepository.login(phone, password);
    }

    public UUID verifyPhoneBySms() {
        return authRepository.verifyPhoneBySms();
    }

    public UUID verifyPhoneByCall() {
        return authRepository.verifyPhoneByCall();
    }

    public UUID submitVerification(final String code) {
        return authRepository.submitVerification(code);
    }

    public UUID checkPinAssigned() {
        return authRepository.checkPinAssigned();
    }

    public void fetchCountryList() {
        countryRepository.fetchCountryList();
    }

    public LiveData<List<Country>> getCountryList() {
        return countryRepository.getCountryList();
    }

    public LiveData<Country> getCountry(final long countryId) {
        return countryRepository.getCountry(countryId);
    }
}