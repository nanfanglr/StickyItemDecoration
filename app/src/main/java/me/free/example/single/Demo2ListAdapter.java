package me.free.example.single;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.free.example.R;

/**
 * @author pengyuming
 * @version 1.0
 * @Package com.pengyuming.myapplication.demo
 * @Description: TODO (用一句话描述该文件做什么) Date: 2017-02-23  09:51
 */
public class Demo2ListAdapter extends BaseQuickAdapter<DemoBean2, BaseViewHolder> implements View.OnClickListener {


    private int mSelectedPos = -1;//实现单选  方法二，变量保存当前选中的position

    public int getmSelectedPos() {
        return mSelectedPos;
    }

    public DemoBean2 getmSelectedItem() {
        return mSelectedPos > -1 ? mData.get(mSelectedPos) : null;
    }

    public void setmSelectedPos(int mSelectedPos) {
        this.mSelectedPos = mSelectedPos;
    }

    public Demo2ListAdapter(@LayoutRes int layoutResId, @Nullable List<DemoBean2> data) {
        super(layoutResId, data);
        if (mSelectedPos > -1 && data != null && data.size() > 0) {
            data.get(mSelectedPos).setSelected(true);
        }
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DemoBean2 item) {
        helper.setText(R.id.btn_name, item.getName());
        helper.setVisible(R.id.btn_name, !item.isShow());
        helper.setChecked(R.id.cb_item_select, item.isSelected());
//        helper.addOnClickListener(R.id.iv_icon);
//        helper.addOnClickListener(R.id.btn_name);
        final View llItemContainer = helper.getView(R.id.ll_item_container);
        final CheckBox checkBox = helper.getView(R.id.cb_item_select);

        llItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //实现单选方法： RecyclerView另一种定向刷新方法：不会有白光一闪动画 也不会重复onBindVIewHolder
                if (mSelectedPos != -1) {
                    BaseViewHolder lastHelper = (BaseViewHolder) getRecyclerView().findViewHolderForLayoutPosition(mSelectedPos);
                    if (lastHelper != null) {//还在屏幕里
                        CheckBox lasCheckBox = lastHelper.getView(R.id.cb_item_select);
                        lasCheckBox.setChecked(false);
                    } else {//add by 2016 11 22 for 一些极端情况，holder被缓存在Recycler的cacheView里，
                        //此时拿不到ViewHolder，但是也不会回调onBindViewHolder方法。所以add一个异常处理
                        notifyItemChanged(mSelectedPos);
                    }
                    mData.get(mSelectedPos).setSelected(false);//不管在不在屏幕里 都需要改变数据
                }
                //设置新Item的勾选状态
                mSelectedPos = helper.getAdapterPosition();
                mData.get(mSelectedPos).setSelected(true);
                checkBox.setChecked(true);
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //实现单选方法： RecyclerView另一种定向刷新方法：不会有白光一闪动画 也不会重复onBindVIewHolder
                if (mSelectedPos != -1) {
                    BaseViewHolder lastHelper = (BaseViewHolder) getRecyclerView().findViewHolderForLayoutPosition(mSelectedPos);
                    if (lastHelper != null) {//还在屏幕里
                        CheckBox lasCheckBox = lastHelper.getView(R.id.cb_item_select);
                        lasCheckBox.setChecked(false);
                    } else {//add by 2016 11 22 for 一些极端情况，holder被缓存在Recycler的cacheView里，
                        //此时拿不到ViewHolder，但是也不会回调onBindViewHolder方法。所以add一个异常处理
                        notifyItemChanged(mSelectedPos);
                    }
                    mData.get(mSelectedPos).setSelected(false);//不管在不在屏幕里 都需要改变数据
                }
                //设置新Item的勾选状态
                mSelectedPos = helper.getAdapterPosition();
                mData.get(mSelectedPos).setSelected(true);
                checkBox.setChecked(true);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
