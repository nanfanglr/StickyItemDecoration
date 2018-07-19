package me.free.example.multi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.free.example.R;
import me.free.example.single.DemoBean2;

public class MultiSelectActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private MultiSelectAdapter mMultiSelectAdapter;
    private List<DemoBean2> mDemoBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_select);
        mRecyclerView = findViewById(R.id.recycler_view);

        showData();

        mMultiSelectAdapter = new MultiSelectAdapter(R.layout.list_demo2_item, mDemoBeanList);
//        mRecyclerView.setAdapter(mDemo2ListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMultiSelectAdapter.bindToRecyclerView(mRecyclerView);

        mMultiSelectAdapter.setOnItemClickListener((adapter, view, position) -> {
            Log.i("TAG", "----viewsetOnItemClick-->getId=" + view.getId());
            Log.i("TAG", "----viewsetOnItemClick-->position=" + position);
        });

        mMultiSelectAdapter.addFooterView(new View(this));

    }

    private void showData() {
        mDemoBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDemoBeanList.add(new DemoBean2("name" + i));
        }
    }

    public void getSelectItem(View view) {
        List<DemoBean2> list = mMultiSelectAdapter.getSelectList();
        for (DemoBean2 de : list) {
            Log.i("TAG", "---->getSelectItem=" + de.toString());
        }
    }


}
