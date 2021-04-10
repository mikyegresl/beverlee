package uz.alex.its.beverlee.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import uz.alex.its.beverlee.model.transaction.Card;
import uz.alex.its.beverlee.storage.LocalDatabase;
import uz.alex.its.beverlee.storage.dao.CardDao;

public class CardRepository {
    private final CardDao cardDao;

    public CardRepository(final Context context) {
        final LocalDatabase database = LocalDatabase.getInstance(context);
        this.cardDao = database.cardDao();
    }

    void insertCard(final Card card) {
        new Thread(() -> cardDao.insertCard(card)).start();
    }

    void insertCardList(final List<Card> cardList) {
        new Thread(() -> cardDao.insertCardList(cardList)).start();
    }

    void deleteCard(final Card card) {
        new Thread(() -> cardDao.deleteCard(card)).start();
    }

    void deleteCardList(final List<Card> cardList) {
        new Thread(() -> cardDao.deleteCardList(cardList));
    }

    LiveData<List<Card>> selectAllCards() {
        return cardDao.selectAllCards();
    }

    LiveData<Card> selectCardByUsername(final String username) {
        return cardDao.selectCardByUsername(username);
    }

    LiveData<List<Card>> selectCardsByType(final int type) {
        return cardDao.selectCardsByType(type);
    }
}
