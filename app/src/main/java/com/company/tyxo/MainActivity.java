package com.company.tyxo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
import com.company.tyxo.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {

    private Button createPrePay;
    private Button onkeyPay;
    private Button btn_test;
    private Button btn_test2;
    private Button tv_webview;
    private Button btn_tablayout;

    private ImageView imageView;
    private EditText et_product_id;
    private String url; // webView 加载的url

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
//        imageView = (ImageView) findViewById(R.id.im_imageview);
        et_product_id = (EditText) findViewById(R.id.et_product_id);

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

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 强制横屏设置
    }

    /** 横竖屏切换检测 */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("tyxo", "onConfigurationChanged 屏幕  横屏");
        }else if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
            Log.i("tyxo", "onConfigurationChanged 屏幕  竖屏");
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
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
                Log.i("tyxo", "imageview 点击 ImageViewerActivity");

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
                    Log.i("tyxo","分割出来的内容: "+ strs[i]);
                }

                while(mat.find()){
                    Log.i("tyxo","截取出来的内容: "+ mat.group());
                }
                break;

            case R.id.btn_test2:
                for (int i = 0; i < 10; i++) {
                    int selector = i;
                    Log.i("tyxo","for当前i的值: "+i);

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

    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            Log.i("tyxo","keyCode : "+keyCode);
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
        Log.i("tyxo", "方法处i的值: " + i);
    }

    private void createPrePayList() {
       PrePayAsynTask prePayAsynTask=new PrePayAsynTask();
        prePayAsynTask.execute();
    }

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
            Log.i("tyxo", "HttpUtil result : " + new String(result));
        }
    }
}

