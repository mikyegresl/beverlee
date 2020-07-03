package uz.alex.its.beverlee.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Contact implements Serializable {
    private String name;

    public Contact(@NonNull final String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull final String name) {
        this.name = name;
    }
}
