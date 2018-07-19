package me.free.example.multi;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import me.free.example.R;
import me.free.example.single.DemoBean2;

/**
 * @author rui
 * @version 1.0
 * @Description: recyclerview多选实现
 */
public class MultiSelectAdapter extends BaseQuickAdapter<DemoBean2, BaseViewHolder> {

    private List<DemoBean2> selectList;

    public MultiSelectAdapter(@LayoutRes int layoutResId, @Nullable List<DemoBean2> data) {
        super(layoutResId, data);
        selectList = new ArrayList<>();
    }

    public List<DemoBean2> getSelectList() {
        return selectList;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DemoBean2 item) {
        helper.setText(R.id.btn_name, item.getName())
                .setVisible(R.id.btn_name, !item.isShow())
                .setChecked(R.id.cb_item_select, item.isSelected());

        final CheckBox checkBox = helper.getView(R.id.cb_item_select);

 /*       if (selectList.contains(item)) {
            checkBox.setChecked(true);
        }*/
    /*    helper.addOnClickListener(R.id.iv_icon);
        helper.addOnClickListener(R.id.ll_item_container);*/

        checkBox.setOnClickListener(view -> {
            if (checkBox.isChecked()) {
                item.setSelected(true);
                selectList.add(item);
            } else {
                item.setSelected(false);
                selectList.remove(item);
            }
        });

    }
}
