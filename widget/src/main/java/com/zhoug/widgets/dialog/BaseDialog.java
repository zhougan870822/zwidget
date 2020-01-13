package com.zhoug.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;


/**
 * @Author HK-LJJ
 * @Date 2019/12/18
 * @Description
 */
public abstract class BaseDialog extends Dialog {
    private int width = WindowManager.LayoutParams.MATCH_PARENT;
    private int height = WindowManager.LayoutParams.WRAP_CONTENT;
    private int gravity = Gravity.BOTTOM;
    private @StyleRes int windowAnimationResId = -1;


    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//必须设置无标题 否则不会匹配内容
        setContentView(getLayoutResId());
        init();
        _setAttributes();
        if (windowAnimationResId != -1) {
            setWindowAnimations(windowAnimationResId);
        }
    }

    protected abstract @LayoutRes int getLayoutResId();

    protected abstract void init();


    /**
     * 设置窗口的属性
     * @param width   窗口宽 默认{@link WindowManager.LayoutParams#MATCH_PARENT}
     * @param height  高 例：默认{@link WindowManager.LayoutParams#WRAP_CONTENT}
     * @param gravity {@link android.view.Gravity} 默认{@link android.view.Gravity#BOTTOM}
     */
    public BaseDialog setAttributes(int width, int height, int gravity) {
        this.width = width;
        this.height = height;
        this.gravity = gravity;
        _setAttributes();
        return this;
    }

    /**
     * 设置窗口的属性
     */
    private void _setAttributes() {
        Window window = this.getWindow();
        if(window==null){
            return ;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = width;
        attributes.height = height;
        attributes.gravity = gravity;
        window.setAttributes(attributes);
    }

    /**
     * 窗口的宽
     * @param width 默认{@link WindowManager.LayoutParams#MATCH_PARENT}
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 窗口的高
     * @param height 默认{@link WindowManager.LayoutParams#WRAP_CONTENT}
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 窗口位置
     * @param gravity {@link android.view.Gravity} 默认{@link android.view.Gravity#BOTTOM}
     */
    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    /**
     * 设置动画
     * @param resId 例:com.heking.common.R.style.common_anim_window_popu_down
     */
    public void setWindowAnimations(@StyleRes int resId) {
        windowAnimationResId = resId;
        if (this.getWindow() != null) {
            this.getWindow().setWindowAnimations(resId);
        }
    }

    /**
     * 设置默认动画,从底部弹出
     */
    public void setDefWindowAnimations() {
        setWindowAnimations(com.zhoug.common.R.style.common_anim_window_popu_down);
    }

}
