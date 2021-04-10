package uz.alex.its.beverlee.storage.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import uz.alex.its.beverlee.model.transaction.Card;

@Dao
public interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCard(final Card card);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertCardList(final List<Card> cardList);

    @Delete
    void deleteCard(final Card card);

    @Delete
    void deleteCardList(final List<Card> cardList);

    @Query("SELECT * FROM card ORDER BY number DESC")
    LiveData<List<Card>> selectAllCards();

    @Query("SELECT * FROM card WHERE username LIKE :username")
    LiveData<Card> selectCardByUsername(final String username);

    @Query("SELECT * FROM card WHERE type == :type")
    LiveData<List<Card>> selectCardsByType(final int type);
}
