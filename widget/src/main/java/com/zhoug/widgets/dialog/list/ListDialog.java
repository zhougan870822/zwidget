package com.zhoug.widgets.dialog.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.zhoug.common.adapters.BaseListViewAdapter;
import com.zhoug.widgets.R;
import com.zhoug.widgets.dialog.BaseDialog;

import java.util.List;

/**
 * 创建日期:2017/9/7 on 13:57
 * 描述：包含ListView的dialog
 * 作者: zhougan
 */

public abstract class ListDialog<T> extends BaseDialog {
    private final String TAG = ListDialog.class.getSimpleName();
    private List<T> data;
    private String title;
    private AdapterView.OnItemClickListener onItemClickListener;
    private boolean quxiao = false;
    private int textGravity = Gravity.CENTER;



    public ListDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * @param context
     * @param themeResId R.style.widget_dialog_full 左右无空隙
     */
    public ListDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.widget_list_dialog;
    }

    @Override
    protected void init() {
        ListView listView = findViewById(R.id.listView);
        View quxiaoGroup = findViewById(R.id.zg_list_cancel_group);
        TextView quxiaoBtn = findViewById(R.id.zg_list_cancel);

        //设置标题
        if (title != null) {
            TextView tvTitle = findViewById(R.id.textView1);
            LinearLayout llGroup = findViewById(R.id.linearLayout);
            if (llGroup != null && tvTitle != null) {
                llGroup.setVisibility(View.VISIBLE);
                tvTitle.setText(title);
            }
        }

        if (quxiao && quxiaoGroup != null && quxiaoBtn != null) {
            quxiaoGroup.setVisibility(View.VISIBLE);
            quxiaoBtn.setOnClickListener(v -> {
                if (isShowing()) {
                    dismiss();
                }
            });
        } else {
            if (quxiaoGroup != null) {
                quxiaoGroup.setVisibility(View.GONE);
            }
        }

        setListView(listView);
    }

    /**
     * 设置listView
     *
     * @param listView
     */
    private void setListView(ListView listView) {
        if (listView != null) {

            adapter.setData(data);
            listView.setAdapter(adapter);
            if (onItemClickListener != null) {
                listView.setOnItemClickListener(onItemClickListener);
            }

        }


    }

    private BaseListViewAdapter<T> adapter = new BaseListViewAdapter<T>() {
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View root = view;
            if (root == null) {
                root = getLayoutInflater().inflate(R.layout.widget_list_dialog_item, null);
            }
            TextView textView = root.findViewById(R.id.textView2);
            textView.setGravity(textGravity);
            T item = getItem(i);
            textView.setText(getText(item));

//            Log.i(TAG, "getView: s="+s);

            return root;
        }
    };


    public List<T> getData() {
        return data;
    }

    public ListDialog addQuxiaoBtn(boolean quxiao) {
        this.quxiao = quxiao;
        return this;
    }

    public ListDialog setData(List<T> data) {
        this.data = data;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ListDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public ListDialog setTextGravity(int textGravity) {
        this.textGravity = textGravity;
        return this;
    }

    public ListDialog setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    public abstract String getText(T itemData);

}
