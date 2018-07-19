package me.free.example.edittext_3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.free.example.R;
import me.free.example.edittext_1.DemoBean;

public class RecyclerViewEditTextActivity3 extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private List<DemoBean> mDemoBeanList;
    private DemoListAdapter3 mDemoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_edit_text);
        mRecyclerView = findViewById(R.id.recycler_view);

        mDemoListAdapter = new DemoListAdapter3();
        mRecyclerView.setAdapter(mDemoListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        showData();
    }

    private void showData() {
        mDemoBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDemoBeanList.add(new DemoBean(i, "name" + i));
        }
        mDemoListAdapter.setNewData(mDemoBeanList);
    }

}
