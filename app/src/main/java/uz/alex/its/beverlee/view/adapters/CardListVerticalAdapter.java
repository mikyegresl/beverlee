package uz.alex.its.beverlee.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.Card;
import uz.alex.its.beverlee.view.interfaces.CardCallback;

public class CardListVerticalAdapter extends RecyclerView.Adapter<CardListVerticalAdapter.CardVerticalViewHolder> {
    private final Context context;
    private List<Card> cardList;
    private CardCallback cardCallback;

    public CardListVerticalAdapter(@NonNull final Context context, @NonNull final CardCallback cardCallback) {
        this.context = context;
        this.cardCallback = cardCallback;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardVerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View root = LayoutInflater.from(context).inflate(R.layout.card_vertical_view_holder, parent, false);
        return new CardVerticalViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CardVerticalViewHolder holder, int position) {
        if (cardList.get(position).isMaster()) {
            holder.cardLogoImageView.setImageResource(R.drawable.ic_master_card_logo);
        }
        else {
            holder.cardLogoImageView.setImageResource(R.drawable.ic_visa_card_logo);
        }
        holder.cardNoTextView.setText(cardList.get(position).getCardNo());
        holder.expDateTextView.setText(cardList.get(position).getCardExpDate());
        holder.cardUsernameTextView.setText(cardList.get(position).getCardUsername());
        holder.checkImageView.setVisibility(View.INVISIBLE);

        holder.bind(cardList.get(position), cardCallback);
    }

    @Override
    public int getItemCount() {
        return cardList == null ? 0 : cardList.size();
    }

    public static class CardVerticalViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout cardLayoutWhole;
        public View creditCard;
        public ImageView cardChipImageView;
        public ImageView cardLogoImageView;
        public ImageView checkImageView;
        public TextView cardTextView;
        public TextView cardNoTextView;
        public TextView expDateTextView;
        public TextView cardUsernameTextView;

        public CardVerticalViewHolder(@NonNull View itemView) {
            super(itemView);
            cardLayoutWhole = itemView.findViewById(R.id.card_view_holder);
            creditCard = itemView.findViewById(R.id.credit_card_layout);
            cardChipImageView = itemView.findViewById(R.id.card_chip);
            cardLogoImageView = itemView.findViewById(R.id.card_logo);
            checkImageView = itemView.findViewById(R.id.check_image_view);
            cardTextView = itemView.findViewById(R.id.card_text_view);
            cardNoTextView = itemView.findViewById(R.id.card_no);
            expDateTextView = itemView.findViewById(R.id.card_exp_date);
            cardUsernameTextView = itemView.findViewById(R.id.card_username);
        }

        void bind(final Card card, final CardCallback cardCallback) {
            creditCard.setOnClickListener(v -> {
                cardCallback.onCardSelected(card, this);
            });

            cardLayoutWhole.setOnClickListener(v -> {
                cardCallback.onCardDeselected(card);
            });
        }
    }

    private static final String TAG = CardListVerticalAdapter.class.toString();
}
