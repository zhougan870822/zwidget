package com.zhoug.widgets.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zhoug.widgets.R;

import static android.util.TypedValue.COMPLEX_UNIT_SP;


/**
 * 带有一个输入框的窗口 继承DialogFragment
 */
public class EditDialogFragment extends DialogFragment {
    private TextView tvTitle;
    private TextView tvBtn;
    private EditText etValue;

    private String title = "请输入内容";
    private String value;

    /**
     * 标题大小
     */
    private int titleSize = 0;

    /**
     * 输入文字的大小
     */
    private int textSize = 0;

    private int width = WindowManager.LayoutParams.MATCH_PARENT;
    private int height = WindowManager.LayoutParams.WRAP_CONTENT;
    private int gravity = Gravity.BOTTOM;

    /**
     * 默认动画 从底部滑出滑入
     */
    private @StyleRes
    int windowAnimationResId = R.style.common_anim_window_popu_down;
    /**
     * 是否启动动画
     */
    private boolean animEnable = true;

    /**
     * 确认按钮监听
     */
    private confirmOnclickListener confirmOnclickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //无标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getDialog().getWindow();
        if (window != null) {
            //设置左右无空隙,用一个白色的背景替换默认背景
            window.setBackgroundDrawableResource(R.drawable.widget_bg_white);
            //默认弹出软键盘
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        //设置动画
        if (animEnable) {
            if (window != null) {
                window.setWindowAnimations(windowAnimationResId);
            }
        }
        //设置宽高:这里无效,要在show之后设置,可以重写onStart方法,在里面设置
        setAttributes(width, height, gravity);

        //初始化
        View view = inflater.inflate(R.layout.widget_fragment_edit_dialog, container, false);
        init(view);

        return view;
    }


    /**
     * 初始化
     *
     * @param root
     */
    private void init(View root) {
        tvTitle = root.findViewById(R.id.tv_title);
        tvBtn = root.findViewById(R.id.tv_btn);
        etValue = root.findViewById(R.id.et_value);

        tvTitle.setText(title);
        etValue.setText(value);

        if (titleSize > 0) {
            tvTitle.setTextSize(COMPLEX_UNIT_SP, titleSize);
        }
        if (textSize > 0) {
            etValue.setTextSize(COMPLEX_UNIT_SP, textSize);
        }

        tvBtn.setOnClickListener(v -> {
            dismiss();
            value = etValue.getText().toString();
            if (confirmOnclickListener != null) {
                confirmOnclickListener.onConfirmClick(value);
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        //设置宽高
        setAttributes(width, height, gravity);
    }

    /**
     * 设置窗口的属性
     *
     * @param width   窗口宽 例：WindowManager.LayoutParams.MATCH_PARENT
     * @param height  高 例：200
     * @param gravity 位子 例：Gravity.BOTTOM
     */
    public void setAttributes(int width, int height, int gravity) {
        this.width = width;
        this.height = height;
        this.gravity = gravity;
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
            attributes.width = this.width;
            attributes.height = this.height;
            attributes.gravity = this.gravity;
            dialog.getWindow().setAttributes(attributes);
        }
    }

    /**
     * 设置动画
     *
     * @param resId R.style.anim_window_popu动 画
     */
    public void setWindowAnimations(@StyleRes int resId) {
        windowAnimationResId = resId;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isAnimEnable() {
        return animEnable;
    }

    public void setAnimEnable(boolean animEnable) {
        this.animEnable = animEnable;
    }


    public void setConfirmOnclickListener(confirmOnclickListener listener) {
        this.confirmOnclickListener = listener;
    }


    public interface confirmOnclickListener {
        void onConfirmClick(String value);
    }


}
