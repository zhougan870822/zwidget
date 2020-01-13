package com.zhoug.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zhoug.common.utils.StringUtils;
import com.zhoug.widgets.LinePathView;
import com.zhoug.widgets.R;

public class QianMingDialog extends Dialog {
    private String title;
    private LinePathView linePathView;
    private Callback mCallback;

    public QianMingDialog(@NonNull Context context) {
        super(context,R.style.widget_dialog_full);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//必须设置无标题 否则不会匹配内容
        setContentView(R.layout.widget_dialog_qianming);

        TextView tvTitle = findViewById(R.id.title);
        linePathView = findViewById(R.id.linePathView);
        Button btnCancel = findViewById(R.id.btnCancel);
        Button btnClear = findViewById(R.id.btnClear);
        Button btnOk = findViewById(R.id.btnOK);

        tvTitle.setText(StringUtils.getText(title));
        btnCancel.setOnClickListener(v -> cancel());
        btnClear.setOnClickListener(v -> linePathView.clear());
        btnOk.setOnClickListener(v->okClick());

        setAttributes(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

    }

    private void okClick(){
        cancel();
        if (linePathView.getTouched()) {
            mCallback.call(linePathView.getBitMap());
        } else {
            mCallback.call(null);
        }
    }

    public interface Callback{
        void call(Bitmap bitMap);
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
