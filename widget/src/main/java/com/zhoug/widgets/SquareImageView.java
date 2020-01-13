package com.zhoug.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * 确定长宽比的ImageView
 * 根据指定的确认的宽或高 设置宽高比例,默认1:1正方形
 */
@SuppressLint("AppCompatCustomView")
public class SquareImageView extends ImageView {
    private static final int SureWidth=0;
    private static final int SureHeight=1;
    private int sure;//0:宽确定高,1:高确定宽
    private float ratio=1;

    public SquareImageView(Context context) {
        this(context,null);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareImageView);
        sure = typedArray.getInt(R.styleable.SquareImageView_sure, SureWidth);
        int ratioWidth = typedArray.getInt(R.styleable.SquareImageView_ratioWidth, 1);
        int ratioHeight = typedArray.getInt(R.styleable.SquareImageView_ratioHeight, 1);
        ratio=(float) ratioWidth/(float)ratioHeight;
        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(sure==SureWidth){
            if(ratio==1){
                super.onMeasure(widthMeasureSpec, widthMeasureSpec);
            }else{
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height= (int) (width/ratio);
                super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
            }

        }else if(sure==SureHeight){
            if(ratio==1){
                super.onMeasure(heightMeasureSpec, heightMeasureSpec);
            }else{
                int height = MeasureSpec.getSize(heightMeasureSpec);
                int width= (int) (height*ratio);
                super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
            }
        }
    }
}
