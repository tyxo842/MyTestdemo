package com.company.tyxo.ui.manager;

/**
 * Created by LiYang on 2016/7/4 15: 27.
 * Mail      1577441454@qq.com
 * Describe :
 */
public class hyr {

    /*private static Context jdField_a_of_type_AndroidContentContext;
    private static int jdField_a_of_type_Int;

    public hyr(int paramInt, Context paramContext) {
        this.jdField_a_of_type_AndroidContentContext = paramContext;
        this.jdField_a_of_type_Int = paramInt;
    }

    public void run() {
        int i = this.jdField_a_of_type_Int;
        try {
            if (BadgeUtil2.a() == null) {
                Uri localUri = Uri.parse("content://com.sec.badge/apps");
                if (this.jdField_a_of_type_AndroidContentContext.getContentResolver().query(localUri, null, null, null, null) == null) {
                    BadgeUtil2.a();
                    Boolean.valueOf(false);
                    return;
                }
                BadgeUtil2.a();
                Boolean.valueOf(true);
            }
            if (BadgeUtil2.a().booleanValue()) {
                String str = BadgeUtil2.a(this.jdField_a_of_type_AndroidContentContext);
                if (str != null)
                    if (i > 99) {
                        i = 99;
                        Intent localIntent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
                        localIntent.putExtra("badge_count", i);
                        localIntent.putExtra("badge_count_package_name", this.jdField_a_of_type_AndroidContentContext.getPackageName());
                        localIntent.putExtra("badge_count_class_name", str);
                        this.jdField_a_of_type_AndroidContentContext.sendBroadcast(localIntent);
                        return;
                    }
            }
        } catch (Throwable localThrowable) {
            *//*int j;
            while (QLog.isColorLevel()) {
                QLog.d("BadgeUtil", 2, "samsung badge get a  crash");
                return;
                j = i;
            }*//*
        }
    }*/
}