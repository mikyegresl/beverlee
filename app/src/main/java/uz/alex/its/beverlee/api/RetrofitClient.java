package uz.alex.its.beverlee.api;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.response.CountriesResponse;
import uz.alex.its.beverlee.model.News;
import uz.alex.its.beverlee.model.NewsData;
import uz.alex.its.beverlee.model.Token;
import uz.alex.its.beverlee.model.requestParams.AuthParams;
import uz.alex.its.beverlee.model.requestParams.PinParams;
import uz.alex.its.beverlee.model.requestParams.RegisterParams;
import uz.alex.its.beverlee.model.requestParams.TransferFundsParams;
import uz.alex.its.beverlee.model.requestParams.VerifyCodeParams;
import uz.alex.its.beverlee.model.requestParams.VerifyTransferParams;
import uz.alex.its.beverlee.model.requestParams.WithdrawalParams;
import uz.alex.its.beverlee.model.response.NewsDataResponse;
import uz.alex.its.beverlee.model.response.NewsResponse;
import uz.alex.its.beverlee.model.transaction.Balance;
import uz.alex.its.beverlee.model.transaction.DaysBalance;
import uz.alex.its.beverlee.model.transaction.MonthBalance;
import uz.alex.its.beverlee.model.transaction.Transaction;
import uz.alex.its.beverlee.storage.SharedPrefs;
import uz.alex.its.beverlee.utils.Constants;

public class RetrofitClient {
    private ApiService apiService;

    private static RetrofitClient INSTANCE;

    private RetrofitClient(@NonNull final Context context) {
        final OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS)
                .callTimeout(60L, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        final Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.server_url))
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .setLenient()
                                .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context1) -> new Date(json.getAsJsonPrimitive().getAsLong()))
                                .create()));

        final String bearerToken = SharedPrefs.getInstance(context).getString(Constants.BEARER_TOKEN);

        if (!TextUtils.isEmpty(bearerToken)) {
            final AuthInterceptor interceptor = new AuthInterceptor(bearerToken);
            httpBuilder.addInterceptor(interceptor);
        }

        apiService = retrofitBuilder.client(httpBuilder.addInterceptor(new ContentTypeInterceptor()).build()).build().create(ApiService.class);
    }

    public static RetrofitClient getInstance(@NonNull final Context context) {
        if (INSTANCE == null) {
            synchronized (RetrofitClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitClient(context);
                }
            }
        }
        return INSTANCE;
    }

    /* Authentication */
    public Response<Token> register(final RegisterParams registerParams) throws IOException {
        return apiService.register(registerParams).execute();
    }

    public Response<Token> login(final AuthParams authParams) throws IOException {
        return apiService.login(authParams).execute();
    }

    public Response<Void> verifySms() throws IOException {
        return apiService.verifySms().execute();
    }

    public Response<Void> verifyCall() throws IOException {
        return apiService.verifyCall().execute();
    }

    public Response<Void> submitVerification(final VerifyCodeParams verifyCodeParams) throws IOException {
        return apiService.submitVerification(verifyCodeParams).execute();
    }

    public Response<Void> assignPin(final PinParams pinParams) throws IOException {
        return apiService.assignPin(pinParams).execute();
    }

    public Response<Void> checkVerified() throws IOException {
        return apiService.checkVerified().execute();
    }

    public Response<Void> checkPinAssigned() throws IOException {
        return apiService.checkPinAssigned().execute();
    }

    /* Countries */
    public Response<CountriesResponse> getCountryList() throws IOException {
        return apiService.getCountryList().execute();
    }

    /* Transactions */
    /**
     * Registers the text to display in a tool tip.   The text
     * displays when the cursor lingers over the component.
     *
     * @param page          Номер страницы
     * @param perPage      Кол-во записей
     * @param typeId       Тип. 1 - Начисление бонуса,
     *                      2 - Покупка в магазине,
     *                      3 - Перевод - получение,
     *                      4 - Перевод - отправка,
     *                      5 - Платежные системы - пополнение,
     *                      6 - Платежные системы - вывод
     * @param dateStart    Дата от, формат 2020-01-01
     * @param dateFinished Дата до, формат 2020-01-31
     * @param contactId    Идентификатор получателя или отправителя перевода
     */
    public void getTransactionHistory(final int page,
                                      final int perPage,
                                      final int typeId,
                                      final String dateStart,
                                      final String dateFinished,
                                      final long contactId,
                                      final Callback<List<Transaction>> callback) {
        apiService.getTransactionHistory(page, perPage, typeId, dateStart, dateFinished, contactId).enqueue(callback);
    }

    /* Текущий баланс в уе */
    public void getCurrentBalance(final Callback<Balance> callback) {
        apiService.getCurrentBalance().enqueue(callback);
    }

    /* Поступления/списания за месяц */
    public void getMonthlyBalanceHistory(final int month, final Callback<MonthBalance> callback) {
        apiService.getMonthlyBalanceHistory(month).enqueue(callback);
    }

    /* Поступления/списания за месяц по дням */
    public void getMonthlyBalanceHistoryByDays(final int month, final Callback<DaysBalance> callback) {
        apiService.getMonthlyBalanceHistoryByDays(month).enqueue(callback);
    }

    public void withdrawFunds(final WithdrawalParams withdrawalParams, final Callback<Void> callback) {
        apiService.withdrawFunds(withdrawalParams).enqueue(callback);
    }

    public void transferFunds(final TransferFundsParams transferFundsParams, final Callback<Balance> callback) {
        apiService.transferFunds(transferFundsParams).enqueue(callback);
    }

    public void verifyTransfer(final VerifyTransferParams verifyTransferParams, final Callback<Void> callback) {
        apiService.verifyTransfer(verifyTransferParams).enqueue(callback);
    }

    /* News */
    public void getNews(final int page, final int perPage, final Callback<NewsResponse> callback) {
        apiService.getNews(page, perPage).enqueue(callback);
    }

    public void getNewsData(final long newsId, final Callback<NewsDataResponse> callback) {
        apiService.getNewsData(newsId).enqueue(callback);
    }

    private static final String TAG = RetrofitClient.class.toString();
}