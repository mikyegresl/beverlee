package uz.alex.its.beverlee.api;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final String bearerToken;

    public AuthInterceptor(@NonNull final String bearerToken) {
        this.bearerToken = bearerToken;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder()
                .header(AUTHORIZATION_HEADER, "Bearer " + bearerToken)
                .header(CONTENT_TYPE_HEADER, CONTENT_TYPE_VALUE)
                .build());
    }

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "application/json; charset=UTF-8;";

    private static final String TAG = AuthInterceptor.class.toString();
}
