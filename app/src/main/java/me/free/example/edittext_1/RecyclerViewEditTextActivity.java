package me.free.example.edittext_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;

import java.util.ArrayList;
import java.util.List;

import me.free.example.R;

public class RecyclerViewEditTextActivity extends AppCompatActivity implements DemoListAdapter.OnItemEditTextChangedListener {

    RecyclerView mRecyclerView;
    private List<DemoBean> mDemoBeanList;
    private DemoListAdapter mDemoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_edit_text);
        mRecyclerView = findViewById(R.id.recycler_view);


        mDemoListAdapter = new DemoListAdapter();
        mDemoListAdapter.setListener(this);
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

    @Override
    public void onEditTextAfterTextChanged(Editable editable, int position) {
        DemoBean demoBean = mDemoBeanList.get(position);
        demoBean.setAge(editable.toString());
    }
}
