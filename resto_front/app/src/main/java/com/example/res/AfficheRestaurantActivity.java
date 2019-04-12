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
import android.widget.AdapterView;
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

public class AfficheRestaurantActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ListView listView;
    private List<Restaurant> restaurants;
    private MyListViewAdapter myListViewAdapter;
    private Button button;
    private Button restaurant_by_name;
    private TextView nameR;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficherestaurant);
        this.restaurants = new ArrayList<>();
        this.myListViewAdapter = new MyListViewAdapter(getApplicationContext(), this.restaurants);
        this.listView = findViewById(R.id.listView);
        this.listView.setAdapter(myListViewAdapter);
        getRestaurants();
        this.restaurant_by_name = findViewById(R.id.restaurant_by_name);
        this.nameR = findViewById(R.id.restaurant_by_name_editText);

        SharedPreferences prefs = getSharedPreferences("TokenUser", Context.MODE_PRIVATE);
        String token = prefs.getString("id", null);

        NavigationView mNavigationView = findViewById(R.id.navigation);

        if (token == null) {
            mNavigationView.getMenu().getItem(1).setVisible(false);
            mNavigationView.getMenu().getItem(2).setVisible(false);
            mNavigationView.getMenu().getItem(3).setVisible(false);
        }

        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant r = restaurants.get(position);
                afficheMenusActivity(r);
            }
        });

        this.restaurant_by_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aff_restaurant_by_name();
            }
        });
    }

    private void afficheMenusActivity(Restaurant r) {
        Intent intent = new Intent(this, AfficheMenusActivity.class);
        intent.putExtra("id", r.getId());
        intent.putExtra("id_user", r.getId_user());
        intent.putExtra("name", r.getName());
        intent.putExtra("description", r.getDescription());
        intent.putExtra("localisation", r.getLocalisation());
        intent.putExtra("telephone", r.getTelephone());
        intent.putExtra("web_site", r.getWeb_site());
        intent.putExtra("horaire_semaine", r.getHoraire_semaine());
        intent.putExtra("horaire_week", r.getHoraire_week());
        startActivity(intent);
    }

    private void addRestaurantActivity() {
        Intent intent = new Intent(this, AddRestaurantActivity.class);
        startActivity(intent);
    }

    private void getRestaurants() {
        Log.d("restau", "rentre ");
        MyRetrofit.getCallApi().getRestaurantAPI().enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.body() != null)
                    afficheRestaurant(response.body());
                else if (response.code() == 400) {
                    Log.d("restau", "error: " + response.errorBody().source());
                }
            }
            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("restau", "fail " + t.getMessage());
            }
        });
    }

    private void afficheRestaurant(JsonArray jarr) {
        Gson gson = new Gson();
        for (int i = 0; i < jarr.size(); ++i) {
            JsonObject jobj = jarr.get(i).getAsJsonObject();

            Restaurant restaurant = gson.fromJson(jobj, Restaurant.class);
            restaurant.setId("" + jobj.get("id"));
            restaurant.setId_user("" + jobj.get("id_user"));
            restaurants.add(restaurant);
        }
        myListViewAdapter.notifyDataSetChanged();
    }

    private void aff_restaurant_by_name() {
        String name = nameR.getText().toString();

        if (name.isEmpty()) {
            restaurants.clear();
            getRestaurants();
            return;
        }
        MyRetrofit.getCallApi().getRestaurantByNameAPI(name).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.body() != null) {
                    if (response.body().size() > 0)
                        affiche_one_Restaurant(response.body());
                    else
                       Toast.makeText(getApplicationContext(), "N'existe pas", Toast.LENGTH_SHORT).show();

                }
                else if (response.code() == 400) {
                    Log.d("go", "error: " + response.errorBody().source());
                }
            }
            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("go", "fail " + t.getMessage());
            }
        });
    }

    private void affiche_one_Restaurant(JsonArray jarr) {
        Log.d("one", "" + jarr.size());

        Gson gson = new Gson();
        JsonObject jobj = jarr.get(0).getAsJsonObject();
        Restaurant restaurant = gson.fromJson(jobj, Restaurant.class);
        restaurant.setId("" + jobj.get("id"));
        restaurants.clear();
        restaurants.add(restaurant);
        myListViewAdapter.notifyDataSetChanged();
        Log.d("one", jarr.toString());
    }

    public void logout(){
        SharedPreferences prefs = getSharedPreferences("TokenUser", Context.MODE_PRIVATE);
        String token = prefs.getString("access_token", null);
        token = token.replace("\"", "");
        MyRetrofit.getCallApi().logoutAPI(token).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    Intent home = new Intent(AfficheRestaurantActivity.this, LoginActivity.class);
                    startActivity(home);
                }
                else if (response.code() == 400) {
                    Log.d("logout", "error: " + response.errorBody().source());
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("logout", "fail " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences prefs = getSharedPreferences("TokenUser", Context.MODE_PRIVATE);
        String token = prefs.getString("access_token", null);
            getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.trieun:
                trier_par_note();
                return true;
            /*case R.id.logout:
                logout();
                return true;
            case R.id.accueil:
                Intent home = new Intent(this, AfficheRestaurantActivity.class);
                startActivity(home);
                return true;
            case R.id.restau:
                Intent restau = new Intent(this, AddRestaurantActivity.class);
                startActivity(restau);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void trier_par_note() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.profile:
                Intent user = new Intent(this, ProfilUserActivity.class);
                startActivity(user);
                return true;
            case R.id.logout:
                logout();
                return true;
            case R.id.accueil:
                Intent home = new Intent(this, AfficheRestaurantActivity.class);
                startActivity(home);
                return true;
            case R.id.restau:
                Intent restau = new Intent(this, AddRestaurantActivity.class);
                startActivity(restau);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

}
