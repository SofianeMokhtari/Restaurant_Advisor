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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AfficheAvisActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static MyListViewAdapterAvis myListViewAdapter;
    private List<Avis> avis;
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficheavis);
        this.avis = new ArrayList<>();
        this.listView = findViewById(R.id.listView3);
        SharedPreferences prefs = getSharedPreferences("TokenUser", Context.MODE_PRIVATE);
        String id_user = prefs.getString("id", null);
        id_user = id_user.replace("\"", "");
        this.myListViewAdapter = new MyListViewAdapterAvis(getApplicationContext(), this.avis, id_user);
        this.listView.setAdapter(myListViewAdapter);

        NavigationView mNavigationView = findViewById(R.id.navigation4);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }

        getAvis();

    }

    public void getAvis() {
        MyRetrofit.getCallApi().getAvisAPI(getIntent().getStringExtra("id_restaurant").replace("\"", "")).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.body() != null)
                    afficheAvis(response.body());
                else {
                    Log.d("menu", "error: " + response.errorBody().source());
                }
            }
            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("menu", "fail " + t.getMessage());
            }
        });
    }

    private void afficheAvis(JsonArray jarr) {
        Gson gson = new Gson();
        for (int i = 0; i < jarr.size(); ++i) {
            JsonObject jobj = jarr.get(i).getAsJsonObject();
            Log.d("okok", "" + jobj);
            Avis avi = gson.fromJson(jobj, Avis.class);
            avi.setId("" + jobj.get("id"));
            avi.setId_user("" + jobj.get("id_user"));
            avi.setId_restaurant("" + jobj.get("id_restaurants"));
            avis.add(avi);
        }
        myListViewAdapter.notifyDataSetChanged();
    }

   public static void delete(final List<Avis> list, final Avis avis, String token) {
       Call<JsonObject> call = MyRetrofit.getCallApi().deleteAvisAPI(avis.getId(), token);
       call.enqueue(new Callback<JsonObject>() {
           @Override
           public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
               if (response.body() != null) {
                   Log.d("delete", ""+response.body());
                   list.remove(avis);
                   myListViewAdapter.notifyDataSetChanged();
               }
               else {
                   Log.d("delete", ""+response.errorBody().source());

               }
           }
           @Override
           public void onFailure(Call<JsonObject> call, Throwable t) {
               Log.d("login", "fail: " + t.getMessage());
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
