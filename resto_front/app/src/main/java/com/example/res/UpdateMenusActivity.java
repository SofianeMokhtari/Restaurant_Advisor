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

public class UpdateMenusActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private EditText nom_Edit;
    private EditText description_Edit;
    private EditText prix_Edit;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upadatemenus);

        NavigationView mNavigationView = findViewById(R.id.navigation8);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }

        nom_Edit = findViewById(R.id.name_update_menu);
        description_Edit = findViewById(R.id.description_update_menu);
        prix_Edit = findViewById(R.id.prix_update_menu);
        button = findViewById(R.id.button_update_menu);

        nom_Edit.setText(getIntent().getStringExtra("name").replace("\"", ""));
        description_Edit.setText(getIntent().getStringExtra("description").replace("\"", ""));
        prix_Edit.setText(getIntent().getStringExtra("prix").replace("\"", ""));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = nom_Edit.getText().toString();
                String description = description_Edit.getText().toString();
                String prix = prix_Edit.getText().toString();
                if (nom.isEmpty() || description.isEmpty() || prix.isEmpty())
                    return;
                if (nom.equals(getIntent().getStringExtra("name").replace("\"", "")))
                    nom = "";
                if (description.equals(getIntent().getStringExtra("description").replace("\"", "")))
                    description = "";
                if (prix.equals(getIntent().getStringExtra("prix").replace("\"", "")))
                    prix = "";
                Menu menu = new Menu(nom, description, prix);
                SharedPreferences prefs = getSharedPreferences("TokenUser", Context.MODE_PRIVATE);
                String token = prefs.getString("access_token", null);
                token = token.replace("\"", "");
                updateMenusApi(menu, token);
            }
        });
    }

    private void updateMenusApi(Menu menu, String token) {
        Call<JsonObject> call = MyRetrofit.getCallApi().updateMenusAPI(menu, getIntent().getStringExtra("id_restaurants").replace("\"", ""), getIntent().getStringExtra("id").replace("\"", ""), token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("update", "3");
                if (response.body() != null){
                    Log.d("update", "response: " + response.body());
                    Intent intent = new Intent(UpdateMenusActivity.this, AfficheRestaurantActivity.class);
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
