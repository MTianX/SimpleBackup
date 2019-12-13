package com.example.simplebackup;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;


import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class WifiFragment extends Fragment {
    private ListView listView;
    private BaseAdapter listItemAdapter;
    MyViewModel myViewModel;
    String TAG = "TAG";

    public WifiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wifi, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ListView listview1 = Objects.requireNonNull(getView()).findViewById(R.id.listview);
        Button button_check = Objects.requireNonNull(getView()).findViewById(R.id.button4);
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        ArrayList<MyViewModel.Item>    list = myViewModel.getSsidItem();
        listItemAdapter = new MyBaseAdapter(getActivity(),list);
        listview1.setAdapter(listItemAdapter);
        button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}

