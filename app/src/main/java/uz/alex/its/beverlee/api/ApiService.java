package uz.alex.its.beverlee.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import uz.alex.its.beverlee.model.response.CountriesResponse;
import uz.alex.its.beverlee.model.News;
import uz.alex.its.beverlee.model.NewsData;
import uz.alex.its.beverlee.model.requestParams.TransferFundsParams;
import uz.alex.its.beverlee.model.requestParams.PinParams;
import uz.alex.its.beverlee.model.requestParams.RegisterParams;
import uz.alex.its.beverlee.model.requestParams.VerifyTransferParams;
import uz.alex.its.beverlee.model.requestParams.WithdrawalParams;
import uz.alex.its.beverlee.model.response.NewsDataResponse;
import uz.alex.its.beverlee.model.response.NewsResponse;
import uz.alex.its.beverlee.model.transaction.DaysBalance;
import uz.alex.its.beverlee.model.transaction.Purchase;
import uz.alex.its.beverlee.model.transaction.Balance;
import uz.alex.its.beverlee.model.transaction.MonthBalance;
import uz.alex.its.beverlee.model.Token;
import uz.alex.its.beverlee.model.transaction.PurchaseResult;
import uz.alex.its.beverlee.model.requestParams.AuthParams;
import uz.alex.its.beverlee.model.requestParams.MakePurchaseParams;
import uz.alex.its.beverlee.model.requestParams.VerifyCodeParams;
import uz.alex.its.beverlee.model.transaction.Transaction;

public interface ApiService {
    /* Authentication */
    @POST("/api/auth/registration")
    Call<Token> register(@Body final RegisterParams registerParams);

    @POST("/api/auth/login")
    Call<Token> login(@Body final AuthParams authParams);

    @POST("/api/user/verify-code/sms")
    Call<Void> verifySms();

    @POST("/api/user/verify-code/call")
    Call<Void> verifyCall();

    @POST("/api/user/verify-code/verify")
    Call<Void> submitVerification(@Body final VerifyCodeParams verifyCodeParams);

    @GET("/api/user/pin")
    Call<Void> checkPinAssigned();

    @POST("/api/user/pin")
    Call<Void> assignPin(@Body final PinParams pinParams);

    @GET("/api/user/verified")
    Call<Void> checkVerified();

    /* Countries */
    @GET("/api/countries")
    Call<CountriesResponse> getCountryList();

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
    @GET("/api/user/transactions")
    Call<List<Transaction>> getTransactionHistory(@Query("page") final int page,
                                                  @Query("per_page") final int perPage,
                                                  @Query("type_id") final int typeId,
                                                  @Query("date_start") final String dateStart,
                                                  @Query("date_end") final String dateFinished,
                                                  @Query("contact_id") final long contactId);

    /* Balance */
    @GET("/api/user/balance/current")
    Call<Balance> getCurrentBalance();

    @POST("/api/user/balance/common")
    Call<MonthBalance> getMonthlyBalanceHistory(@Query("month") final int month);

    @POST("/api/user/balance/days")
    Call<DaysBalance> getMonthlyBalanceHistoryByDays(@Query("month") final int month);

    /* Purchases */
    @GET("/api/user/buy/requests")
    Call<List<Purchase>> getUserPurchases();

    @POST("/api/user/buy/requests/{requestId}/buy")
    Call<PurchaseResult> makePurchase(@Path("requestId") final long requestId, @Body MakePurchaseParams makePurchaseParams);

    @DELETE("/api/user/buy/requests/{requestId}")
    Call<Void> deletePurchase(@Path("requestId") final long requestId);

    /* Transfers */
    @POST("/api/user/transfer")
    Call<Balance> transferFunds(@Body final TransferFundsParams transferFundsParams);

    @POST("/api/user/transfer/verify")
    Call<Void> verifyTransfer(@Body final VerifyTransferParams verifyTransferParams);

    /* Withdrawal */
    @POST("/api/user/transfer/verify")
    Call<Void> withdrawFunds(@Body final WithdrawalParams withdrawalParams);

    /* News */
    @GET("/api/news")
    Call<NewsResponse> getNews(@Query("page") final int page, @Query("per_page") final int perPage);

    @GET("/api/news")
    Call<NewsDataResponse> getNewsData(@Query("newsId") final long newsId);
}

