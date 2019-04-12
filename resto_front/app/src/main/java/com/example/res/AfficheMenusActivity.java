package com.example.res;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.res.R.*;
import static com.example.res.R.id.addmenu;
import static com.example.res.R.id.start;

public class AfficheMenusActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView nomTextView;
    private TextView descriptionTextView;
    private TextView telephoneTextView;
    private TextView localisationTextView;
    private TextView web_siteTextView;
    private TextView horaire_semaineTextView;
    private TextView horaire_weekTextView;
    private NavigationView navigationView;

    private static String id;
    private String id_user;
    private ListView listView;
    private List<Menu> menus;
    private Button button;
    private static MyListViewAdapterMenus myListViewAdapter;
    private static Context mContext;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_affichemenus);
        mContext = this;

        SharedPreferences prefs = getSharedPreferences("TokenUser", Context.MODE_PRIVATE);
        String token = prefs.getString("id", null);

        this.id = getIntent().getStringExtra("id").replace("\"", "");
        Log.d("idresto", "" + this.id);
        this.id_user = getIntent().getStringExtra("id_user");

        NavigationView mNavigationView = findViewById(R.id.navigation2);

        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }

        if (token == null) {
            mNavigationView.getMenu().getItem(2).setVisible(false);
        }

        if(!this.id_user.equals(token)) {
            Log.d("create", "C4EST A TOI");
            mNavigationView.getMenu().getItem(0).setVisible(false);
            mNavigationView.getMenu().getItem(1).setVisible(false);
            mNavigationView.getMenu().getItem(4).setVisible(false);
        } else {
            mNavigationView.getMenu().getItem(2).setVisible(false);
            MenuItem delete = mNavigationView.getMenu().findItem(R.id.deleteresto);
            SpannableString del = new SpannableString(delete.getTitle());
            del.setSpan(new TextAppearanceSpan(this, style.TextAppearance44), 0, del.length(), 0);
            delete.setTitle(del);
        }


        String id_user = prefs.getString("id", "0");
        id_user = id_user.replace("\"", "");

        this.menus = new ArrayList<>();
        this.myListViewAdapter = new MyListViewAdapterMenus(getApplicationContext(), this.menus, id_user);
        this.listView = findViewById(R.id.listView2);
        this.listView.setAdapter(myListViewAdapter);
        getMenus();

        nomTextView = findViewById(R.id.detaille_restaurant_name_textView);
        descriptionTextView = findViewById(R.id.detaille_restaurant_description_textView);
        telephoneTextView = findViewById(R.id.detaille_restaurant_telephone_textView);
        localisationTextView = findViewById(R.id.detaille_restaurant_localisation_textView);
        web_siteTextView = findViewById(R.id.detaille_restaurant_website_textView);
        horaire_semaineTextView = findViewById(R.id.detaille_restaurant_horaire_semaine__textView);
        horaire_weekTextView = findViewById(R.id.detaille_restaurant_horaire_week_textView);

        nomTextView.setText(getIntent().getStringExtra("name"));
        descriptionTextView.setText(getIntent().getStringExtra("description"));
        telephoneTextView.setText(getIntent().getStringExtra("localisation"));
        localisationTextView.setText(getIntent().getStringExtra("telephone"));
        web_siteTextView.setText(getIntent().getStringExtra("web_site"));
        horaire_semaineTextView.setText(getIntent().getStringExtra("horaire_semaine"));
        horaire_weekTextView.setText(getIntent().getStringExtra("horaire_week"));
    }

    private void getMenus() {
        MyRetrofit.getCallApi().getMenusAPI(this.id).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.body() != null)
                    afficheMenus(response.body());
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

    private void afficheMenus(JsonArray jarr) {
        Log.d("menu", "rentre2 " + this.id);

        Gson gson = new Gson();
        for (int i = 0; i < jarr.size(); ++i) {
            JsonObject jobj = jarr.get(i).getAsJsonObject();
            Menu menu = gson.fromJson(jobj, Menu.class);
            menu.setId("" + jobj.get("id"));
            menu.setId_user("" + jobj.get("id_user"));
            menu.setId_restau("" + jobj.get("id_restaurants"));
            menus.add(menu);
        }
        myListViewAdapter.notifyDataSetChanged();
    }

    public void deleteResto() {
        SharedPreferences prefs = getSharedPreferences("TokenUser", Context.MODE_PRIVATE);
        String token = prefs.getString("access_token", null);
        token = token.replace("\"", "");


        Call<JsonPrimitive> call = MyRetrofit.getCallApi().deleteRestaurantsAPI( this.id, token);
        call.enqueue(new Callback<JsonPrimitive>() {
            @Override
            public void onResponse(Call<JsonPrimitive> call, Response<JsonPrimitive> response) {
                if (response.body() != null) {
                    Intent newpage = new Intent(AfficheMenusActivity.this, AfficheRestaurantActivity.class);
                    startActivity(newpage);
                }
            }
            @Override
            public void onFailure(Call<JsonPrimitive> call, Throwable t) {
                Log.d("deleteresto", "fail: " + t.getMessage());
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        SharedPreferences prefs = getSharedPreferences("TokenUser", Context.MODE_PRIVATE);
        String id = prefs.getString("id", null);

        switch (menuItem.getItemId()) {
            case R.id.addmenu:
                if(this.id_user.equals(id)) {
                    Intent intent = new Intent(this, AddMenusActivity.class);
                    intent.putExtra("id_restau", this.id);
                    startActivity(intent);
                }
                return true;
            case R.id.addavis:
                Intent intentc = new Intent(this, AddAvisActivity.class);
                intentc.putExtra("id_restaurant", getIntent().getStringExtra("id").replace("\"", ""));
                startActivity(intentc);
                return true;
            case R.id.showavis:
                Intent intentAvis = new Intent(this, AfficheAvisActivity.class);
                intentAvis.putExtra("id_restaurant", getIntent().getStringExtra("id").replace("\"", ""));
                startActivity(intentAvis);
                return true;
            case R.id.modifmenu:
                Intent intent = new Intent(this, UpdateRestaurantActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id").replace("\"", ""));
                intent.putExtra("id_user", getIntent().getStringExtra("id_user").replace("\"", ""));
                intent.putExtra("name",  getIntent().getStringExtra("name").replace("\"", ""));
                intent.putExtra("description", getIntent().getStringExtra("description").replace("\"", ""));
                intent.putExtra("localisation", getIntent().getStringExtra("localisation").replace("\"", ""));
                intent.putExtra("telephone", getIntent().getStringExtra("telephone").replace("\"", ""));
                intent.putExtra("web_site", getIntent().getStringExtra("web_site").replace("\"", ""));
                intent.putExtra("horaire_semaine", getIntent().getStringExtra("horaire_semaine").replace("\"", ""));
                intent.putExtra("horaire_week", getIntent().getStringExtra("horaire_week").replace("\"", ""));
                startActivity(intent);
                return true;
            case R.id.deleteresto:
                Log.d("ok", "okok");
                deleteResto();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public static void delete(final List<Menu> list, final Menu menu, String token) {
        Call<JsonObject> call = MyRetrofit.getCallApi().deleteMenusAPI( id, menu.getId(), token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    list.remove(menu);
                    myListViewAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("login", "fail: " + t.getMessage());
            }
        });
    }

    public static void update(Menu m, String token) {
        Intent intent = new Intent(mContext, UpdateMenusActivity.class);
        intent.putExtra("name", m.getName());
        intent.putExtra("description", m.getDescription());
        intent.putExtra("prix", m.getPrix());
        intent.putExtra("id_restaurants", m.getId_restau());
        intent.putExtra("id", m.getId());
        mContext.startActivity(intent);
    }
}