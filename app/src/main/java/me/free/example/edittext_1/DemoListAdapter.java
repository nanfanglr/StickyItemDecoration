package me.free.example.edittext_1;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import me.free.example.R;

/**
 * @author rui
 * @Description: recyclerview with edittext
 */
public class DemoListAdapter extends BaseQuickAdapter<DemoBean, BaseViewHolder> {


    private OnItemEditTextChangedListener mListener;

    public DemoListAdapter() {
        super(R.layout.list_demo_item);
    }

    public void setListener(OnItemEditTextChangedListener listener) {
        mListener = listener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DemoBean item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.et_age, item.getAge());
        EditText etAge = helper.getView(R.id.et_age);
        etAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mListener != null) {
                    mListener.onEditTextAfterTextChanged(s, helper.getLayoutPosition());
                }
            }
        });
    }

    public interface OnItemEditTextChangedListener {
        void onEditTextAfterTextChanged(Editable editable, int position);
    }
}
