package com.company.tyxo;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.company.tyxo.constants.Constants;
import com.company.tyxo.ui.ImageViewerActivity;
import com.company.tyxo.ui.TabLayoutActivity;
import com.company.tyxo.ui.WebViewActivity;
import com.company.tyxo.util.DialogHelper;
import com.company.tyxo.util.HttpUtil;
import com.company.tyxo.util.MyAsyncTask;
import com.company.tyxo.utils.BadgeUtil;
import com.company.tyxo.utils.ToastUtil;
import com.company.tyxo.utils.log.HLog;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * 已有功能:
 *  微信支付
 *  glide图片加载 --> 看图+长按保+单击
 *  webview
 *  tabLayout
 *  HLog
 *  icon 上角数字(未读数字) --->三个方法,均未起作用 ①(ActivityAlias2); ② (ShortcutBadger-master); ③.
 *
 * */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {

    private Button createPrePay ,onkeyPay, btn_test, btn_test2,tv_webview, btn_tablayout;

    private ImageView imageView;
    private EditText et_product_id;
    private String url; // webView 加载的url

    private Button btnSet, btnClean ,btn_iconnum_set2;//icon 未读消息 -- 不起作用

    /** 图标 上角 未读消息 ④ */
    private EditText numInput;
    private Button button, removeBadgeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createPrePay = (Button) findViewById(R.id.bt_create_prepaylist);
        onkeyPay = (Button) findViewById(R.id.bt_onkey_pay);
        btn_test = (Button) findViewById(R.id.btn_test);
        btn_test2 = (Button) findViewById(R.id.btn_test2);
        tv_webview = (Button) findViewById(R.id.tv_webview);
        btn_tablayout = (Button) findViewById(R.id.btn_tablayout);

        imageView = (ImageView) findViewById(R.id.im_imageview2);
        // imageView = (ImageView) findViewById(R.id.im_imageview);
        et_product_id = (EditText) findViewById(R.id.et_product_id);

        btnSet = (Button) findViewById(R.id.btn_iconnum_set);
        btnClean = (Button) findViewById(R.id.btn_iconnum_clean);
        btn_iconnum_set2 = (Button) findViewById(R.id.btn_iconnum_set2);

        numInput = (EditText) findViewById(R.id.numInput);
        button = (Button) findViewById(R.id.btnSetBadge);
        removeBadgeBtn = (Button) findViewById(R.id.btnRemoveBadge);

        // String url = "http://img4.imgtn.bdimg.com/it/u=3656820678,353780200&fm=11&gp=0.jpg";
        url = "http://b164.photo.store.qq.com/psb?/V11IXfXu1OApUM/bRbBm8FNRXVXb*BGLmN4IM2UtDkHFiAuLRcuGcv7RRQ!/b/dL54w2GxAQAA&bo=IANYAgAAAAABAF4!&rf=viewer_4";

        createPrePay.setOnClickListener(this);
        onkeyPay.setOnClickListener(this);
        btn_test.setOnClickListener(this);
        btn_test2.setOnClickListener(this);
        tv_webview.setOnClickListener(this);
        btn_tablayout.setOnClickListener(this);

        imageView.setOnClickListener(this);
        imageView.setOnLongClickListener(this);
        et_product_id.setOnKeyListener(onKeyListener);

        btnSet.setOnClickListener(this);
        btnClean.setOnClickListener(this);
        btn_iconnum_set2.setOnClickListener(this);

        button.setOnClickListener(this);
        removeBadgeBtn.setOnClickListener(this);
        initIntent();

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 强制横屏设置
    }

    /** 横竖屏切换检测 */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE) {
            HLog.i("tyxo", "onConfigurationChanged 屏幕  横屏");
        }else if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
            HLog.i("tyxo", "onConfigurationChanged 屏幕  竖屏");
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSetBadge:      /** ④ : 改变icon上角数字 */
                setIconNumber();
                break;
            case R.id.btnRemoveBadge:   /** ④ : 改变icon上角数字 */
                clearIconNumber();
                break;
            case R.id.btn_iconnum_set:  /** ② : 改变icon上角数字 */
                BadgeUtil.setBadgeCount(getApplicationContext(), 35);
                break;
            case R.id.btn_iconnum_clean: // 恢复icon上角数字
                BadgeUtil.resetBadgeCount(getApplicationContext());
                break;
            case R.id.btn_iconnum_set2: /** ③ : 改变icon上角数字 */
                changeIconSecond();
                break;
            case R.id.bt_create_prepaylist:
                createPrePayList();
                break;
            case R.id.bt_onkey_pay:

                break;
            case R.id.btn_tablayout:
            {
                Intent intent = new Intent(this, TabLayoutActivity.class);
                startActivity(intent);
            }
                break;
            case R.id.tv_webview:
            {
                Intent intent = new Intent(this, WebViewActivity.class);
                startActivity(intent);
            }
                break;
            case R.id.im_imageview2:
                HLog.i("tyxo", "imageview 点击 ImageViewerActivity");

                Intent intent = new Intent(this, ImageViewerActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
//                Log.i("tyxo","currentTime : "this.getSystemService())
                break;
            case R.id.btn_test:
                // 汉字[..]..[..](一碰到汉字就停) list1
                // list1按汉字逐个分割,汉字String,截取出[..]-->list2
                // list3逐个添加String,list2
                // 最后list3就是按顺序分好的
                String s = "美容习惯一：早晚两杯白开水[media t=\"img\" s=\"Uploads/img/20160319/56ecb67714e91.png\" w=\"544\" h=\"438\" /]" +
                        "美容习惯二：一瓶矿泉水一定是要名副其实的矿泉水[media t=\"img\" s=\"Uploads/img/20160319/56ecb67714e92.png\" w=\"544\" h=\"438\" /]" +
                        "[media t=\"img\" s=\"Uploads/img/20160319/56ecb67714e93.png\" w=\"544\" h=\"438\" /]";
                String regEx = "\\[((?!\\[).)*]";
                Pattern pat = Pattern.compile(regEx);
                Matcher mat = pat.matcher(s);

                String[] strs = s.split(regEx);
                for (int i = 0; i < strs.length; i++) {
                    HLog.i("tyxo","分割出来的内容: "+ strs[i]);
                }

                while(mat.find()){
                    HLog.i("tyxo","截取出来的内容: "+ mat.group());
                }
                break;

            case R.id.btn_test2:
                for (int i = 0; i < 10; i++) {
                    int selector = i;
                    HLog.i("tyxo","for当前i的值: "+i);

                    nextMethod(i);
                }

                /*Glide.with(getApplicationContext())
                        .load(url)
                        .placeholder(R.mipmap.loading) //占位符 也就是加载中的图片，可放个gif
                        .error(R.mipmap.icon_zanwu) //失败图片
                        .crossFade() // 添加图片淡入加载的效果
                        .into(target);*/
                /*Glide.with(getApplicationContext())
                        .load(url)
                        .placeholder(R.drawable.loading) //占位符 也就是加载中的图片，可放个gif
                        .error(R.drawable.icon_zanwu) //失败图片
                        .crossFade() // 添加图片淡入加载的效果
                        .into(target);*/
                Glide.with( this )
                        .load( url )
                        .asBitmap()
                        .placeholder(R.drawable.loading) //占位符 也就是加载中的图片，可放个gif
                        .error(R.drawable.icon_zanwu) //失败图片
                        .into( target ) ;

                Intent intent2 = new Intent(this, ImageViewerActivity.class);
                intent2.putExtra("url", url);
                startActivity(intent2);

                break;
        }
    }

    /** ④ : 改变icon上角数字 start */
    private void initIntent() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        String currentHomePackage = resolveInfo.activityInfo.packageName;

        TextView textViewHomePackage = (TextView) findViewById(R.id.textViewHomePackage);
        textViewHomePackage.setText("launcher:" + currentHomePackage);
    }
    private void clearIconNumber() {
        boolean success = ShortcutBadger.removeCount(MainActivity.this);

        ToastUtil.showToastS(getApplicationContext(), "success=" + success);
    }
    private void setIconNumber() {
        int badgeCount = 0;
        try {
            badgeCount = Integer.parseInt(numInput.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Error input", Toast.LENGTH_SHORT).show();
        }

        boolean success = ShortcutBadger.applyCount(MainActivity.this, badgeCount);

        ToastUtil.showToastS(getApplicationContext(),"Set count=" + badgeCount + ", success=" + success);
    }/** ④ : 改变icon上角数字 end */



    /** ③ : 更改图标 创建快捷方式         ps: 不管用 */
    private void changeIconSecond() {

        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        BitmapDrawable iconBitmapDrawablel = null;

        // 获取应用基本信息
        String label = this.getPackageName();
        PackageManager packageManager = getPackageManager();
        try {
            iconBitmapDrawablel = (BitmapDrawable) packageManager.getApplicationIcon(label);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // 设置属性
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, label);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON, iconBitmapDrawablel.getBitmap());
        // 是否允许重复创建 -- false --> 否
        shortcut.putExtra("duplicate", false);
        // 设置启动程序
        ComponentName comp = new ComponentName(label, "." + this.getLocalClassName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));

        sendBroadcast(shortcut);
    }

    // 手机软键盘 展开与关闭
    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            HLog.i("tyxo","keyCode : "+keyCode);
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                //隐藏软键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(inputMethodManager.isActive()){
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                return true;
            }
            return false;
        }
    };

    /** 加载图片 */
    /*private SimpleTarget target = new SimpleTarget<Bitmap>() {

        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            //图片加载完成
            imageView.setImageBitmap(resource);
        }
    };*/
    private SimpleTarget target = new SimpleTarget() {
        @Override
        public void onResourceReady(Object resource, GlideAnimation glideAnimation) {
            //图片加载完成
            imageView.setImageBitmap((Bitmap) resource);//第一/二处会报: java.lang.ClassCastException: com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable cannot be cast to android.graphics.Bitmap
        }
    };

    private void nextMethod(int i) {
        HLog.i("tyxo", "方法处i的值: " + i);
    }

    private void createPrePayList() {
       PrePayAsynTask prePayAsynTask=new PrePayAsynTask();
        prePayAsynTask.execute();
    }

    /** 长按保存事件 */
    @Override
    public boolean onLongClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(new String[]{getResources().getString(R.string.save_picture)}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                imageView.setDrawingCacheEnabled(true);
                Bitmap imageBitmap = imageView.getDrawingCache();
                if (imageBitmap != null) {
                    new SaveImageTask().execute(imageBitmap);
                }
            }
        });
        builder.show();

        return true;
    }

    /** 保存图片 */
    private class SaveImageTask extends AsyncTask<Bitmap, Void, String> {
        @Override
        protected String doInBackground(Bitmap... params) {
        String result = getResources().getString(R.string.save_picture_failed);
        try {
            String sdcard = Environment.getExternalStorageDirectory().toString();

            File file = new File(sdcard + "/Download");
            if (!file.exists()) {
                file.mkdirs();
            }

            File imageFile = new File(file.getAbsolutePath(),new Date().getTime()+".jpg");
            FileOutputStream outStream = null;
            outStream = new FileOutputStream(imageFile);
            Bitmap image = params[0];
            image.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
            result = getResources().getString(R.string.save_picture_success,  file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

        @Override
        protected void onPostExecute(String result) {
            ToastUtil.showToastS(getApplicationContext(),result);
            imageView.setDrawingCacheEnabled(false);
        }
    }

    class PrePayAsynTask extends MyAsyncTask {
        private byte[] result;
        ProgressDialog waitDialog;

        @Override
        public void onPreExecute() {
            waitDialog = DialogHelper.getWaitDialog(MainActivity.this, "请等待...");
            waitDialog.show();
        }

        @Override
        public void onPostExecute() {
            if (result != null) {
                waitDialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                intent.putExtra("result", result);
                startActivity(intent);

            } else {

               waitDialog.dismiss();
            }
        }

        @Override
        public void doInBackground() {
            result = HttpUtil.httpGet(Constants.PREPAYLIST_URL_TEST);
//            result = HttpUtil.httpPost(" ".getBytes(), Constants.PREPAYLIST_URL_TEST);
            HLog.i("tyxo", "HttpUtil result : " + new String(result));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

