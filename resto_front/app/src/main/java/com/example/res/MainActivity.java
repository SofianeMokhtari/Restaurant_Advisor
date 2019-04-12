package com.example.res;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public MyRetrofit myRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.connection_label);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginActivity();
            }
        });
        Button button = findViewById(R.id.register_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                register();
            }
        });
        this.myRetrofit = new MyRetrofit();
    }

    public void loginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void register() {
        EditText usernameEdit = findViewById(R.id.username_editText);
        String username = usernameEdit.getText().toString();
        EditText nomEdit = findViewById(R.id.nom_editText);
        String nom = nomEdit.getText().toString();
        EditText prenomEdit = findViewById(R.id.prénom_editText);
        String prenom = prenomEdit.getText().toString();
        EditText ageEdit = findViewById(R.id.age_editText);
        String age = ageEdit.getText().toString();
        EditText telephoneEdit = findViewById(R.id.telephone_editText);
        EditText emailEdit = findViewById(R.id.email_editText);
        String email = emailEdit.getText().toString();
        String telephone = telephoneEdit.getText().toString();
        EditText passwordEdit = findViewById(R.id.password_editText);
        String password = passwordEdit.getText().toString();
        EditText password_confEdit = findViewById(R.id.password_conf_editText);
        String  password_conf = password_confEdit.getText().toString();

        if (username.isEmpty() || nom.isEmpty()
                || prenom.isEmpty() || age.isEmpty()
                || telephone.isEmpty() || email.isEmpty()
                || password.isEmpty() || password_conf.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Remplir tout les champs", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!password.equals(password_conf)) {
            Toast.makeText(getApplicationContext(), "password faux", Toast.LENGTH_SHORT).show();

            return;
        }
        Register register = new Register(username, nom, prenom, email, age, telephone, password, password_conf);
        this.registerAPI(register);
    }

    private void registerAPI(Register register) {
        Call<JsonObject> call = MyRetrofit.getCallApi().registerAPI(register);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    Log.d("register", "response: " + response.body());

                }
                else if (response.code() == 400) {
                    Log.d("register", "error: " + response.errorBody().source());
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("register", "fail: " + t.getMessage());
            }
        });
    }
}