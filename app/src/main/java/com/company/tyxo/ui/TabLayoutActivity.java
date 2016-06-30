package com.company.tyxo.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.company.tyxo.R;
import com.company.tyxo.ui.fragment.FragmentNormal;

/**
 * Created by LiYang on 2016/6/28 18: 55.
 * Mail      1577441454@qq.com
 * Describe : com.android.support:design...
 */
public class TabLayoutActivity extends AppCompatActivity {

    private ViewPager vp_viewpager;
    private TabLayout tab_tablayout;

    private SparseArray<Fragment> mFragments;
    private MyTabAdapter myTabAdapter ;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);

        toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        tab_tablayout = (TabLayout) findViewById(R.id.tab_tablayout);
        vp_viewpager = (ViewPager)findViewById(R.id.vp_viewpager);

        initView();
        initListener();
        initData();
    }

    private void initView() {

        toolbar.setTitle("");//居中设置二中之一
//        toolbar.setLogo(R.drawable.ic_launcher);
//        toolbar.setSubtitle("Sub title");
        // Navigation Icon 要設定在 setSupoortActionBar 才有作用
        // 否則會出現 back button
        toolbar.setNavigationIcon(R.drawable.search_icon);
        toolbar.inflateMenu(R.menu.menu_main);//设置右上角的填充菜单
        setSupportActionBar(toolbar);//添加上之后,右上角menu就没了  居中设置二中之二

        tab_tablayout.setTabMode(TabLayout.MODE_FIXED);//无论多少个都挤在屏内,均分
//        tab_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);//很多个,会排到屏外,如果少会空出留白
        tab_tablayout.setupWithViewPager(vp_viewpager);
        tab_tablayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayout linearLayout = (LinearLayout) tab_tablayout.getChildAt(0);
                linearLayout.getChildAt(0).setBackgroundResource(R.drawable.tablayout_tab_left_bg);
                linearLayout.getChildAt(1).setBackgroundResource(R.drawable.tablayout_tab_center_bg);
                linearLayout.getChildAt(2).setBackgroundResource(R.drawable.tablayout_tab_right_bg);
            }
        },10);
    }

    private void initListener() {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

    private void initData() {
        mFragments = new SparseArray<>();
        mFragments.append(0,new FragmentNormal());
        mFragments.append(1,new FragmentNormal());
        mFragments.append(2,new FragmentNormal());
        mFragments.append(3,new FragmentNormal());
//        mFragments.append(4,new FragmentNormal());
//        mFragments.append(5,new FragmentNormal());
//        mFragments.append(6,new FragmentNormal());
//        mFragments.append(7,new FragmentNormal());
        myTabAdapter = new MyTabAdapter(getSupportFragmentManager());
        vp_viewpager.setAdapter(myTabAdapter);
        vp_viewpager.setOffscreenPageLimit(2);//预加载个数,少的可以用,防止快速切换卡顿
    }

    private class MyTabAdapter extends FragmentPagerAdapter{

        public MyTabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "标题1";
                case 1:
                    return "标题2";
                case 2:
                    return "标题3";
                case 3:
                    return "标题4";
//                case 4:
//                    return "标题5";
//                case 5:
//                    return "标题6";
//                case 6:
//                    return "标题7";
//                case 7:
//                    return "标题8";
            }
            return super.getPageTitle(position);
//            return null;
        }
    }
}






















