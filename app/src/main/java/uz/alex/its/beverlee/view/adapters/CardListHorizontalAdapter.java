package uz.alex.its.beverlee.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.Card;

public class CardListHorizontalAdapter extends RecyclerView.Adapter<CardListHorizontalAdapter.CardHorizontalViewHolder> {
    private List<Card> cardList;
    private final Context context;

    public CardListHorizontalAdapter(@NonNull final Context context) {
        this.context = context;
    }

    public void setCardList(@NonNull final List<Card> cardList) {
        this.cardList = cardList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View root = LayoutInflater.from(context).inflate(R.layout.card_horizontal_view_holder, parent, false);
        return new CardListHorizontalAdapter.CardHorizontalViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHorizontalViewHolder holder, int position) {
        if (cardList.get(position).isMaster()) {
            holder.cardLogoImageView.setImageResource(R.drawable.ic_master_card_logo);
        }
        else {
            holder.cardLogoImageView.setImageResource(R.drawable.ic_visa_card_logo);
        }
        holder.cardNoTextView.setText(cardList.get(position).getCardNo());
        holder.expDateTextView.setText(cardList.get(position).getCardExpDate());
        holder.cardUsernameTextView.setText(cardList.get(position).getCardUsername());
    }

    @Override
    public int getItemCount() {
        return cardList == null ? 0 : cardList.size();
    }

    static class CardHorizontalViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout creditCardLayout;
        public ConstraintLayout creditCard;
        public TextView cardTextView;
        public ImageView cardChipImageView;
        public ImageView cardLogoImageView;
        public TextView cardNoTextView;
        public TextView expDateTextView;
        public TextView cardUsernameTextView;
        public ImageView checkImageView;

        public CardHorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            creditCardLayout = itemView.findViewById(R.id.credit_card_layout);
            creditCard = itemView.findViewById(R.id.credit_card);
            cardTextView = itemView.findViewById(R.id.card_text_view);
            cardChipImageView = itemView.findViewById(R.id.card_chip);
            cardLogoImageView = itemView.findViewById(R.id.card_logo);
            cardNoTextView = itemView.findViewById(R.id.card_no);
            expDateTextView = itemView.findViewById(R.id.card_exp_date);
            cardUsernameTextView = itemView.findViewById(R.id.card_username);
            checkImageView = itemView.findViewById(R.id.check_image_view);
        }
    }

    private static final String TAG = CardListHorizontalAdapter.class.toString();
}
