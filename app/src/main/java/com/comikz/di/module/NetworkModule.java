package com.comikz.di.module;

import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moczul.ok2curl.CurlInterceptor;
import com.moczul.ok2curl.logger.Loggable;
import com.comikz.BuildConfig;
import com.comikz.MangaApplication;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Agustin on 01/11/2016.
 */
@Module
public class NetworkModule {
    private static boolean ENABLE_OKHTTP_CACHE = true;
    private static long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MB
    private final String mHost;

    public NetworkModule(String host) {
        mHost = host;
    }


    /**
     * Builds a 10MiB cache,
     * @return
     */
    @Provides
    @Singleton
    protected okhttp3.Cache provideCache(Context context) {
        okhttp3.Cache cache = null;
        try  {
            cache = new okhttp3.Cache( new File(context.getCacheDir(), "http" ), SIZE_OF_CACHE);
        } catch (Exception e)  {
            Log.w(MangaApplication.class.getSimpleName(), e.getLocalizedMessage());
        }
        return cache;
    }

    @Provides
    @Singleton
    protected Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
    }

    @Provides
    @Singleton @Named("cacheInterceptor")
    protected Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept (Chain chain) throws IOException {
                Request request = chain.request();
                request = request.newBuilder()
                        .header("Cache-Control", "public, max-age=60")
                        .build();
                return chain.proceed(request);
            }
        };
    }

    @Provides
    @Singleton
    protected OkHttpClient provideOkHttpClient(okhttp3.Cache cache,
                                               @Named("cacheInterceptor") Interceptor cacheInterceptor){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (ENABLE_OKHTTP_CACHE) {
            builder.cache(cache);
        }

        try {
            setUnsafeSslCertificates(builder);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't build SSL certificates");
        }

        builder.connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new CurlInterceptor(new Loggable() {
                @Override
                public void log(String message) {
                    Log.d("Captain", message);
                }
            }));
        }

        return builder.build();
    }

    private void setUnsafeSslCertificates(OkHttpClient.Builder builder) throws NoSuchAlgorithmException, KeyManagementException {
        // Create a trust manager that does not validate certificate chains
        final TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        // Create an ssl socket factory with our all-trusting manager
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        builder.sslSocketFactory(sslSocketFactory);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }

    @Provides
    @Singleton
    protected Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
        try {
            retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(mHost)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
            return retrofit;
        } catch (Exception e) {
            retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BuildConfig.HOST)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
            return retrofit;
        }
    }

}
