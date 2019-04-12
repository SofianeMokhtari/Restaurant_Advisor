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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.Time;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public Utilisateur utilisateur;
    private EditText emailEdit;
    private EditText passwordEdit;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEdit = findViewById(R.id.login_email_editText);
        passwordEdit = findViewById(R.id.login_password_editText);

        TextView textView = findViewById(R.id.register_label);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button button = findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login();
            }
        });
        Button button_two = findViewById(R.id.affiche_restaurant_button);
        button_two.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences preferences =getSharedPreferences("TokenUser",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                afficheRestaurantActivity();
            }
        });
    }


    private void afficheRestaurantActivity() {
        Intent intent = new Intent(this, AfficheRestaurantActivity.class);
        startActivity(intent);
    }

    private void login() {
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            return;
        }
        Login login = new Login(email, password);
        this.loginApi(login);
        Log.d("login", "rentre");
    }

    private void loginApi(Login login) {
        Call<JsonObject> call = MyRetrofit.getCallApi().loginAPI(login);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    Gson gson = new Gson();
                    String token_user = "" + response.body().get("access_token");
                    SharedPreferences.Editor editor = getSharedPreferences("TokenUser", MODE_PRIVATE).edit();
                    editor.putString("access_token", "Bearer " + token_user );
                    editor.putString("username", "" + response.body().get("username"));
                    editor.putString("nom", "" + response.body().get("nom"));
                    editor.putString("prénom", "" + response.body().get("prénom"));
                    editor.putString("email", "" + response.body().get("email"));
                    editor.putString("age", "" + response.body().get("age"));
                    editor.putString("telephone", "" + response.body().get("telephone"));
                    editor.putString("id", "" + response.body().get("id"));
                    editor.apply();

                    afficheRestaurantActivity();
                }
                else {
                    Toast.makeText(getApplicationContext(), "N'existe pas", Toast.LENGTH_SHORT).show();
                    passwordEdit.setText("");
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("login", "fail: " + t.getMessage());
            }
        });
    }
    private void parseRep(JsonObject obj) {
        Log.d("login", "response: " + obj);
    }
}