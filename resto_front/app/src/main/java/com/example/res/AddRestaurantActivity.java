package com.example.res;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRestaurantActivity extends AppCompatActivity {

    private EditText nomEdit;
    private EditText descriptionEdit;
    private EditText localisationEdit;
    private EditText web_siteEdit;
    private EditText semaineEdit;
    private EditText weekEdit;
    private EditText phoneEdit;
    private Button button;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrestaurant);
        nomEdit = findViewById(R.id.create_restaurant_name_editText);
        descriptionEdit = findViewById(R.id.create_restaurant_description_editText);
        localisationEdit = findViewById(R.id.create_restaurant_localisation_editText);
        web_siteEdit = findViewById(R.id.create_restaurant_web_site_editText);
        semaineEdit = findViewById(R.id.create_restaurant_horaire_semaine_localisation_editText);
        weekEdit = findViewById(R.id.create_restaurant_horaire_week_localisation_editText);
        phoneEdit = findViewById(R.id.create_restaurant_phone_editText);
        button = findViewById(R.id.create_restaurant_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_restaurant();
            }
        });
    }

    private void create_restaurant() {
        String nom = nomEdit.getText().toString();
        String description = descriptionEdit.getText().toString();
        String localisation = localisationEdit.getText().toString();
        String web_site = web_siteEdit.getText().toString();
        String semaine = semaineEdit.getText().toString();
        String week = weekEdit.getText().toString();
        String phone = phoneEdit.getText().toString();

        if (nom.isEmpty() || description.isEmpty() || localisation.isEmpty()
                || phone.isEmpty() || web_site.isEmpty()
                || semaine.isEmpty() || week.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }
        Restaurant restaurant = new Restaurant(nom, description,localisation,phone , web_site, semaine, week);
        Log.d("create", "1");

        SharedPreferences prefs = getSharedPreferences("TokenUser", Context.MODE_PRIVATE);
        String token = prefs.getString("access_token", null);
        token = token.replace("\"", "");
        create_restaurantAPI(restaurant, token);
    }

    private void create_restaurantAPI(Restaurant restaurant, String token) {
        Call<JsonObject> call = MyRetrofit.getCallApi().createRestaurantsAPI(restaurant, token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("create", "3");
                if (response.body() != null){
                    Log.d("create", "response: " + response.body());
                    Intent intent = new Intent(AddRestaurantActivity.this, AfficheRestaurantActivity.class);
                    startActivity(intent);
                }
                else {
                    Log.d("create", "error: " + response.errorBody().source());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("create", "fail: " + t.getMessage());
            }
        });
    }
}
