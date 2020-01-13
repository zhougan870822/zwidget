package com.zhoug.widgets.dialog.gender;

public interface IGenderChooser {
    void show();
    void setTitle(String title);
    void dismiss();
    void setCanceledOnTouchOutside(boolean canceled);
    void setCancelable(boolean canceled);
    void setGender(String gender);
    void setCallback(Callback callback);

     interface Callback{
        void onChange(String gender);
    }

}
