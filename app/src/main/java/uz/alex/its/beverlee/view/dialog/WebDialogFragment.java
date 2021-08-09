package uz.alex.its.beverlee.view.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.core.widgets.Rectangle;
import androidx.fragment.app.DialogFragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.utils.Constants;

public class WebDialogFragment extends DialogFragment {
    private WebView webView;
    private String replenishUrl;

    public WebDialogFragment() { }

    public static WebDialogFragment newInstance(final String url) {
        WebDialogFragment dialog = new WebDialogFragment();
        Bundle args = new Bundle();
        args.putString(Constants.REPLENISH_URL, url);
        dialog.setArguments(args);
        return dialog;
    }

    private void setDimensions(final int percentage, final Context context) {
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        final Rectangle rect = new Rectangle();
        rect.setBounds(0, 0, metrics.widthPixels, metrics.heightPixels);
        final float width = rect.width * (float) percentage / 100;

        if (getDialog() != null) {
            getDialog().getWindow().setLayout((int) width, ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
    }

    private void setDimensions(final int width) {
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            replenishUrl = getArguments().getString(Constants.REPLENISH_URL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.dialog_web, container);

        webView = root.findViewById(R.id.web_view);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setDimensions(100, requireContext());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(replenishUrl);
    }

    private static final String TAG = WebDialogFragment.class.toString();
}