package com.example.res;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

class MyListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Restaurant> restaurantList;

    public MyListViewAdapter(Context context, List restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @Override
    public int getCount() {
        return restaurantList.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.restaurant_row, null);

        Restaurant restaurant = restaurantList.get(position);
        TextView textViewRestaurantName = convertView.findViewById(R.id.restaurant_name);
        TextView note = convertView.findViewById(R.id.textViewNote);
        Log.d("test", "" + restaurant.getNote());
        if (restaurant.getNote() != null) {
            note.setText("Note : " + restaurant.getNote());
        } else {
            note.setText("Non notée");
        }
        textViewRestaurantName.setText(restaurant.getName());
        return convertView;
    }
}
