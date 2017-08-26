package com.udacity.bakeit.io;

import com.udacity.bakeit.dto.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.udacity.bakeit.io.APIConstants.RECIPE_LIST;

/**
 * Created by Vijayalakshmi.IN on 7/13/2017.
 */

public interface APIService {

    @GET(RECIPE_LIST)
    Call<List<Recipe>> getRecipeList();
}

