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
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMenusActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Button create_menu;
    private EditText nom_Edit;
    private EditText description_Edit;
    private EditText prix_Edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmenus);

        NavigationView mNavigationView = findViewById(R.id.navigation10);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }

        create_menu = findViewById(R.id.button_create_menu);
        nom_Edit = findViewById(R.id.name_add_menu);
        description_Edit = findViewById(R.id.description_add_menu);
        prix_Edit = findViewById(R.id.prix_add_menu);

        create_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_menu();
            }
        });
    }

    private void add_menu() {
        String nom = nom_Edit.getText().toString();
        String description = description_Edit.getText().toString();
        String prix = prix_Edit.getText().toString();

        if (nom.isEmpty() || description.isEmpty() || prix.isEmpty())
            return;

        Menu menu = new Menu(nom, description, prix);
        SharedPreferences prefs = getSharedPreferences("TokenUser", Context.MODE_PRIVATE);
        String token = prefs.getString("access_token", null);
        token = token.replace("\"", "");

        add_menu_Api(menu, "" + getIntent().getStringExtra("id_restau").replace("\"", ""), token);

    }

    private void add_menu_Api(Menu menu, String id_restau, String token) {
        Call<JsonObject> call = MyRetrofit.getCallApi().createMenusAPI(menu, id_restau, token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("create", "3");
                if (response.body() != null){
                    Log.d("create", "response: " + response.body());
                    Intent intent = new Intent(AddMenusActivity.this, AfficheRestaurantActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Le menu a bien été ajouté", Toast.LENGTH_SHORT).show();
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
