package com.company.tyxo.ui.changeicon;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;

import com.company.tyxo.MainActivity;
import com.company.tyxo.R;

import java.util.List;

/**
 * Created by LiYang on 2016/6/30 18: 40.
 * Mail      1577441454@qq.com
 * Describe :
 */
public class ActivityAlias1 extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
        initData();
    }

    private void initView() {

    }

    private void initListener() {

    }

    private void initData() {

    }

    /** android动态修改app桌面icon① 暂时未完成*/
    // TODO: 2016/7/1  动态修改app桌面icon    原理 : kill掉launcher，等launcher重启后，icon就替换了
    private String ACTIVITY_ALIAS_1 = "ACTIVITY_ALIAS_1";
    private String ACTIVITY_ALIAS_2 = "ACTIVITY_ALIAS_2";

    private void setIcon(String activity_alias) {

        Context ctx = getApplicationContext();
        PackageManager pm = ctx.getPackageManager();
        ActivityManager am = (ActivityManager) ctx.getSystemService(Activity.ACTIVITY_SERVICE);

        pm.setComponentEnabledSetting(
                new ComponentName(ctx, ACTIVITY_ALIAS_1),
                ACTIVITY_ALIAS_1.equals(activity_alias) ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(
                new ComponentName(ctx, ACTIVITY_ALIAS_2),
                ACTIVITY_ALIAS_2.equals(activity_alias) ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        List<ResolveInfo> resolves = pm.queryIntentActivities(i, 0);
        for (ResolveInfo res : resolves) {
            if (res.activityInfo != null) {
                am.killBackgroundProcesses(res.activityInfo.packageName);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_edit:
             /** 做成 Android 更新app未读消息数目在app图标的办法 ②*/
            {
                String launcherClassName = getLauncherClassName(this);
                Intent intent = new Intent("android.intent.action.BADGE.COUNT.UPDATE");
                intent.putExtra("badge_count",6);
                intent.putExtra("badge_count_package_name", this.getPackageName());
                intent.putExtra("badge_count_class_name", MainActivity.class.getName());
//                intent.putExtra("badge_count_class_name", launcherClassName);
                this.sendBroadcast(intent);
            }
                break;
        }
    }
    private static String getLauncherClassName(Context context) {
        PackageManager packageManager = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setPackage(context.getPackageName());
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        ResolveInfo info = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);

        if (info == null) {
            info = packageManager.resolveActivity(intent, 0);
        }

        return info.activityInfo.name;
    }
}
