package com.zhoug.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zhoug.common.utils.LogUtils;


/**
 * 自定义星星组件
 */
public class RatingBar extends View  {
    private static final String TAG = "RatingBar";
    private Bitmap frontBitmap;
    private Bitmap backBitmap;

    private int spacing=10;//星星间距
    private int numStars=5;//星星总数
    private int starProgress=1;//每个星星代表的进度

//    private int max;//最大进度
//    private int progress;//当前进度
    private float stepSize=1f;//步数 0-1
    private float rating ;//当前星星

    private float scale;
    private Paint paint;
    private Rect src=new Rect();
    private Rect des=new Rect();

    private int desWidth=0;
    private int desHeight=0;
    private int top=0;

    private boolean changeEnable=false;
    private int left;
    private int right;

    public RatingBar(Context context) {
        super(context);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        decodeStyleAttr(typedArray);


        typedArray.recycle();
    }

    private void decodeStyleAttr(TypedArray typedArray){
        //星星
        Drawable frontDrawable = typedArray.getDrawable(R.styleable.RatingBar_frontDrawable);
        frontBitmap=getBitmap(frontDrawable);
        Drawable backDrawable = typedArray.getDrawable(R.styleable.RatingBar_backDrawable);
        backBitmap=getBitmap(backDrawable);

        spacing = (int) typedArray.getDimension(R.styleable.RatingBar_spacing, 10);
        numStars=typedArray.getInteger(R.styleable.RatingBar_numStars,5 );
        starProgress=typedArray.getInteger(R.styleable.RatingBar_starProgress,1 );
        stepSize=typedArray.getFloat(R.styleable.RatingBar_stepSize,1f );
        rating=typedArray.getFloat(R.styleable.RatingBar_rating,0 );
        changeEnable=typedArray.getBoolean(R.styleable.RatingBar_changeEnable,false );

    }

    private void initPaint(){
        if(paint==null){
            paint=new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setAntiAlias(true);
        }
    }



    @Override
    protected void onDraw(Canvas canvas) {
        initPaint();
       //画空星
        if(backBitmap!=null){
            for (int i = 0; i < numStars; i++) {
                src.left=0;
                src.top=0;
                src.right=backBitmap.getWidth();
                src.bottom=backBitmap.getHeight();

                des.left=getPaddingStart()+(desWidth+spacing)*i;
                if(i==0 && left==0){
                    left=getPaddingStart();
                }
                des.top=top;
                des.right= des.left+desWidth;
                des.bottom= des.top+desHeight;
                if(i==numStars-1 && right==0){
                    right= des.right;
                }

                canvas.drawBitmap(backBitmap, src, des, paint);
            }
        }

        //画进度
        if(frontBitmap!=null){
            for (int i = 0; i < rating; i++) {
                //画整颗星
               if(i<=rating-1){
                   src.left=0;
                   src.top=0;
                   src.right=frontBitmap.getWidth();
                   src.bottom=frontBitmap.getHeight();

                   des.left=getPaddingStart()+(desWidth+spacing)*i;
                   des.top=top;
                   des.right= des.left+desWidth;
                   des.bottom= des.top+desHeight;
               }else{
                   //画最后一个星,可能为半个星等
                   //画整个星的百分比
                   float p= rating-i;
                   LogUtils.d(TAG, "onDraw: p="+p);

                   src.left=0;
                   src.top=0;
                   src.right= (int) (frontBitmap.getWidth()*p);
                   src.bottom=frontBitmap.getHeight();

                   des.left=getPaddingStart()+(desWidth+spacing)*i;
                   des.top=top;
                   des.right= (int) ((des.left+desWidth*p));
                   des.bottom= des.top+desHeight;
               }
                canvas.drawBitmap(frontBitmap, src, des, paint);
            }
        }
        super.onDraw(canvas);
    }

    private  float r2=0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!changeEnable){
            return super.onTouchEvent(event);
        }
//        LogUtils.d(TAG, "onTouchEvent: ");
        float x = event.getX();
//        LogUtils.d(TAG, "onTouchEvent: x="+x);

        if(x<=left){
            r2=0;
        }else if(x>=right){
            r2=numStars;
        }else if(x<=left+desWidth){
            r2= (x-left)/desWidth;
        } else if(x<left+desWidth+spacing){
            r2= 1;
        }else if(x>left+desWidth+spacing){
            //前end个完全显示
            int end = (int) ((x - left) / (desWidth + spacing));
//            LogUtils.d(TAG, "onTouchEvent: end="+end);
            float s = x - left - end * (desWidth + spacing);
            float add= s/desWidth;
            add= add>1 ? 1 :add;
            r2=end+add;

        }

        /*
        *
        * */
