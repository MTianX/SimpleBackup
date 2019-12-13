package com.example.simplebackup;

import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.simplebackup.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
   // public MyViewModel myViewModel;
    ActivityMainBinding binding;
    private ListView listView;
    private BaseAdapter listItemAdapter;
    String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavController controller = Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this,controller);
       // binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
/*        ActivityMainBinding mbinding = ActivityMainBinding.inflate(getLayoutInflater());
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);*/
/*        binding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<MyViewModel.Item>     list = myViewModel.getSsidItem();

                for(int index = 0; index < list.size();index ++){
                    Log.d(TAG,String.format("list: %s",list.get(index).ssidname));
                }

                listItemAdapter = new MyBaseAdapter(MainActivity.this,list);
                binding.ListView.setAdapter(listItemAdapter);

            }
        });*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController controller = Navigation.findNavController(this,R.id.fragment);
        return  controller.navigateUp();
        //return super.onSupportNavigateUp();
    }
}
