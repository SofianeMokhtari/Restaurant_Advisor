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
import android.widget.TextView;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAvisActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private TextView descriptionTextView;
    private TextView noteTextView;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addavis);
        this.descriptionTextView = findViewById(R.id.avis_create_description);
        this.noteTextView = findViewById(R.id.avis_create_note);
        this.button = findViewById(R.id.avis_create_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_avis();
            }
        });

        NavigationView mNavigationView = findViewById(R.id.navigation4);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }
    }
    private void create_avis() {
        String description = descriptionTextView.getText().toString();
        String note = noteTextView.getText().toString();
        if (description.isEmpty() || note.isEmpty())
            return;

        Avis avis = new Avis(description, note);
        SharedPreferences prefs = getSharedPreferences("TokenUser", Context.MODE_PRIVATE);
        String token = prefs.getString("access_token", null);
        token = token.replace("\"", "");
        create_avisApi(avis, getIntent().getStringExtra("id_restaurant").replace("\"", ""), token);

    }
    private void create_avisApi(Avis avis, String id_restaurant, String token) {
        Call<JsonObject> call = MyRetrofit.getCallApi().createAvisAPI(avis, id_restaurant,token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("create", "3");
                if (response.body() != null){
                    Log.d("create", "response: " + response.body());
                    finish();
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
