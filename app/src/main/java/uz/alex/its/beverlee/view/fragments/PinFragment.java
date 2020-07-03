package uz.alex.its.beverlee.view.fragments;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uz.alex.its.beverlee.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PinFragment extends Fragment {
    private ImageView firstStarImageView;
    private ImageView secondStarImageView;
    private ImageView thirdStarImageView;
    private ImageView forthStarImageView;
    private TextView oneImageView;
    private TextView twoImageView;
    private TextView threeImageView;
    private TextView fourImageView;
    private TextView fiveImageView;
    private TextView sixImageView;
    private TextView sevenImageView;
    private TextView eightImageView;
    private TextView nineImageView;
    private TextView zeroImageView;
    private ImageView eraseImageView;

    public PinFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_pin, container, false);
        firstStarImageView = root.findViewById(R.id.firstStartImageView);
        secondStarImageView = root.findViewById(R.id.secondStartImageView);
        thirdStarImageView = root.findViewById(R.id.thirdStartImageView);
        forthStarImageView = root.findViewById(R.id.forthStartImageView);
        oneImageView = root.findViewById(R.id.oneImageView);
        twoImageView = root.findViewById(R.id.twoImageView);
        threeImageView = root.findViewById(R.id.threeImageView);
        fourImageView = root.findViewById(R.id.fourImageView);
        fiveImageView = root.findViewById(R.id.fiveImageView);
        sixImageView = root.findViewById(R.id.sixImageView);
        sevenImageView = root.findViewById(R.id.sevenImageView);
        eightImageView = root.findViewById(R.id.eightImageView);
        nineImageView = root.findViewById(R.id.nineImageView);
        zeroImageView = root.findViewById(R.id.zeroImageView);
        eraseImageView = root.findViewById(R.id.eraseImageView);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View.OnClickListener onPinNumberClick = v -> {
            switch (v.getId()) {
                case R.id.oneImageView: {
                    pinArray.add(1);
                    break;
                }
                case R.id.twoImageView: {
                    pinArray.add(2);
                    break;
                }
                case R.id.threeImageView: {
                    pinArray.add(3);
                    break;
                }
                case R.id.fourImageView: {
                    pinArray.add(4);
                    break;
                }
                case R.id.fiveImageView: {
                    pinArray.add(5);
                    break;
                }
                case R.id.sixImageView: {
                    pinArray.add(6);
                    break;
                }
                case R.id.sevenImageView: {
                    pinArray.add(7);
                    break;
                }
                case R.id.eightImageView: {
                    pinArray.add(8);
                    break;
                }
                case R.id.nineImageView: {
                    pinArray.add(9);
                    break;
                }
                case R.id.zeroImageView: {
                    pinArray.add(0);
                    break;
                }
            }

            if (pinArray.size() == 1) {
                firstStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_purple, null));
                secondStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_grey, null));
                thirdStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_grey, null));
                forthStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_grey, null));
            }
            else if (pinArray.size() == 2) {
                firstStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_purple, null));
                secondStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_purple, null));
                thirdStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_grey, null));
                forthStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_grey, null));
            }
            else if (pinArray.size() == 3) {
                firstStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_purple, null));
                secondStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_purple, null));
                thirdStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_purple, null));
                forthStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_grey, null));
            }
            else if (pinArray.size() == 4) {
                firstStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_purple, null));
                secondStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_purple, null));
                thirdStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_purple, null));
                forthStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_purple, null));
                NavHostFragment.findNavController(PinFragment.this).navigate(R.id.action_pinFragment_to_splashActivity);
                pinArray.clear();
            }
            else {
                firstStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_grey, null));
                secondStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_grey, null));
                thirdStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_grey, null));
                forthStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_grey, null));
            }
        };

        oneImageView.setOnClickListener(onPinNumberClick);
        twoImageView.setOnClickListener(onPinNumberClick);
        threeImageView.setOnClickListener(onPinNumberClick);
        fourImageView.setOnClickListener(onPinNumberClick);
        fiveImageView.setOnClickListener(onPinNumberClick);
        sixImageView.setOnClickListener(onPinNumberClick);
        sevenImageView.setOnClickListener(onPinNumberClick);
        eightImageView.setOnClickListener(onPinNumberClick);
        nineImageView.setOnClickListener(onPinNumberClick);
        zeroImageView.setOnClickListener(onPinNumberClick);
//        eraseImageView.setOnClickListener();
    }

    private static final String TAG = PinFragment.class.toString();
    private static final List<Integer> pinArray = new ArrayList<>();
}
