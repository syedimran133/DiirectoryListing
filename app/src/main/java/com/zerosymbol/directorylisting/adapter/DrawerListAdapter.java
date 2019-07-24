package com.zerosymbol.directorylisting.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zerosymbol.directorylisting.R;

import java.util.ArrayList;

public class DrawerListAdapter extends BaseAdapter {

    private Activity activity;
    private static LayoutInflater inflater = null;
    private ArrayList<String> titles;

    public DrawerListAdapter(Activity activity, ArrayList<String> titles) {
        // TODO Auto-generated constructor stub
        this.titles = titles;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView tv_title;
        ImageView im_icon;
        LinearLayout main;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View view;
        view = inflater.inflate(R.layout.layout_drawer_item, null);

        holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
        holder.main = (LinearLayout) view.findViewById(R.id.drawer_main);

        holder.tv_title.setText(titles.get(position));

        if (titles.get(position).contains("Language")) {
            setMargins(holder.main, 0, 80, 0, 80);
        }
        if (titles.get(position).contains("Logout")) {
            setMargins(holder.main, 0, 0, 150, 0);
        }
        return view;
    }

    private void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}
