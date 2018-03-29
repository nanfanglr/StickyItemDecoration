package me.free.sticky;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cpf on 2018/1/16.
 */

public class StickyItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 吸附的itemView,需要绘制上去的itemView
     */
    private View mStickyItemView;

    /**
     * 吸附itemView 距离顶部
     */
    private int mStickyItemViewMarginTop;

    /**
     * 吸附itemView 高度
     */
    private int mStickyItemViewHeight;

    /**
     * 通过它获取到需要吸附view的相关信息
     */
    private StickyView mStickyView;

    /**
     * 滚动过程中当前的UI是否可以找到吸附的view
     */
    private boolean mCurrentUIFindStickView;

    /**
     * adapter
     */
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

    /**
     * viewHolder
     */
    private RecyclerView.ViewHolder mViewHolder;

    /**
     * position list
     */
    private List<Integer> mStickyPositionList = new ArrayList<>();

    /**
     * layout manager
     */
    private LinearLayoutManager mLayoutManager;

    /**
     * 绑定数据的position
     */
    private int mBindDataPosition = -1;

    /**
     * paint
     */
    private Paint mPaint;

    public StickyItemDecoration() {
        mStickyView = new ExampleStickyView();
        initPaint();
    }

    /**
     * init paint
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    /**
     * 在列表滚动的时候会进入onDrawOver方法，然后循环当前列表的ItemView，如果遇到是吸附的Item View(break), 通过适配器再根据itemType来创建一个ViewHolder，
     * 并且得到这个ViewHolder的itemView；
     * 循环的时候需要不断去缓存吸附View所在RecyclerView中的下标位置position，根据View距离顶部的高度来得到当前吸附View的position；
     * 接下来通过adapter的onBindViewHolder来给ViewHolder的itemView绑定数据，然后计算itemView的宽高,z这样吸附的View拿到了，数据也绑定好了；
     * 然后再计算距离顶部的高度，把itemView绘制到屏幕上即可。
     * 如果因为在当前列表中没有找到吸附的itemView（mCurrentUIFindStickView=false），就直接绘制上一个即可。
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        mLayoutManager = (LinearLayoutManager) parent.getLayoutManager();
        mCurrentUIFindStickView = false;

        for (int posInParent = 0, size = parent.getChildCount(); posInParent < size; posInParent++) {
            //在recyclerview中，每一屏幕加载的itemview总数量是确定，
            // 因此view的position与adapter中的position没有关系
            View view = parent.getChildAt(posInParent);

            /**如果是吸附的view
             *
             * 这里就用到了ExampleStickyView的isStickyView方法
             * 用来判断是否是需要吸附效果的View
             * 是的话才会进入到if逻辑当中
             */
            if (mStickyView.isStickyView(view)) {
                //当前UI当中是否找到了需要吸附的View，此时设置为true
                mCurrentUIFindStickView = true;
                //这个方法是得到吸附View的viewHolder
                getStickyViewHolder(parent);
                //缓存需要吸附的View在列表当中的下标position
                cacheStickyViewPosition(posInParent);
                Log.d("TAG", "------------>posInParent =  " + posInParent);
                //如果当前吸附的view距离 顶部小于等于0，然后给吸附的View绑定数据，计算View的宽高
                if (view.getTop() <= 0) {
                    Log.d("TAG", "------------>view.getTop() =  " + view.getTop());
                    bindDataForStickyView(mLayoutManager.findFirstVisibleItemPosition(), parent.getMeasuredWidth());
                } else {
                    //如果大于0，从position缓存中取得当前的position，然后绑定数据，计算View的宽高
                    if (mStickyPositionList.size() > 0) {
                        if (mStickyPositionList.size() == 1) {
                            bindDataForStickyView(mStickyPositionList.get(0), parent.getMeasuredWidth());
                        } else {
                            int currentPosition = getStickyViewPositionOfRecyclerView(posInParent);
                            Log.d("TAG", "------------>currentPosition =  " + currentPosition);
                            int indexOfCurrentPosition = mStickyPositionList.lastIndexOf(currentPosition);
                            bindDataForStickyView(mStickyPositionList.get(indexOfCurrentPosition - 1), parent.getMeasuredWidth());
                        }
                    }
                }
                //计算吸附的View距离顶部的高度
                if (view.getTop() > 0 && view.getTop() <= mStickyItemViewHeight) {
                    //当view向上滑动且，距离顶部的距离小于mStickyItemViewHeight时,需要计算不断计算mStickyItemViewMarginTop
                    //这样才能在绘制的时候才能去改变mStickyItemView的位置
                    mStickyItemViewMarginTop = mStickyItemViewHeight - view.getTop();
                } else {
                    mStickyItemViewMarginTop = 0;
                }
                //绘制吸附的View
                drawStickyItemView(c);
                break;//当找到第一个吸附的view之后，即可结束当前循环
            }
        }

        //如果在当前的列表视图中没有找到需要吸附的View
        if (!mCurrentUIFindStickView) {
            mStickyItemViewMarginTop = 0;
            //如果已经滑动到底部了，就绑定最后一个缓存的position的View，这种情况一般出现在快速滑动列表的时候吸附View出现错乱，所以需要绑定一下
            if (mLayoutManager.findFirstVisibleItemPosition() + parent.getChildCount() == parent.getAdapter().getItemCount()) {
                bindDataForStickyView(mStickyPositionList.get(mStickyPositionList.size() - 1), parent.getMeasuredWidth());
            }

            //绘制View
            drawStickyItemView(c);
        }
    }

    /**
     * 给StickyView绑定数据
     *
     * @param position
     */
    private void bindDataForStickyView(int position, int width) {
        if (mBindDataPosition == position) return;

        mBindDataPosition = position;
        //绑定数据到viewholder
        mAdapter.onBindViewHolder(mViewHolder, mBindDataPosition);
        measureLayoutStickyItemView(width);
        mStickyItemViewHeight = mViewHolder.itemView.getBottom() - mViewHolder.itemView.getTop();
    }

    /**
     * 缓存吸附的view position
     *
     * @param posInParent
     */
    private void cacheStickyViewPosition(int posInParent) {
        int position = getStickyViewPositionOfRecyclerView(posInParent);
        if (!mStickyPositionList.contains(position)) {
            mStickyPositionList.add(position);
        }
    }

    /**
     * 得到吸附view在RecyclerView中 的position
     *
     * @param posInParent
     * @return
     */
    private int getStickyViewPositionOfRecyclerView(int posInParent) {
//        Log.d("TAG", "------------>findFirstVisibleItemPosition()= " + mLayoutManager.findFirstVisibleItemPosition());
//        Log.d("TAG", "------------> m = " + posInParent);
        //第一个显示的view数据在adapter的位置加+当前吸附的view在当前Parent(recyclerview)中的位置
        return mLayoutManager.findFirstVisibleItemPosition() + posInParent;
    }

    /**
     * 得到吸附viewHolder
     *
     * @param recyclerView
     */
    private void getStickyViewHolder(RecyclerView recyclerView) {
        if (mAdapter != null) return;
        mAdapter = recyclerView.getAdapter();
        //用适配器创建一个viewHolder
        mViewHolder = mAdapter.onCreateViewHolder(recyclerView, mStickyView.getStickViewType());
        //将viewHolde要显示的view赋值给mStickyItemView，这个mStickyItemView是要绘制出来的
        mStickyItemView = mViewHolder.itemView;
    }

    /**
     * 计算布局吸附的itemView
     *
     * @param parentWidth
     */
    private void measureLayoutStickyItemView(int parentWidth) {
        if (!mStickyItemView.isLayoutRequested()) return;
        //宽度即是recyclerview的宽度
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parentWidth, View.MeasureSpec.EXACTLY);
        int heightSpec;

        ViewGroup.LayoutParams layoutParams = mStickyItemView.getLayoutParams();
        if (layoutParams != null && layoutParams.height > 0) {
            //如果原来有宽度则用原来的宽度，EXACTLY模式
            heightSpec = View.MeasureSpec.makeMeasureSpec(layoutParams.height, View.MeasureSpec.EXACTLY);
        } else {
            //如果原来没有高度的则用UNSPECIFIED模式
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        //将包装过的宽高作为参数转到mStickyItemView中调用measure方法，重新测量mStickyItemView的宽高
        mStickyItemView.measure(widthSpec, heightSpec);
        //mStickyItemView重新布局
        mStickyItemView.layout(0, 0, mStickyItemView.getMeasuredWidth(), mStickyItemView.getMeasuredHeight());
    }

    /**
     * 绘制吸附的itemView
     *
     * @param canvas
     */
    private void drawStickyItemView(Canvas canvas) {
        //保存当前画布的状态
        int saveCount = canvas.save();
        //绘制的画布像向上移除出当前的页面
        canvas.translate(0, -mStickyItemViewMarginTop);
        mStickyItemView.draw(canvas);
        //恢复画布状态
        canvas.restoreToCount(saveCount);
    }
}
