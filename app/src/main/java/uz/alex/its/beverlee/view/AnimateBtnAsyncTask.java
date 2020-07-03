package uz.alex.its.beverlee.view;

import android.os.AsyncTask;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import java.lang.ref.WeakReference;
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.view.fragments.OperationResultFragment;

public class AnimateBtnAsyncTask extends AsyncTask<Integer, Void, Integer> {
    private WeakReference<CircularProgressButton> btnRef;
    private WeakReference<FragmentActivity> activityRef;

    public AnimateBtnAsyncTask(final FragmentActivity activity, final CircularProgressButton btn) {
        activityRef = new WeakReference<>(activity);
        btnRef = new WeakReference<>(btn);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        btnRef.get().startAnimation();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        final Fragment fragment = new OperationResultFragment();
        activityRef.get().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_up, R.animator.slide_down).add(R.id.operations_fragment_container, fragment).commit();

    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        for (int i = 0; i < 15; i++) {
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
