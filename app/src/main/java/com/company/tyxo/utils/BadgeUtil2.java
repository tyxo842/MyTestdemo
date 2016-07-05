package com.company.tyxo.utils;

/**
 * 反编译的qq 5.0 代码有错误,未改
 * 涉及到的类 : hyr , ThreadManager
 *
 * 未用到,可以删除此类,来自反编译的qq5.0,内含错误未改完(混淆后)
 */
public class BadgeUtil2 {

    private static Boolean boo = null; // 反编译 开始为a
    public static final String a = "android.intent.action.APPLICATION_MESSAGE_QUERY";
    public static final String b = "android.intent.action.APPLICATION_MESSAGE_UPDATE";
    public static final String c = "android.intent.extra.update_application_message_text";
    public static final String d = "android.intent.extra.update_application_component_name";
    public static final String e = "samsung";
    public static final String f = "Xiaomi";
    public static final String g = "Sony Ericsson";

    /*public static String a(Context paramContext) {
        PackageManager localPackageManager = paramContext.getPackageManager();
        Intent localIntent = new Intent("android.intent.action.MAIN");
        localIntent.addCategory("android.intent.category.LAUNCHER");
        try {
            Iterator localIterator = localPackageManager.queryIntentActivities(localIntent, 0).iterator();
            while (localIterator.hasNext()) {
                ResolveInfo localResolveInfo = (ResolveInfo) localIterator.next();
                if (localResolveInfo.activityInfo.applicationInfo.packageName.equalsIgnoreCase(paramContext.getPackageName())) {
                    String str = localResolveInfo.activityInfo.name;
                    return str;
                }
            }
        } catch (Exception localException) {
            return null;
        }
        return null;
    }

    public static void a(Context paramContext, int paramInt) {
        Intent localIntent = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
        localIntent.putExtra("android.intent.extra.update_application_component_name", "com.tencent.mobileqq/.activity.SplashActivity");
        String str = "";
        if (paramInt > 0)
            if (paramInt > 99)
                str = "99";
        while (true) {
            localIntent.putExtra("android.intent.extra.update_application_message_text", str);
            paramContext.sendBroadcast(localIntent);
            return;
            str = paramInt + "";
            continue;
            str = "";
        }
    }

    public static void b(Context paramContext, int paramInt) {
        ThreadManager.b(new hyr(paramInt, paramContext));
    }

    public static void c(Context paramContext, int paramInt) {
        Intent localIntent = new Intent();
        String str1 = a(paramContext);
        if (str1 == null)
            return;
        if (paramInt < 1) {
            str2 = "";
            localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", false);
            localIntent.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
            localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", str1);
            localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", str2);
            localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", paramContext.getPackageName());
            paramContext.sendBroadcast(localIntent);
            return;
        }
        if (paramInt > 99) ;
        for (String str2 = "99"; ; str2 = paramInt + "") {
            localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", true);
            break;
        }
    }

    public static void d(Context paramContext, int paramInt) {
        if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi"))
            a(paramContext, paramInt);
        while (!Build.MANUFACTURER.equalsIgnoreCase("samsung"))
            return;
        b(paramContext, paramInt);
    }*/
}
