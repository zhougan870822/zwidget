package com.zhoug.widgets.dialog.gender;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zhoug.widgets.R;


public class GenderChooser extends Dialog implements IGenderChooser,View .OnClickListener{
    private String title="选择性别";
    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;
    private LinearLayout mll1;
    private LinearLayout mll2;
    private TextView mTvTitle;
    private String gender="男";
    private IGenderChooser.Callback mCallback;


    public GenderChooser(@NonNull Context context) {
        this(context, R.style.widget_dialog_full);
    }

    public GenderChooser(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);//必须设置无标题 否则不会匹配内容
        setContentView(R.layout.widget_dialog_gender);
        mRadioButton1=findViewById(R.id.gender_rb1);
        mRadioButton2=findViewById(R.id.gender_rb2);
        mll1=findViewById(R.id.gender_ll_item1);
        mll2=findViewById(R.id.gender_ll_item2);
        mTvTitle=findViewById(R.id.gender_title);
        mTvTitle.setText(title);
//        check();

        setListener();
        setWindowAnimations(R.style.common_anim_window_popu_down);
    }

    private void setListener(){
        mll1.setOnClickListener(this);
        mll2.setOnClickListener(this);

    }

    @Override
    public void show() {
        super.show();
        setAttributes(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT,Gravity.BOTTOM);
        check();
    }

    @Override
    public void setTitle(String title) {
        this.title=title;
    }

    @Override
    public void setGender(String gender) {
        this.gender=gender;
    }

    @Override
    public void setCallback(Callback callback) {
        this.mCallback=callback;
    }

    /**
     *设置窗口的属性
     * @param width 窗口宽 例：WindowManager.LayoutParams.MATCH_PARENT
     * @param height 高 例：200
     * @param gravity 位子 例：Gravity.BOTTOM
     */
    public void setAttributes(int width,int height,int gravity){
        WindowManager.LayoutParams attributes = this.getWindow().getAttributes();
        attributes.width=width;
        attributes.height= height;
        attributes.gravity= gravity;
        this.getWindow().setAttributes(attributes);

    }

    /**
     * 设置动画
     * @param resId  R.style.anim_window_popu动 画
     */
    public void setWindowAnimations(@StyleRes int resId){
        this.getWindow().setWindowAnimations(resId);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.gender_ll_item1){
            gender="男";
        }else if(v.getId()== R.id.gender_ll_item2){
            gender="女";
        }

        check();
        if(mCallback!=null){
            dismiss();
            mCallback.onChange(gender);
        }
    }

   private void check( ){
        if("男".equals(gender)){
            if(!mRadioButton1.isChecked()){
                mRadioButton1.setChecked(true);
                mRadioButton2.setChecked(false);
            }
        }else if("女".equals(gender)){
            if(!mRadioButton2.isChecked()){
                mRadioButton1.setChecked(false);
                mRadioButton2.setChecked(true);
            }
        }


   }
}
