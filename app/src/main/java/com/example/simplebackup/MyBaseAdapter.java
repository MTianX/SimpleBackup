package com.example.simplebackup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class MyBaseAdapter extends BaseAdapter {
    private  Activity context;
    private  ArrayList<MyViewModel.Item> items;
    MyViewModel myViewModel;

    public MyBaseAdapter(Activity context, ArrayList<MyViewModel.Item> items){
        this.context = context;
        this.items = items;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public MyViewModel.Item getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class Holder{
        TextView nameView;
        TextView psk_mode;
        TextView password;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewModel.Item items = getItem(i);
        Holder holder;
        if(view == null){
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(R.layout.ssid_item,viewGroup,false);
            holder.nameView = (TextView)view.findViewById(R.id.ssid_name);
            holder.psk_mode = view.findViewById(R.id.mode);
            holder.password = view.findViewById(R.id.password);
            view.setTag(holder);
        }
        else{
            holder = (Holder)view.getTag();
        }

        holder.nameView.setText(items.ssidname);
        holder.psk_mode.setText(items.pskmode);
        holder.password.setText(items.password);

        return view;
    }



}
