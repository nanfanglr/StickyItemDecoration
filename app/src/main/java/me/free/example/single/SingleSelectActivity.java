package me.free.example.single;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import me.free.example.R;

public class SingleSelectActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    Button btnGet;

    private Demo2ListAdapter mDemo2ListAdapter;

    private List<DemoBean2> mDemoBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "---------------->onCreate");
        setContentView(R.layout.activity_single_select);
        mRecyclerView = findViewById(R.id.recycler_view);
        btnGet = findViewById(R.id.btn_get);
        showData();
        mDemo2ListAdapter = new Demo2ListAdapter(R.layout.list_demo2_item, mDemoBeanList);
//        mRecyclerView.setAdapter(mDemo2ListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDemo2ListAdapter.bindToRecyclerView(mRecyclerView);

    }

    private void showData() {
        mDemoBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDemoBeanList.add(new DemoBean2("name" + i));
        }
    }

    public void getSelectItem(View view) {
        DemoBean2 demoBean2 = mDemo2ListAdapter.getmSelectedItem();
        if (demoBean2 != null) {
            Log.i("TAG", "---------------->" + mDemo2ListAdapter.getmSelectedItem().toString());
        }
    }
}
