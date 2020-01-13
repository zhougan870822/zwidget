package com.zhoug.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 指示器, 小圆点[ o o o o o]
 * @Author HK-LJJ
 * @Date 2019/12/11
 * @Description
 */
public class IndicatorView  extends View {
    private static final String TAG = "IndicatorView";

    private int colorSelected=Color.BLACK;
    private int colorNormal=Color.GRAY;

    private int radius=20;//圆大小
    private int count=3;//总
    private int selection=0;//选择
    private int distance=radius;
    private int width;
    private int height;
    private Paint mPaint;

    public IndicatorView(Context context) {
        this(context,null);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint(){
        if(mPaint==null){
            mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(colorNormal);
        int x=radius;

        for(int i=0;i<count;i++){
            if(i!=selection){
                canvas.drawCircle(x, height/2,radius ,mPaint );
            }else{
                mPaint.setColor(colorSelected);
                canvas.drawCircle(x, height/2,radius ,mPaint );
                mPaint.setColor(colorNormal);
            }
            x+=radius*3;
        }

        super.onDraw(canvas);
    }

    public int getColorSelected() {
        return colorSelected;
    }

    public void setColorSelected(int colorSelected) {
        this.colorSelected = colorSelected;
    }

    public int getColorNormal() {
        return colorNormal;
    }

    public void setColorNormal(int colorNormal) {
        this.colorNormal = colorNormal;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
        postInvalidate();
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(width==0 || height==0){
            width=radius*2*count+distance*(count-1);
            height=radius*2;
        }
        int w = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int h = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(w, h);
    }

}
