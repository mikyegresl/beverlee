package uz.alex.its.beverlee.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uz.alex.its.beverlee.api.RetrofitClient;
import uz.alex.its.beverlee.model.Country;
import uz.alex.its.beverlee.model.News;
import uz.alex.its.beverlee.model.NewsData;
import uz.alex.its.beverlee.model.response.CountriesResponse;
import uz.alex.its.beverlee.model.response.NewsDataResponse;
import uz.alex.its.beverlee.model.response.NewsResponse;
import uz.alex.its.beverlee.storage.LocalDatabase;

public class NewsRepository {
    private final Context context;

    public NewsRepository(final Context context) {
        this.context = context;
    }

    public void fetchNews(final int page, final int perPage, final Callback<NewsResponse> callback) {
        RetrofitClient.getInstance(context).getNews(page, perPage, callback);
    }

    public void fetchNewsData(final long newsId, final Callback<NewsDataResponse> callback) {
        RetrofitClient.getInstance(context).getNewsData(newsId, callback);
    }

    private static final String TAG = NewsRepository.class.toString();
}
