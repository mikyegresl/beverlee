package uz.alex.its.beverlee.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.transaction.Card;
import uz.alex.its.beverlee.view.activities.OperationsContainerActivity;
import uz.alex.its.beverlee.view.adapters.CardListVerticalAdapter;
import uz.alex.its.beverlee.view.interfaces.CardCallback;

public class MyCardsFragment extends Fragment implements CardCallback {
    private static final String TAG = MyCardsFragment.class.toString();
    private Context context;

    private final List<Card> cardList = new ArrayList<>();

    private ImageView plusImageView;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fab;

    private CardListVerticalAdapter.CardVerticalViewHolder selectedHolder;
    private boolean bottomSheetHidden = true;
    private boolean cardSelected = false;

    private LinearLayout bottomSheet;
    private TextView bottomSheetDebit;
    private TextView bottomSheetEdit;
    private TextView bottomSheetDelete;
    private BottomSheetBehavior sheetBehavior;

    public MyCardsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            context = getActivity().getApplicationContext();
        }

        cardList.add(new Card(1, "1234 5678 9012 3456", "01/22", "Антон Камушкин"));
        cardList.add(new Card(2, "8954 2345 4659 5048", "03/21", "Роджер Федерер"));
        cardList.add(new Card(2, "1293 1214 9898 1238", "12/20", "Сидни Кросби"));
        cardList.add(new Card(1, "9886 0136 9081 9012", "11/21", "Джон Джонс"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_my_cards, container, false);
        final RecyclerView cardListRecyclerView = root.findViewById(R.id.card_list_recycler_view);
        final CardListVerticalAdapter cardAdapter = new CardListVerticalAdapter(context, this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        plusImageView = root.findViewById(R.id.plus_image_view);

        cardAdapter.setCardList(cardList);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        cardListRecyclerView.setHasFixedSize(true);
        cardListRecyclerView.setLayoutManager(layoutManager);
        cardListRecyclerView.setAdapter(cardAdapter);
        cardAdapter.setCardList(cardList);

        if (getActivity() != null) {
            fab = getActivity().findViewById(R.id.floating_btn);
            bottomNavigationView = getActivity().findViewById(R.id.bottom_nav);

            bottomSheet = getActivity().findViewById(R.id.bottom_sheet_cards);
            bottomSheetDebit = getActivity().findViewById(R.id.debit_option);
            bottomSheetEdit = getActivity().findViewById(R.id.edit_option);
            bottomSheetDelete = getActivity().findViewById(R.id.delete_option);

            sheetBehavior = BottomSheetBehavior.from(bottomSheet);
            sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        plusImageView.setOnClickListener(v -> {
            final Intent addCardIntent = new Intent(getActivity(), OperationsContainerActivity.class);
            addCardIntent.putExtra(OperationsContainerActivity.FRAGMENT_FLAG, OperationsContainerActivity.ADD_CARD_FRAGMENT);
            startActivity(addCardIntent);
        });

        fab.setOnClickListener(v -> {
            final Intent addCardIntent = new Intent(getActivity(), OperationsContainerActivity.class);
            addCardIntent.putExtra(OperationsContainerActivity.FRAGMENT_FLAG, OperationsContainerActivity.TRANSFER_FRAGMENT);
            startActivity(addCardIntent);
        });

        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN: {
                        break;
                    }
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        bottomNavigationView.setVisibility(View.INVISIBLE);
                        fab.setVisibility(View.INVISIBLE);
                        break;
                    }
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        Log.i(TAG, "onStateChanged: STATE_COLLAPSED");
                        break;
                    }
                    case BottomSheetBehavior.STATE_DRAGGING: {
                        Log.i(TAG, "onStateChanged: STATE_DRAGGING");
                        break;
                    }
                    case BottomSheetBehavior.STATE_SETTLING: {
                        if (bottomSheetHidden) {
                            bottomNavigationView.setVisibility(View.VISIBLE);
                            fab.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    case BottomSheetBehavior.STATE_HALF_EXPANDED: {
                        Log.i(TAG, "onStateChanged: STATE_HALF_EXPANDED");
                        break;
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        bottomSheetDebit.setOnClickListener(v -> {
            final Intent debitIntent = new Intent(getActivity(), OperationsContainerActivity.class);
            debitIntent.putExtra(OperationsContainerActivity.FRAGMENT_FLAG, OperationsContainerActivity.DEBIT_FRAGMENT);
            startActivity(debitIntent);
        });

        bottomSheetEdit.setOnClickListener(v -> {
            final Intent editCardIntent = new Intent(getActivity(), OperationsContainerActivity.class);
            editCardIntent.putExtra(OperationsContainerActivity.FRAGMENT_FLAG, OperationsContainerActivity.EDIT_CARD_FRAGMENT);
            startActivity(editCardIntent);
        });
    }

    @Override
    public void onCardSelected(final Card card, CardListVerticalAdapter.CardVerticalViewHolder holder) {
        Log.i(TAG, "cardSelected=" + cardSelected);

        if (!cardSelected) {
            cardSelected = true;
            bottomSheetHidden = false;
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            selectedHolder = holder;

            final RelativeLayout.LayoutParams creditCardOldParams = (RelativeLayout.LayoutParams) selectedHolder.creditCard.getLayoutParams();
            final RelativeLayout.LayoutParams chipOldParams = (RelativeLayout.LayoutParams) selectedHolder.cardChipImageView.getLayoutParams();
            final RelativeLayout.LayoutParams creditCardNewParams = new RelativeLayout.LayoutParams(creditCardOldParams);
            final RelativeLayout.LayoutParams chipNewParams = new RelativeLayout.LayoutParams(chipOldParams);

            //increase width and height of credit card
            creditCardNewParams.leftMargin = creditCardOldParams.leftMargin - 32;
            creditCardNewParams.rightMargin = creditCardOldParams.rightMargin - 32;
            creditCardNewParams.topMargin = creditCardOldParams.topMargin - 24;
            creditCardNewParams.bottomMargin = creditCardOldParams.bottomMargin - 24;
            creditCardNewParams.height = creditCardOldParams.height + 48;
            selectedHolder.creditCard.setLayoutParams(creditCardNewParams);

            //increase textviews inside
            selectedHolder.cardNoTextView.setTextSize(20);
            selectedHolder.cardUsernameTextView.setTextSize(17);
            selectedHolder.expDateTextView.setTextSize(17);
            selectedHolder.cardTextView.setTextSize(17);

            //increase imageviews inside
            chipNewParams.topMargin = chipOldParams.topMargin + 4;
            chipNewParams.height = chipOldParams.height + 10;
            chipNewParams.width = chipOldParams.width + 14;
            selectedHolder.cardChipImageView.setLayoutParams(chipNewParams);

            selectedHolder.checkImageView.setVisibility(View.VISIBLE);
            return;
        }
        cardSelected = false;
        bottomSheetHidden = true;
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        final RelativeLayout.LayoutParams creditCardOldParams = (RelativeLayout.LayoutParams) selectedHolder.creditCard.getLayoutParams();
        final RelativeLayout.LayoutParams chipOldParams = (RelativeLayout.LayoutParams) selectedHolder.cardChipImageView.getLayoutParams();
        final RelativeLayout.LayoutParams creditCardNewParams = new RelativeLayout.LayoutParams(creditCardOldParams);
        final RelativeLayout.LayoutParams chipNewParams = new RelativeLayout.LayoutParams(chipOldParams);

        creditCardNewParams.leftMargin = creditCardOldParams.leftMargin + 32;
        creditCardNewParams.rightMargin = creditCardOldParams.rightMargin + 32;
        creditCardNewParams.topMargin = creditCardOldParams.topMargin + 24;
        creditCardNewParams.bottomMargin = creditCardOldParams.bottomMargin + 24;
        creditCardNewParams.height = creditCardOldParams.height - 48;
        selectedHolder.creditCard.setLayoutParams(creditCardNewParams);

        selectedHolder.cardNoTextView.setTextSize(18);
        selectedHolder.cardUsernameTextView.setTextSize(15);
        selectedHolder.expDateTextView.setTextSize(15);
        selectedHolder.cardTextView.setTextSize(15);

        chipNewParams.topMargin = chipOldParams.topMargin - 4;
        chipNewParams.height = chipOldParams.height - 10;
        chipNewParams.width = chipOldParams.width - 14;
        selectedHolder.cardChipImageView.setLayoutParams(chipNewParams);

        selectedHolder.checkImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCardDeselected(Card card) {
        Log.i(TAG, "cardlayout: clicked()");

        if (cardSelected) {
            cardSelected = false;
            bottomSheetHidden = true;
            sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

            final RelativeLayout.LayoutParams creditCardOldParams = (RelativeLayout.LayoutParams) selectedHolder.creditCard.getLayoutParams();
            final RelativeLayout.LayoutParams chipOldParams = (RelativeLayout.LayoutParams) selectedHolder.cardChipImageView.getLayoutParams();
            final RelativeLayout.LayoutParams creditCardNewParams = new RelativeLayout.LayoutParams(creditCardOldParams);
            final RelativeLayout.LayoutParams chipNewParams = new RelativeLayout.LayoutParams(chipOldParams);

            creditCardNewParams.leftMargin = creditCardOldParams.leftMargin + 32;
            creditCardNewParams.rightMargin = creditCardOldParams.rightMargin + 32;
            creditCardNewParams.topMargin = creditCardOldParams.topMargin + 24;
            creditCardNewParams.bottomMargin = creditCardOldParams.bottomMargin + 24;
            creditCardNewParams.height = creditCardOldParams.height - 48;
            selectedHolder.creditCard.setLayoutParams(creditCardNewParams);

            selectedHolder.cardNoTextView.setTextSize(18);
            selectedHolder.cardUsernameTextView.setTextSize(15);
            selectedHolder.expDateTextView.setTextSize(15);
            selectedHolder.cardTextView.setTextSize(15);

            chipNewParams.topMargin = chipOldParams.topMargin - 4;
            chipNewParams.height = chipOldParams.height - 10;
            chipNewParams.width = chipOldParams.width - 14;
            selectedHolder.cardChipImageView.setLayoutParams(chipNewParams);

            selectedHolder.checkImageView.setVisibility(View.INVISIBLE);
        }
    }
}
