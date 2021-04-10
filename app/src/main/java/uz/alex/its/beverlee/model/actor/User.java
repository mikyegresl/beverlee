package uz.alex.its.beverlee.model.actor;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import uz.alex.its.beverlee.model.Country;

public class User {
    @Expose
    @SerializedName("first_name")
    private final String firstName;

    @Expose
    @SerializedName("last_name")
    private final String lastName;

    private String middleName;

    @Expose
    @SerializedName("phone")
    private final String phone;

    @Expose
    @SerializedName("email")
    private final String email;

    @Expose
    @SerializedName("country_id")
    private final long countryId;

    @Expose
    @SerializedName("city")
    private final String city;

    private String position;

    private String address;

    public User(final String firstName,
                final String lastName,
                final String phone,
                final String email,
                final long countryId,
                final String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.countryId = countryId;
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public long getCountryId() {
        return countryId;
    }

    public String getCity() {
        return city;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getPosition() {
        return position;
    }

    @NonNull
    @Override
    public String toString() {
        return "CurrentUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", countryId=" + countryId +
                ", city='" + city + '\'' +
                '}';
    }
}
