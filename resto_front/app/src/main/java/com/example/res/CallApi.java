package com.example.res;

        import com.google.gson.JsonArray;
        import com.google.gson.JsonObject;
        import com.google.gson.JsonPrimitive;

        import retrofit2.Call;
        import retrofit2.http.Body;
        import retrofit2.http.GET;
        import retrofit2.http.Header;
        import retrofit2.http.POST;
        import retrofit2.http.Path;
        import retrofit2.http.Query;

public interface CallApi {
    @POST("inscription")
    Call<JsonObject> registerAPI(@Body Register register);

    @POST("login")
    Call<JsonObject> loginAPI(@Body Login login);

    @GET("restaulist")
    Call<JsonArray> getRestaurantAPI();

    @POST("createRestaurants")
    Call<JsonObject> createRestaurantsAPI(@Body Restaurant restaurant, @Header("Authorization") String authHeader);

    @POST("updateRestaurants")
    Call<JsonObject> updateRestaurantsAPI(@Body Restaurant restaurant, @Query("id") String id_restaurant, @Header("Authorization") String authHeader);

    @POST("createMenus")
    Call<JsonObject> createMenusAPI(@Body Menu menu, @Query("id") String id_restaurant, @Header("Authorization") String authHeader);

    @POST("deleteMenus")
    Call<JsonObject> deleteMenusAPI(@Query("id_restaurants") String id_restaurants, @Query("id") String id_menu, @Header("Authorization") String authHeader);

    @POST("updateMenus")
    Call<JsonObject> updateMenusAPI(@Body Menu menu, @Query("id_restaurants") String id_restaurants, @Query("id") String id_menu, @Header("Authorization") String authHeader);

    @POST("createAvis")
    Call<JsonObject> createAvisAPI(@Body Avis avis, @Query("id") String id_restaurant, @Header("Authorization") String authHeader);

    @POST("deleteAvis")
    Call<JsonObject> deleteAvisAPI(@Query("id") String id_avis, @Header("Authorization") String authHeader);

    @GET("menu/{id}")
    Call<JsonArray> getMenusAPI(@Path("id") String id);

    @GET("avislist/{id}")
    Call<JsonArray> getAvisAPI(@Path("id") String id_restaurants);

    @GET("restaurantn/{name}")
    Call<JsonArray> getRestaurantByNameAPI(@Path("name") String name);

    @GET("logout")
    Call<JsonObject> logoutAPI(@Header("Authorization") String authHeader);

    @POST("deleteRestaurants")
    Call<JsonPrimitive> deleteRestaurantsAPI(@Query("id") String id_restaurants, @Header("Authorization") String authHeader);
}