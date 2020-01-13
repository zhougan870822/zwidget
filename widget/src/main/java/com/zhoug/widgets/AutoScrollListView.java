package com.zhoug.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.zhoug.common.adapters.recycler.BaseRecyclerViewAdapter;
import com.zhoug.common.utils.AppUtils;
import com.zhoug.common.utils.LogUtils;


/**
 * 描述：跑马灯
 * zhougan
 * 2019/4/5
 **/
public class AutoScrollListView extends RecyclerView implements Runnable{
    private static final String TAG = "AutoScrollListView";
    private int delay=20;
    private int mTouchSlop;

    private boolean isScrolling=false;//正在滚动


    public AutoScrollListView(Context context) {
        this(context,null);
    }

    public AutoScrollListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AutoScrollListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTouchSlop= ViewConfiguration.get(context).getScaledTouchSlop();
    }


    /**
     * 开始自动滚动
     */
    public void start(){
        if(!isScrolling ){
            isScrolling=true;
            LogUtils.d(TAG, "start: 开始滚动");
            postDelayed(this,delay);
        }
    }

    public void stop(){
        isScrolling=false;

    }



    /**
     * 可以滚动,表示数据充满了ListView的高度
     * @return
     */
    public boolean canScrollUp(){
      return canScrollVertically(1);//能否向上滚动
    }

    private boolean isMax=false;

    @Override
    public void run() {
//        LogUtils.d(TAG, "run: 滚动");
        //滚动
//        smoothScrollBy(0,AppUtil.dipTopx(getContext(),3f));
        scrollBy(0, AppUtils.dipTopx(getContext(),0.5f));
        if(isScrolling && canScrollUp()){
            //第一次启动判断设置为无限数据
            if(!isMax){
                isScrolling=false;
                RecyclerView.Adapter adapter = getAdapter();
                if(adapter instanceof Adapter){
                    ((Adapter)adapter).setCanScrollUp(canScrollUp());
                    adapter.notifyDataSetChanged();
                    start();
                    isMax=true;
                }
            }else{
            postDelayed(this,delay);
            }
        }else{
            isScrolling=false;
        }
    }


    private float lastY;
    private float nowY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //拦截滑动事件
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = ev.getRawY();

                break;

            case MotionEvent.ACTION_MOVE:
                nowY=ev.getRawY();
                if(Math.abs(nowY-lastY)>=mTouchSlop){
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                nowY=ev.getRawY();
                if(Math.abs(nowY-lastY)>=mTouchSlop){
                    return true;
                }
                break;

        }
        return super.dispatchTouchEvent(ev);



    }



    public static abstract class  Adapter<VH> extends BaseRecyclerViewAdapter<VH> {
        public boolean canScrollUp=false;


        public void setCanScrollUp(boolean canScrollUp) {
            this.canScrollUp = canScrollUp;
        }

        @Override
        public int getItemCount() {
            if(canScrollUp && getData()!=null && getData().size()>0 )
                return Integer.MAX_VALUE;

            return super.getItemCount();
        }

        @Override
        public VH getItemData(int position) {
            if(canScrollUp && getData()!=null && getData().size()>0 )
                return super.getItemData(position%getData().size());

            return super.getItemData(position);
        }


    }

}
