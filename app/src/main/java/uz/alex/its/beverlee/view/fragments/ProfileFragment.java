package uz.alex.its.beverlee.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.squareup.picasso.Picasso;

import uz.alex.its.beverlee.Constants;
import uz.alex.its.beverlee.R;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private static final String TAG = ProfileFragment.class.toString();

    private ImageView backArrowImageView;
    private ImageView avatarImageView;
    private ImageView changeProfilePhotoImageView;
    private Button editBtn;
    private ConstraintLayout parentLayout;

    private boolean bottomSheetHidden = true;
    private boolean imageSelected = false;
    private static int lastPosition = -1;
    private static int selectedAvatarId = -1;
    private static int selectedNameId = -1;
    private static int selectedCheckId = -1;

    private LinearLayout bottomSheet;
    private TextView bottomSheetPersonalData;
    private TextView bottomSheetNotificationsSettings;
    private TextView bottomSheetChangePassword;
    private BottomSheetBehavior sheetBehavior;

    private Animation bubbleAnimation;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_profile, container, false);

        Log.i(TAG, "onCreateView: ");

        backArrowImageView = root.findViewById(R.id.back_arrow_image_view);
        avatarImageView = root.findViewById(R.id.profile_photo_image_view);
        changeProfilePhotoImageView = root.findViewById(R.id.change_profile_photo_image_view);
        editBtn = root.findViewById(R.id.edit_btn);
        parentLayout = root.findViewById(R.id.parent_layout);

        bubbleAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bubble);

        if (getActivity() != null) {
            bottomSheet = getActivity().findViewById(R.id.bottom_sheet_profile);
            bottomSheetPersonalData = getActivity().findViewById(R.id.personal_data_option);
            bottomSheetNotificationsSettings = getActivity().findViewById(R.id.notifications_option);
            bottomSheetChangePassword = getActivity().findViewById(R.id.change_password_option);

            sheetBehavior = BottomSheetBehavior.from(bottomSheet);
            sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i(TAG, "onViewCreated: ");
        
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN: {
                        break;
                    }
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        editBtn.setVisibility(View.INVISIBLE);
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
                            editBtn.setVisibility(View.VISIBLE);
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

        avatarImageView.setOnClickListener(v -> {

        });

        changeProfilePhotoImageView.setOnClickListener(v -> {
            final Intent pickImageIntent = new Intent();
            pickImageIntent.setAction(Intent.ACTION_GET_CONTENT);
            pickImageIntent.setType("image/*");
//            pickImageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(Intent.createChooser(pickImageIntent, getString(R.string.pick_image)), Constants.INTENT_PICK_IMAGE);
        });

        bottomSheetPersonalData.setOnClickListener(v -> {
            if (getActivity() != null) {
                final Fragment personalDataFragment = new PersonalDataFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.profile_fragment_container, personalDataFragment).commit();
                editBtn.setVisibility(View.VISIBLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                imageSelected = false;
                bottomSheetHidden = true;
            }
        });

        bottomSheetNotificationsSettings.setOnClickListener(v -> {
            if (getActivity() != null) {
                final Fragment notificationSettingsFragment = new NotificationSettingsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.profile_fragment_container, notificationSettingsFragment).commit();
                editBtn.setVisibility(View.VISIBLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                imageSelected = false;
                bottomSheetHidden = true;
            }
        });

        bottomSheetChangePassword.setOnClickListener(v -> {
            if (getActivity() != null) {
                final Fragment changePasswordFragment = new ChangePasswordFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.profile_fragment_container, changePasswordFragment).commit();
                editBtn.setVisibility(View.VISIBLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                imageSelected = false;
                bottomSheetHidden = true;
            }
        });

        parentLayout.setOnClickListener(v -> {
            Log.i(TAG, "parentLayoutClick: " + imageSelected + " bottomSheet: " + bottomSheetHidden);
            if (imageSelected) {
                imageSelected = false;
                bottomSheetHidden = true;
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        editBtn.setOnClickListener(v -> {
            editBtn.startAnimation(bubbleAnimation);
            editBtn.postOnAnimationDelayed(() -> {
                Log.i(TAG, "editBtnClick: " + imageSelected + " bottomSheet: " + bottomSheetHidden);
                if (!imageSelected) {
                    imageSelected = true;
                    bottomSheetHidden = false;
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    return;
                }
                imageSelected = false;
                bottomSheetHidden = true;
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }, 100);
        });

        backArrowImageView.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.INTENT_PICK_IMAGE) {
                if (data == null) {
                    Log.e(TAG, "onActivityResult(): data is NULL");
                    Toast.makeText(getContext(), "Ошибка: пустой ответ", Toast.LENGTH_SHORT).show();
                    return;
                }
                final Uri selectedImageURI = data.getData();
                Log.i(TAG, "onActivityResult(): data=" + selectedImageURI);
                Picasso.get()
                        .load(selectedImageURI)
                        .noPlaceholder()
                        .fit()
                        .centerInside()
                        .rotate(90)
                        .into(avatarImageView);
            }
        }
    }
}