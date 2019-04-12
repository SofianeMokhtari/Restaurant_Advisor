package com.example.res;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateRestaurantActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String id_restaurant;
    private String id_user;
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
        setContentView(R.layout.activity_updaterestaurant);

        this.nomEdit = findViewById(R.id.update_restaurant_name_editText);
        this.descriptionEdit = findViewById(R.id.update_restaurant_description_editText);
        this.localisationEdit = findViewById(R.id.update_restaurant_localisation_editText);
        this.web_siteEdit = findViewById(R.id.update_restaurant_web_site_editText);
        this.semaineEdit = findViewById(R.id.update_restaurant_horaire_semaine_localisation_editText);
        this.weekEdit = findViewById(R.id.update_restaurant_horaire_week_localisation_editText);
        this.phoneEdit = findViewById(R.id.update_restaurant_phone_editText);
        this.button = findViewById(R.id.update_restaurant_button);

        this.nomEdit.setText(getIntent().getStringExtra("name").replace("\"", ""));
        this.descriptionEdit.setText(getIntent().getStringExtra("description").replace("\"", ""));
        this.localisationEdit.setText(getIntent().getStringExtra("localisation").replace("\"", ""));
        this.web_siteEdit.setText(getIntent().getStringExtra("web_site").replace("\"", ""));
        this.semaineEdit.setText(getIntent().getStringExtra("horaire_semaine").replace("\"", ""));
        this.weekEdit.setText(getIntent().getStringExtra("horaire_week").replace("\"", ""));
        this.phoneEdit.setText(getIntent().getStringExtra("telephone").replace("\"", ""));
        this.id_restaurant = getIntent().getStringExtra("id").replace("\"", "");
        this.id_user = getIntent().getStringExtra("id_user").replace("\"", "");

        NavigationView mNavigationView = findViewById(R.id.navigation6);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_restaurant();
            }
        });

    }

    private void update_restaurant() {
        String nom = nomEdit.getText().toString();
        String description = descriptionEdit.getText().toString();
        String localisation = localisationEdit.getText().toString();
        String phone = phoneEdit.getText().toString();
        String web_site = web_siteEdit.getText().toString();
        String semaine = semaineEdit.getText().toString();
        String week = weekEdit.getText().toString();
        if (nom.equals(getIntent().getStringExtra("name").replace("\"", "")))
            nom = "";
        if (description.equals(getIntent().getStringExtra("description").replace("\"", "")))
            description = "";
        if (localisation.equals(getIntent().getStringExtra("localisation").replace("\"", "")))
            localisation = "";
        if (phone.equals(getIntent().getStringExtra("telephone").replace("\"", "")))
            phone = "";
        if (semaine.equals(getIntent().getStringExtra("horaire_semaine").replace("\"", "")))
            semaine = "";
        if (week.equals(getIntent().getStringExtra("horaire_week").replace("\"", "")))
            week = "";

        Restaurant restaurant = new Restaurant(nom, description, localisation,  phone, web_site, semaine, week);

        SharedPreferences prefs = getSharedPreferences("TokenUser", Context.MODE_PRIVATE);
        String token = prefs.getString("access_token", null);
        token = token.replace("\"", "");
        update_restaurantApi(restaurant, this.id_restaurant, token);
    }

    private void update_restaurantApi(Restaurant restaurant, String id_res, String token) {
        Call<JsonObject> call = MyRetrofit.getCallApi().updateRestaurantsAPI(restaurant, id_res, token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("update", "3");
                if (response.body() != null){
                    Log.d("update", "response: " + response.body());
                    Intent intent = new Intent(UpdateRestaurantActivity.this, AfficheMenusActivity.class);
                    intent.putExtra("id", id_restaurant);
                    intent.putExtra("id_user", id_user);
                    intent.putExtra("name", nomEdit.getText().toString());
                    intent.putExtra("description", descriptionEdit.getText().toString());
                    intent.putExtra("localisation", localisationEdit.getText().toString());
                    intent.putExtra("telephone", phoneEdit.getText().toString());
                    intent.putExtra("web_site", web_siteEdit.getText().toString());
                    intent.putExtra("horaire_semaine", semaineEdit.getText().toString());
                    intent.putExtra("horaire_week", weekEdit.getText().toString());
                    startActivity(intent);
                }
                else {
                    Log.d("update", "error: " + response.errorBody().source());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("update", "fail: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.accueil_menu:
                Intent restau = new Intent(this, AfficheRestaurantActivity.class);
                startActivity(restau);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

}
