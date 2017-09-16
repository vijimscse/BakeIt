package com.udacity.bakeit.io;

import com.udacity.bakeit.dto.Recipe;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.udacity.bakeit.io.APIConstants.BASE_URL;

/**
 * Created by Vijayalakshmi.IN on 7/13/2017.
 * Class for API call
 */

public class IOManager {

    private static Retrofit getRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return  new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static void requestRecipeList(Callback<List<Recipe>> callback) {
        Retrofit retrofit = getRetrofit();
        APIService apiService = retrofit.create(APIService.class);
        Call<List<Recipe>> call = apiService.getRecipeList();

        call.enqueue(callback);
    }
}
