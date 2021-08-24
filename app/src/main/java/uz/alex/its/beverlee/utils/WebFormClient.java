package uz.alex.its.beverlee.utils;

import android.os.Message;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import uz.alex.its.beverlee.view.dialog.WebDialogFragment;
import uz.alex.its.beverlee.view.interfaces.WebFormCallback;

public class WebFormClient extends WebViewClient {
    private final String url;
    private final WebFormCallback callback;

    public WebFormClient(final String url, final WebFormCallback callback) {
        this.url = url;
        this.callback = callback;
    }

    @Override
    public boolean shouldOverrideUrlLoading(final WebView view, final WebResourceRequest request) {
        return !view.getUrl().equals(url);
    }

    @Override
    public void onPageFinished(final WebView view, final String url) {
        super.onPageFinished(view, url);

        if (url.contains("https://beverleebank.net")) {
            callback.toMainPage();
        }
    }

    @Override
    public void onFormResubmission(final WebView view, final Message dontResend, final Message resend) {
        super.onFormResubmission(view, dontResend, resend);
    }

    private static final String TAG = WebFormClient.class.toString();
}
