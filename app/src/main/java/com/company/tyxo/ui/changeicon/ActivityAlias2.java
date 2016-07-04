package com.company.tyxo.ui.changeicon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.company.tyxo.R;

/**
 * Created by LiYang on 2016/6/30 18: 40.
 * Mail      1577441454@qq.com
 * Describe :
 */
public class ActivityAlias2 extends Activity implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order_add_minus);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imbtn_add:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
