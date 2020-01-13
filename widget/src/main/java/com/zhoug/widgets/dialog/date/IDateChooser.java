package com.zhoug.widgets.dialog.date;

public interface IDateChooser {
    void show();
    IDateChooser setTitle(String title);
    void dismiss();
    void setCanceledOnTouchOutside(boolean canceled);
    void setCancelable(boolean canceled);
    IDateChooser setYear(String year);
    IDateChooser setMonth(String month);
    IDateChooser setDay(String day);
    IDateChooser setMaxYear(String maxYear);


    IDateChooser setCallback(Callback callback);

    interface Callback{
        void onChange(String year, String month, String day);
    }
}
