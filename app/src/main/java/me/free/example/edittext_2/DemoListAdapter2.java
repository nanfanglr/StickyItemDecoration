package me.free.example.edittext_2;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import me.free.example.R;
import me.free.example.edittext_1.DemoBean;

/**
 * @author rui
 * @Description: recyclerview with edittext
 */
public class DemoListAdapter2 extends BaseQuickAdapter<DemoBean, BaseViewHolder> {

    public DemoListAdapter2() {
        super(R.layout.list_demo_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, DemoBean item) {
        // 第一步应该移除监听，
        EditText etAge = helper.getView(R.id.et_age);
        if (etAge.getTag() instanceof TextWatcher) {
            etAge.removeTextChangedListener((TextWatcher) etAge.getTag());
        }
        // 第二步设置值，
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.et_age, item.getAge());
        // 第三步添加监听，
        Log.i("TAG", "-------------->afterTextChanged.item=" + item.toString());
        TextWatcher watcherMin = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s
                    , int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s
                    , int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    String input = s.toString().trim();
                    item.setAge(input);
                }
                Log.i("TAG", "-------------->afterTextChanged.s=" + s.toString());
            }
        };
        etAge.addTextChangedListener(watcherMin);
        // 第四步设置Tag。
        etAge.setTag(watcherMin);

    }


}
