package com.example.res;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

class MyListViewAdapterAvis extends BaseAdapter {
    private Context context;
    private List<Avis> menuList;
    private String id_user;

    public MyListViewAdapterAvis(Context context, List menuList, String id_user) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.avis_row, null);

        Avis avis = menuList.get(position);
        TextView textViewRestaurantName = convertView.findViewById(R.id.avis_description);
        TextView textViewRestaurantNote = convertView.findViewById(R.id.avis_note);
        Button button = convertView.findViewById(R.id.avis_button);

        textViewRestaurantName.setText(avis.getDescription());
        textViewRestaurantNote.setText(avis.getNote());
        Avis a = menuList.get(position);
        if (id_user.equals(a.getId_user()))
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = context.getSharedPreferences("TokenUser", Context.MODE_PRIVATE);
                    String token = prefs.getString("access_token", null);
                    token = token.replace("\"", "");
                    AfficheAvisActivity.delete(menuList, menuList.get(position), token);
                }
            });
        else
            button.setVisibility(View.GONE);

        return convertView;
    }
}
