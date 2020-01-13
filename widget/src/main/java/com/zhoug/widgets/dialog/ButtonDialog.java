package com.zhoug.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhoug.widgets.R;


/**
 * 创建日期:2017/9/7 on 11:01
 * 描述：有0-2个按钮的Dialog
 * 作者: zhougan
 */

public class ButtonDialog extends Dialog {
    private String title = "提示";
    private String message = "";

    private String btn1;
    private String btn2;
    private View.OnClickListener listener1;
    private View.OnClickListener listener2;

    private boolean hideTitle = false;
    private boolean isCircle = false;

    private int titleColor = -1111;
    private int titleBackground = -1111;

    public ButtonDialog(@NonNull Context context) {
        super(context);
    }

    public ButtonDialog(@NonNull Context context, String message) {
        super(context);
        this.message = message;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//必须设置无标题 否则不会匹配内容
        setContentView(R.layout.widget_button_dialog);

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvMessage = findViewById(R.id.tv_message);
        TextView tvBtn1 = findViewById(R.id.tv_btn1);
        TextView tvBtn2 = findViewById(R.id.tv_btn2);
        View line1 = findViewById(R.id.line1);
        View line2 = findViewById(R.id.line2);

        if (tvTitle != null) {
            tvTitle.setText(title);
            if (titleColor != -1111) {
                tvTitle.setTextColor(titleColor);
            }
            if (titleBackground != -1111) {
                tvTitle.setBackgroundColor(titleBackground);
            }

            if (hideTitle) {
                tvTitle.setVisibility(View.GONE);
            } else {
                tvTitle.setVisibility(View.VISIBLE);
            }

        }
        if (tvMessage != null) {
            tvMessage.setText(message);
        }
        //是否显示分割线1
        if (listener1 != null || listener2 != null) {
            if (line1 != null) {
                line1.setVisibility(View.VISIBLE);
            }
        } else {
            if (line1 != null) {
                line1.setVisibility(View.VISIBLE);
            }
        }
        //是否显示分割线2
        if (listener1 != null && listener2 != null && line2 != null) {
            line2.setVisibility(View.VISIBLE);
        } else {
            if (line2 != null) {
                line2.setVisibility(View.GONE);
            }
        }

        if (tvBtn1 != null && listener1 != null) {
            tvBtn1.setText(btn1);
            tvBtn1.setOnClickListener(listener1);
            tvBtn1.setVisibility(View.VISIBLE);
        }
        if (tvBtn2 != null && listener2 != null) {
            tvBtn2.setText(btn2);
            tvBtn2.setOnClickListener(listener2);
            tvBtn2.setVisibility(View.VISIBLE);
        }
        setAttributes(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        //圆角
        if (isCircle) {
            Window window = getWindow();
            if (window != null) {
                window.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.widget_button_dialog_corner));
            }
        }
    }


    /**
     * 是否隐藏标题
     *
     * @param isHide
     */
    public void hideTitle(boolean isHide) {
        hideTitle = isHide;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (title != null) {
            this.title = title;
        }
    }

    /**
     * 设置消息内容
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 设置按钮1 监听
     *
     * @param text
     * @param listener1
     */
    public void setOnclickListener1(String text, View.OnClickListener listener1) {
        this.btn1 = text;
        this.listener1 = listener1;
    }

    /**
     * 设置按钮2 监听
     *
     * @param text
     * @param listener2
     */
    public void setOnclickListener2(String text, View.OnClickListener listener2) {
        this.btn2 = text;
        this.listener2 = listener2;
    }

    /**
     * 设置圆角
     *
     * @param isCircle
     */
    public void setCircle(boolean isCircle) {
        this.isCircle = isCircle;
    }

    public void setTitleBackground(int titleBackground) {
        this.titleBackground = titleBackground;
    }

    /**
     * 设置窗口的属性
     *
     * @param width   窗口宽 例：WindowManager.LayoutParams.MATCH_PARENT
     * @param height  高 例：200
     * @param gravity 位子 例：Gravity.BOTTOM
     */
    public void setAttributes(int width, int height, int gravity) {
        WindowManager.LayoutParams attributes = this.getWindow().getAttributes();
        attributes.width = width;
        attributes.height = height;
        attributes.gravity = gravity;
        this.getWindow().setAttributes(attributes);

    }

    public void setTitleColor(int color) {
        this.titleColor = color;
    }
}
