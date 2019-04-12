package com.example.res;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.res.AfficheRestaurantActivity;

public class ProfilUserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiluser);
        TextView username = findViewById(R.id.username_profil);
        TextView firstname = findViewById(R.id.firstname);
        TextView lastname = findViewById(R.id.lastname);
        TextView age = findViewById(R.id.age_profil);
        TextView email = findViewById(R.id.email_profil);
        TextView phone = findViewById(R.id.phone_profil);



        SharedPreferences prefs = getSharedPreferences("TokenUser", Context.MODE_PRIVATE);


        String user = prefs.getString("username", null);
        String firstn = prefs.getString("nom", null);
        String lastn = prefs.getString("prénom", null);
        String ageprofil = prefs.getString("age", null);
        String emailprofil = prefs.getString("email", null);
        String phoneprofil = prefs.getString("telephone", null);

        username.setText(user.replace("\"", ""));
        firstname.setText(firstn.replace("\"", ""));
        lastname.setText(lastn.replace("\"", ""));
        age.setText(ageprofil.replace("\"", ""));
        email.setText(emailprofil.replace("\"", ""));
        phone.setText(phoneprofil.replace("\"", ""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences prefs = getSharedPreferences("TokenUser", Context.MODE_PRIVATE);
        String token = prefs.getString("access_token", null);
        if (token != null) {
            getMenuInflater().inflate(R.menu.main_menu, menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent user = new Intent(this, ProfilUserActivity.class);
                startActivity(user);
                return true;
           /* case R.id.logout:
                AfficheRestaurantActivity.logout();
                return true;*/
            case R.id.accueil:
                Intent home = new Intent(this, AfficheRestaurantActivity.class);
                startActivity(home);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}