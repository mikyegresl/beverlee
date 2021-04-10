package uz.alex.its.beverlee.model.transaction;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "card", indices = {@Index(value = "username")})
public class Card {
    @Expose
    @SerializedName("number")
    @PrimaryKey
    @ColumnInfo(name = "number")
    @NonNull
    private final String number;

    @Expose
    @SerializedName("username")
    @ColumnInfo(name = "username")
    private final String username;

    //cardType = {1 -> master \ 2 -> visa;}
    @Expose
    @SerializedName("type")
    @ColumnInfo(name = "type")
    private final int type;

    @Expose
    @SerializedName("exp_date")
    @ColumnInfo(name = "exp_date")
    private final String expDate;

    public Card(final int type, @NonNull final String number, final String expDate, final String username) {
        this.type = type;
        this.number = number;
        this.expDate = expDate;
        this.username = username;
    }

    public int getType() {
        return type;
    }

    @NonNull
    public String getNumber() {
        return number;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getUsername() {
        return username;
    }
}
