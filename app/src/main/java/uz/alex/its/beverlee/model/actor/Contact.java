package uz.alex.its.beverlee.model.actor;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "contact", indices = {@Index(value = "phone")})
public class Contact implements Serializable {
    @Expose
    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    private final long id;

    @Expose
    @SerializedName("name")
    @ColumnInfo(name = "name")
    private final String name;

    @Expose
    @SerializedName("phone")
    @ColumnInfo(name = "phone")
    private final String phone;

    public Contact(final long id, final String name, final String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @NonNull
    @Override
    public String toString() {
        return  "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'';
    }
}
