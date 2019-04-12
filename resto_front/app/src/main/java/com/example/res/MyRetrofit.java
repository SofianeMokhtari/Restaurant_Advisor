package com.example.res;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {

    private Retrofit retrofit;
    private static CallApi callApi;

    public MyRetrofit() {this.configRetrofit();}

    public static CallApi getCallApi() {return callApi;}
    public static void setCallApi(CallApi callApi) {MyRetrofit.callApi = callApi;}

    private void configRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        callApi = retrofit.create(CallApi.class);
    }

}
