package com.company.tyxo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;

import com.company.tyxo.R;
import com.company.tyxo.bean.ImageFuli;
import com.company.tyxo.widget.ExtendedViewPager;

import io.realm.Realm;
import io.realm.RealmResults;

/** 来自viewpager 已经舍弃*/
public class ImageViewerActivity2 extends AppCompatActivity {

    Realm realm;
    private RealmResults<ImageFuli> images;
    private int currentIndex;
    private ExtendedViewPager mViewPager;
    private int type;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewpager);
        mViewPager = (ExtendedViewPager) findViewById(R.id.view_pager);

        currentIndex = getIntent().getIntExtra("CURRENT_INDEX", 0);
        type = getIntent().getIntExtra("TYPE",0);

        realm=Realm.getInstance(this);
        images=realm.where(ImageFuli.class).equalTo("type",type).findAll();

        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        Log.i("tyxo", "ImageViewerActivity  onCreate ");

    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewPager.setCurrentItem(currentIndex);
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Fragment getItem(int position) {
//            return ImageViewerFragment.newInstance(images.get(position).getUrl());
            return null;
        }
    }
}
