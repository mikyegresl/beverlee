package uz.alex.its.beverlee.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uz.alex.its.beverlee.model.News;
import uz.alex.its.beverlee.model.NewsData;
import uz.alex.its.beverlee.model.response.NewsDataResponse;
import uz.alex.its.beverlee.model.response.NewsResponse;
import uz.alex.its.beverlee.repository.NewsRepository;

public class NewsViewModel extends ViewModel {
    private final NewsRepository repository;
    private final MutableLiveData<List<News>> newsMutableLiveData;
    private final MutableLiveData<List<NewsData>> newsDataMutableLiveData;

    public NewsViewModel(final Context context) {
        this.repository = new NewsRepository(context);
        this.newsMutableLiveData = new MutableLiveData<>();
        this.newsDataMutableLiveData = new MutableLiveData<>();
    }

    public void fetchNews(final int page, final int perPage) {
        repository.fetchNews(page, perPage, new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if (response.code() == 200 && response.isSuccessful()) {
                    final NewsResponse customizableObject = response.body();

                    if (customizableObject == null) {
                        Log.w(TAG, "doWork(): empty response from server");
                        return;
                    }
                    final int itemCount = customizableObject.getRecordsTotal();
                    final List<News> newsList = customizableObject.getNewsList();

                    if (newsList == null) {
                        Log.w(TAG, "doWork(): newsList is NULL");
                        return;
                    }
                    if (newsList.isEmpty()) {
                        Log.w(TAG, "doWork(): newsList is empty");
                        return;
                    }
                    setNewsList(newsList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure(): ", t);
            }
        });
    }

    public void fetchNewsData(final long newsId) {
        repository.fetchNewsData(newsId, new Callback<NewsDataResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsDataResponse> call, @NonNull Response<NewsDataResponse> response) {
                if (response.code() == 200 && response.isSuccessful()) {
                    final NewsDataResponse customizableObject = response.body();

                    if (customizableObject == null) {
                        Log.w(TAG, "doWork(): empty response from server");
                        return;
                    }
                    final int itemCount = customizableObject.getRecordsTotal();
                    final List<NewsData> newsData = customizableObject.getNewsData();

                    if (newsData == null) {
                        Log.w(TAG, "doWork(): newsList is NULL");
                        return;
                    }
                    if (newsData.isEmpty()) {
                        Log.w(TAG, "doWork(): newsList is empty");
                        return;
                    }
                    setNewsData(newsData);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsDataResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure(): ", t);
            }
        });
    }

    public void setNewsData(final List<NewsData> newsData) {
        this.newsDataMutableLiveData.setValue(newsData);
    }

    public void setNewsList(final List<News> newsList) {
        this.newsMutableLiveData.setValue(newsList);
    }

    public LiveData<List<News>> getNewsList() {
        return newsMutableLiveData;
    }

    public LiveData<List<NewsData>> getNewsData() {
        return newsDataMutableLiveData;
    }

    private static final String TAG = NewsViewModel.class.toString();
}
