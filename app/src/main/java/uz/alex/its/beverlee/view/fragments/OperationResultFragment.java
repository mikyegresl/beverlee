package uz.alex.its.beverlee.view.fragments;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.view.activities.MainActivity;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class OperationResultFragment extends Fragment {
    private ImageView checkImageView;
    private Button returnBtn;
    private AnimatedVectorDrawable vectorDrawable;
    private AnimatedVectorDrawableCompat vectorDrawableCompat;

    private Animation bubbleAnimation;

    public OperationResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            if (getActivity().getCurrentFocus() != null) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_operation_result, container, false);

        checkImageView = root.findViewById(R.id.success_image_view);
        returnBtn = root.findViewById(R.id.return_btn);

        bubbleAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bubble);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new AnimateAsyncTask(checkImageView).execute();

        returnBtn.setOnClickListener(v -> {
            returnBtn.startAnimation(bubbleAnimation);
            returnBtn.postOnAnimationDelayed(() -> {
                if (getActivity() != null) {
                    final Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }, 100);
        });
    }

    private void animateCheck() {
        final Drawable drawable = checkImageView.getDrawable();

        if (drawable instanceof AnimatedVectorDrawableCompat) {
            vectorDrawableCompat = (AnimatedVectorDrawableCompat) drawable;
            vectorDrawableCompat.start();
            return;
        }
        if (drawable instanceof AnimatedVectorDrawable) {
            vectorDrawable = (AnimatedVectorDrawable) drawable;
            vectorDrawable.start();
        }
    }

    private class AnimateAsyncTask extends AsyncTask<Integer, Void, Integer> {
        private WeakReference<ImageView> imageViewRef;

        public AnimateAsyncTask(final ImageView imageView) {
            imageViewRef = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            animateCheck();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            for (int i = 0; i < 6; i++) {
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}