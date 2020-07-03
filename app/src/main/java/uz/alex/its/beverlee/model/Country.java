package uz.alex.its.beverlee.model;

import androidx.annotation.NonNull;

public class Country {
    private final String country;
    private final int resourceId;

    public Country(String country, int resourceId) {
        this.country = country;
        this.resourceId = resourceId;
    }

    public String getCountry() {
        return country;
    }

    public int getResourceId() {
        return resourceId;
    }

    @NonNull
    @Override
    public String toString() {
        return "Country{" +
                "country='" + country + '\'' +
                ", resourceId=" + resourceId +
                '}';
    }
}
