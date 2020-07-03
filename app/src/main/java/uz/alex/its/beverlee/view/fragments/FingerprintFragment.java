package uz.alex.its.beverlee.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import uz.alex.its.beverlee.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FingerprintFragment extends Fragment {
    private ImageView fingerprintImageView;
    private TextView orEnterPinTextView;

    public FingerprintFragment() {
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
        final View root = inflater.inflate(R.layout.fragment_fingerprint, container, false);
        fingerprintImageView = root.findViewById(R.id.fingerprint_image_view);
        orEnterPinTextView = root.findViewById(R.id.or_enter_pin_text_view);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fingerprintImageView.setOnClickListener(v -> {
            NavHostFragment.findNavController(FingerprintFragment.this).navigate(R.id.action_fingerprintFragment_to_splashActivity);
        });
        orEnterPinTextView.setOnClickListener(v -> {
            NavHostFragment.findNavController(FingerprintFragment.this).navigate(R.id.action_fingerprintFragment_to_pinFragment);
        });
    }
}
