package uz.alex.its.beverlee.view.fragments;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.Serializable;

import uz.alex.its.beverlee.R;

public class InitializationFragment extends Fragment {
    private static final String TAG = InitializationFragment.class.toString();
    private Button signInBtn;
    private Button signUpBtn;

    private Animation bubbleAnimation;

    public InitializationFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_initialization, container, false);

        signInBtn = root.findViewById(R.id.sign_in_button);
        signUpBtn = root.findViewById(R.id.sign_up_button);

        bubbleAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bubble);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signInBtn.setOnClickListener(v -> {
            signInBtn.startAnimation(bubbleAnimation);
            signInBtn.postOnAnimationDelayed(() -> NavHostFragment.findNavController(InitializationFragment.this).navigate(R.id.action_initFragment_to_inputPhoneFragment), 100);
        });

        signUpBtn.setOnClickListener(v -> {
            signUpBtn.startAnimation(bubbleAnimation);
            signUpBtn.postOnAnimationDelayed(() -> NavHostFragment.findNavController(InitializationFragment.this).navigate(R.id.action_init_fragment_to_signUpFragment), 100);
        });
    }
}
