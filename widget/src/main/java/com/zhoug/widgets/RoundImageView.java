package com.zhoug.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * 圆角图片组件
 */
public class RoundImageView extends android.support.v7.widget.AppCompatImageView {
    private static final String TAG = "RoundImageView";

    float width,height;
    /**角度 默认10*/
    private int corners=10;

    /**是否为圆形:为true时corners属性无效*/
    private boolean circular=false;
    private float radius;

//    private int
    private Path path;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        corners=typedArray.getDimensionPixelSize(R.styleable.RoundImageView_corners,10 );
        circular=typedArray.getBoolean(R.styleable.RoundImageView_circular,false );

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        radius=width<height ? width/2 : height/2;
    }

    private Path getPath(){
        if(path==null){
            path = new Path();
            //圆
            if(circular){
                path.addCircle(width/2, height/2, radius,  Path.Direction.CW);
            }else{
                //圆角
                path.moveTo(corners, 0);
                path.lineTo(width - corners, 0);
                //二次贝塞尔
                path.quadTo(width, 0, width, corners);
                path.lineTo(width, height - corners);
                path.quadTo(width, height, width - corners, height);
                path.lineTo(corners, height);
                path.quadTo(0, height, 0, height - corners);
                path.lineTo(0, corners);
                path.quadTo(0, 0, corners, 0);
            }
        }

        return path;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (width > corners && height > corners) {
            canvas.clipPath(getPath());
        }

        super.onDraw(canvas);
    }

}
