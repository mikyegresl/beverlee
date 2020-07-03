package uz.alex.its.beverlee.view;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.lang.ref.WeakReference;
import java.util.Locale;
import uz.alex.its.beverlee.R;

public class CounterTask extends AsyncTask<Void, Integer, Integer> {
    private static final String TAG = CounterTask.class.toString();
    private WeakReference<Resources> resourcesRef;
    private WeakReference<EditText> editTextRef;
    private WeakReference<TextView> textViewRef;
    private WeakReference<Button> btnRef;

    public CounterTask(@NonNull final Resources res, @NonNull final EditText editText, @NonNull final TextView textView, @NonNull final Button btn) {
        this.resourcesRef = new WeakReference<>(res);
        this.editTextRef = new WeakReference<>(editText);
        this.textViewRef = new WeakReference<>(textView);
        this.btnRef = new WeakReference<>(btn);
    }

    @Override
    protected void onPreExecute() {
        Log.i(TAG, "onPreExecute: ");
        super.onPreExecute();
        editTextRef.get().setEnabled(false);
        btnRef.get().setEnabled(false);
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        Log.i(TAG, "doInBackground: ");
        try {
            for (int i = 178; i > 0; i--) {
                Thread.sleep(1000L);
                publishProgress(i);
            }
        }
        catch (InterruptedException e) {
            Log.e(TAG, "doInBackground(): ", e);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        final int minutes = values[0] / 60;
        final int seconds = values[0] % 60;
        String minuteFormat = "0%d";
        String secondFormat = "%d";

        if (seconds <= 9) {
            secondFormat = String.format(Locale.US, "0%d", seconds);
        }
        final String minutesStr = String.format(Locale.US, minuteFormat, minutes);
        final String secondsStr = String.format(Locale.US, secondFormat, seconds);
        final String result = minutesStr + ":" + secondsStr;
        textViewRef.get().setText(result);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        Log.i(TAG, "onPostExecute: ");
        super.onPostExecute(integer);
        //make editText active again
        //make btn active again
        editTextRef.get().setEnabled(true);
        btnRef.get().setEnabled(true);
        btnRef.get().setBackground(resourcesRef.get().getDrawable(R.drawable.btn_purple, null));
        btnRef.get().setTextColor(Color.WHITE);
        textViewRef.get().setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    private static class Counter implements Runnable {
        private volatile boolean exit;
        Thread t;

        private Counter() {
            this.t = new Thread(this);
            this.exit = false;
            t.start();
        }

        @Override
        public void run() {
            while (!exit) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void stop() {
            exit = true;
        }
    }
}