//        LogUtils.d(TAG, "onTouchEvent: r2="+r2);
        r2 = computeRating(r2);
        if(rating!=r2){
            rating=r2;
            invalidate();
//            LogUtils.d(TAG, "onTouchEvent: 刷新rating="+rating);
        }


        return true;
    }

    private float computeRating(float rating){
        if (rating<0){
            rating=0;
        }else if(rating>numStars){
            rating=numStars;
        }
        int a=(int) rating;
        float b=rating-a;
        if(b==0){
            return rating;
        }else{
            float c=  (b/stepSize);
            int x=(int)c;
            if(x==c){
                return a+c*stepSize;
            }else{
                return a+(x+1)*stepSize;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        rating=computeRating(rating);
        if(frontBitmap==null){
            setFrontBitmap(R.drawable.widget_icon_rating_bar_front);
        }
        if(backBitmap==null){
            setBackBitmap(R.drawable.widget_icon_rating_bar_back);
        }

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);//宽度
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);//高度

        //最小宽高
        int minWidth=getSuggestedMinimumWidth();
        int minHeight=getSuggestedMinimumHeight();
        int imgWidth=0;
        int imgHeight=0;
        //需要测量
        if (widthMode == MeasureSpec.UNSPECIFIED || widthMode == MeasureSpec.AT_MOST) {
            if (frontBitmap != null) {
                //图片的宽度
                imgWidth = frontBitmap.getWidth();
                //根据图片宽度计算出的组件宽
                widthSize = imgWidth * numStars+(numStars-1)*spacing+getPaddingEnd()+getPaddingStart();
                widthSize=Math.max(minWidth, widthSize);

            }
        }
        //需要测量
        if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) {
            if (frontBitmap != null) {
                imgHeight =  frontBitmap.getHeight();
                heightSize=imgHeight+getPaddingTop()+getPaddingBottom();
                heightSize=Math.max(minHeight, heightSize);
            }
        }

        //根据宽高计算显示图片大小
         imgWidth=(widthSize-getPaddingStart()-getPaddingEnd()-(numStars-1)*spacing)/numStars;
         imgHeight=heightSize-getPaddingTop()-getPaddingBottom();

        LogUtils.d(TAG, "onMeasure: imgWidth="+imgWidth);
        LogUtils.d(TAG, "onMeasure: imgHeight="+imgHeight);
         //计算图片缩放
        if(frontBitmap!=null){
            float scaleX=((float) imgWidth/(float)frontBitmap.getWidth());
            float scaleY=((float) imgHeight/(float)frontBitmap.getHeight());
            scale=scaleX>scaleY ? scaleY :scaleX;

            desWidth= (int) (backBitmap.getWidth()*scale);
            desHeight= (int) (backBitmap.getHeight()*scale);
            top=(heightSize-desHeight)/2;
        }


        LogUtils.d(TAG, "onMeasure: imgWidth="+imgWidth+",imgHeight="+imgHeight);
        LogUtils.d(TAG, "onMeasure: scale="+scale);

        super.onMeasure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY));


//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public int getNumStars() {
        return numStars;
    }

    public void setNumStars(int numStars) {
        this.numStars = numStars;
    }

    public float getStepSize() {
        return stepSize;
    }

    public void setStepSize(float stepSize) {
        this.stepSize = stepSize;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isChangeEnable() {
        return changeEnable;
    }

    public void setChangeEnable(boolean changeEnable) {
        this.changeEnable = changeEnable;
    }

    public int getStarProgress() {
        return starProgress;
    }

    public void setStarProgress(int starProgress) {
        this.starProgress = starProgress;
    }

    public void setFrontBitmap(@DrawableRes int res){
        Drawable drawable = getContext().getResources().getDrawable(res);
        frontBitmap =getBitmap(drawable);
    }

    public void setBackBitmap(@DrawableRes int res){
        Drawable drawable = getContext().getResources().getDrawable(res);
        backBitmap =getBitmap(drawable);
    }

    private Bitmap getBitmap(Drawable drawable){
        if(drawable==null) return null;
        if(drawable instanceof BitmapDrawable){
            return ((BitmapDrawable) drawable).getBitmap();
        }
        return null;
    }
}
