package com.example.res;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

class MyListViewAdapterMenus extends BaseAdapter {
    private Context context;
    private List<Menu> menuList;
    private String id_user;

    public MyListViewAdapterMenus(Context context, List menuList, String id_user) {
        this.context = context;
        this.menuList = menuList;
        this.id_user = id_user;
    }

    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.menu_row, null);

        Menu menu = menuList.get(position);
        TextView textViewRestaurantName = convertView.findViewById(R.id.menu_name);
        TextView textViewRestaurantPrix = convertView.findViewById(R.id.menu_prix);
        textViewRestaurantName.setText("Menu : " +menu.getName());
        textViewRestaurantPrix.setText("Prix : " + menu.getPrix());
        Button button = convertView.findViewById(R.id.menu_button_delete);
        Button button2 = convertView.findViewById(R.id.menu_button_update);

        Menu m = menuList.get(position);
        if (id_user.equals(m.getId_user()))
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = context.getSharedPreferences("TokenUser", Context.MODE_PRIVATE);
                    String token = prefs.getString("access_token", null);
                    token = token.replace("\"", "");
                    AfficheMenusActivity.delete(menuList, menuList.get(position), token);
                }
            });
        else
            button.setVisibility(View.GONE);

        if (id_user.equals(m.getId_user()))
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = context.getSharedPreferences("TokenUser", Context.MODE_PRIVATE);
                    String token = prefs.getString("access_token", null);
                    token = token.replace("\"", "");
                    AfficheMenusActivity.update(menuList.get(position), token);
                }
            });
        else
            button.setVisibility(View.GONE);
        return convertView;
    }
}