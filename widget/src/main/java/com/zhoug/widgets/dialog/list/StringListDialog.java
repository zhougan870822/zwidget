package com.zhoug.widgets.dialog.list;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @Author HK-LJJ
 * @Date 2019/10/23
 * @Description TODO
 */
public class StringListDialog extends ListDialog<String> {

    public StringListDialog(@NonNull Context context) {
        super(context);
    }

    public StringListDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public String getText(String itemData) {
        return itemData;
    }
}
