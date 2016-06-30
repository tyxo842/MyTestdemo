package com.company.tyxo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 显示单例的吐司，能连续快速弹的吐司
 */
public class ToastUtil {

    private static Toast toast;

    /*public static void showToastS(String text) {
        if (toast == null) {
            toast = Toast.makeText(MyApp.getAppContext(), text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }*/

    public static void showToastS(Context mContext, String text) {
        if (toast == null) {
            toast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }


    /*public static void showToastS(MyApp myApp, String text, String activityName) {
        if (AndroidUtil.isCurrentActivityTop(myApp, activityName)) {
            if (toast == null) {
                toast = Toast.makeText(myApp, text, Toast.LENGTH_SHORT);
            }
            toast.setText(text);
            toast.show();
        }
    }

    public static void showToastL(String text) {
        if (toast == null) {
            toast = Toast.makeText(MyApp.getAppContext(), text, Toast.LENGTH_LONG);
        }
        toast.setText(text);
        toast.show();
    }

    public static void showToastL(MyApp myApp, String text) {
        if (toast == null) {
            toast = Toast.makeText(myApp, text, Toast.LENGTH_LONG);
        }
        toast.setText(text);
        toast.show();
    }*/
}
