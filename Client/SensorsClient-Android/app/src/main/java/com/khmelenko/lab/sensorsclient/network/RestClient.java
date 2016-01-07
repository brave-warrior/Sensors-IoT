package com.khmelenko.lab.sensorsclient.network;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class RestClient {

    private static final String ENDPOINT = "http://192.168.1.1:8080";

    private static RestClient sInstance;

    private ApiService mApiService;

    public static RestClient getInstance() {
        if (sInstance == null) {
            sInstance = new RestClient();
        }
        return sInstance;
    }

    private RestClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mApiService = retrofit.create(ApiService.class);
    }

    /**
     * Gets API service
     *
     * @return API service
     */
    public ApiService getService() {
        return mApiService;
    }
}
