package uz.alex.its.beverlee.view.interfaces;

import uz.alex.its.beverlee.model.transaction.Card;
import uz.alex.its.beverlee.view.adapters.CardListVerticalAdapter;

public interface CardCallback {
    void onCardSelected(final Card card, final CardListVerticalAdapter.CardVerticalViewHolder holder);
    void onCardDeselected(final Card card);
}