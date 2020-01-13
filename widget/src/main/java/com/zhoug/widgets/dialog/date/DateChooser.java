package com.zhoug.widgets.dialog.date;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.zhoug.common.utils.TimeUtils;
import com.zhoug.widgets.R;

import java.util.ArrayList;

/**
 * 日期选择器 年月日
 */
public class DateChooser extends Dialog implements IDateChooser, View.OnClickListener, NumberPicker.OnValueChangeListener {
    private static final String TAG = "DateChooser";
    private String mTitle = "选择日期";
    private String mYear = "2019";
    private String mMonth = "01";
    private String mDay = "01";

    private String mMaxYear;

    private Callback mCallback;


    private NumberPicker mNumberPickerYear;
    private NumberPicker mNumberPickerMonth;
    private NumberPicker mNumberPickerDay;
    private TextView mTvTitle;
    private TextView mTvCancel;
    private TextView mTvOk;

    public DateChooser(@NonNull Context context) {
        this(context, R.style.widget_dialog_full);
    }

    public DateChooser(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//必须设置无标题 否则不会匹配内容
        setContentView(R.layout.widget_dialog_date);
        init();
        initDate();
    }

    private void init() {
        setWindowAnimations(R.style.common_anim_window_popu_down);

        mNumberPickerYear = findViewById(R.id.date_numberPicker_year);
        mNumberPickerMonth = findViewById(R.id.date_numberPicker_month);
        mNumberPickerDay = findViewById(R.id.date_numberPicker_day);
        mTvTitle = findViewById(R.id.date_title);
        mTvCancel = findViewById(R.id.date_cancel);
        mTvOk = findViewById(R.id.date_ok);
        mTvCancel.setOnClickListener(this);
        mTvOk.setOnClickListener(this);
        mNumberPickerYear.setOnValueChangedListener(this);
        mNumberPickerMonth.setOnValueChangedListener(this);
    }


    /**
     * 设置选择的年
     *
     * @param year
     */
    private void setSelectedYear(String year) {
        String[] years = mNumberPickerYear.getDisplayedValues();
        for (int i = 0; i < years.length; i++) {
            if (years[i].equals(year)) {
                mNumberPickerYear.setValue(i);
                break;
            }
        }
    }

    /**
     * 设置选择的月
     *
     * @param month
     */
    private void setSelectedMonth(String month) {
        String[] months = mNumberPickerMonth.getDisplayedValues();
        for (int i = 0; i < months.length; i++) {
            if (months[i].equals(month)) {
                mNumberPickerMonth.setValue(i);
                break;
            }
        }
    }

    /**
     * 设置选择的日
     *
     * @param day
     */
    private void setSelectedDay(String day) {
        String[] days = mNumberPickerDay.getDisplayedValues();
        for (int i = 0; i < days.length; i++) {
            if (days[i].equals(day)) {
                mNumberPickerDay.setValue(i);
                break;
            }
        }
    }

    private void initDate() {
        mTvTitle.setText(mTitle);
        //初始化年
        ArrayList<String> yearList = null;
        if (mMaxYear == null) {
            yearList = TimeUtils.getYearList("2030");
        } else {
            yearList = TimeUtils.getYearList(mMaxYear);
        }
        String[] years = new String[yearList.size()];
        yearList.toArray(years);
        mNumberPickerYear.setMinValue(0);
        mNumberPickerYear.setMaxValue(years.length - 1);
        mNumberPickerYear.setDisplayedValues(years);
        setSelectedYear(mYear);

        //初始化月
        ArrayList<String> monthList = TimeUtils.getMonthList();
        String[] months = new String[monthList.size()];
        monthList.toArray(months);
        mNumberPickerMonth.setMinValue(0);
        mNumberPickerMonth.setMaxValue(months.length - 1);
        mNumberPickerMonth.setDisplayedValues(months);
        setSelectedMonth(mMonth);
        //初始化日
        initDay(mYear, mMonth);
        mNumberPickerDay.setMinValue(0);
        setSelectedDay(mDay);
    }

    ArrayList<String> dayList;//天数

    private void initDay(String year, String month) {
        if (dayList == null) {
            dayList = new ArrayList<>();
        }
        dayList.clear();
        TimeUtils.getDays(year, month, dayList);
        String[] days = new String[dayList.size()];
        dayList.toArray(days);
//        Log.i(TAG, "initDay: ");
        mNumberPickerDay.setDisplayedValues(null);//解决ArrayIndexOutOfBoundsException
        mNumberPickerDay.setMaxValue(days.length - 1);
        mNumberPickerDay.setDisplayedValues(days);

    }

    @Override
    public IDateChooser setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    @Override
    public IDateChooser setYear(String year) {
        this.mYear = year;
        return this;
    }

    @Override
    public IDateChooser setMonth(String month) {
        this.mMonth = month;
        return this;
    }

    @Override
    public IDateChooser setDay(String day) {
        this.mDay = day;
        return this;
    }

    @Override
    public IDateChooser setMaxYear(String maxYear) {
        this.mMaxYear = maxYear;
        return this;
    }

    @Override
    public IDateChooser setCallback(Callback callback) {
        this.mCallback = callback;
        return this;
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

    /**
     * 设置动画
     *
     * @param resId R.style.anim_window_popu动 画
     */
    public void setWindowAnimations(@StyleRes int resId) {
        this.getWindow().setWindowAnimations(resId);
    }

    @Override
    public void show() {
        super.show();
        setAttributes(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        setSelectedYear(mYear);
        setSelectedMonth(mMonth);
        setSelectedDay(mDay);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.date_cancel) {
            dismiss();
        } else if (v.getId() == R.id.date_ok) {
            mYear = mNumberPickerYear.getDisplayedValues()[mNumberPickerYear.getValue()];
            mMonth = mNumberPickerMonth.getDisplayedValues()[mNumberPickerMonth.getValue()];
            mDay = mNumberPickerDay.getDisplayedValues()[mNumberPickerDay.getValue()];
            dismiss();
            mCallback.onChange(mYear, mMonth, mDay);


//            Toast.makeText(getContext(),year+"-"+month+"-"+day , Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        int id = picker.getId();
        if (id == R.id.date_numberPicker_year) {
            //年
            //当前选择的月为2月
            if (mNumberPickerMonth.getValue() == 1) {
                String[] years = picker.getDisplayedValues();
                String oldyear = years[oldVal];
                String newyear = years[newVal];
                if (TimeUtils.isLeapYear(oldyear) != TimeUtils.isLeapYear(newyear)) {
                    initDay(newyear, "2");
                }
            }
        } else if (id == R.id.date_numberPicker_month) {
            //月
            String year = mNumberPickerYear.getDisplayedValues()[mNumberPickerYear.getValue()];
            initDay(year, (newVal + 1) + "");
        }
    }
}
