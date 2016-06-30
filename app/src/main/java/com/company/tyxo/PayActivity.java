package com.company.tyxo;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import com.company.tyxo.constants.Constants;

import org.json.JSONObject;

public class PayActivity extends Activity {
	
	private IWXAPI api;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay);
		api = WXAPIFactory.createWXAPI(getApplicationContext(), Constants.APP_ID);
		final byte[] result = getIntent().getByteArrayExtra("result");
		((TextView)findViewById(R.id.tv_prepay_info)).setText(new String(result));

		Button appayBtn = (Button) findViewById(R.id.bt_pay);
		appayBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        try{
					byte[] buf = result;
					if (result != null && buf.length > 0) {
						String content = new String(buf);
						Log.e("get server pay params:",content);
			        	JSONObject json = new JSONObject(content);

						if(null != json && !json.has("retcode") ){
							System.out.println(json.has("retcode")+"hello");
							PayReq req = new PayReq();
							req.appId			= json.getString("appid");
							req.partnerId		= json.getString("partnerid");
							req.prepayId		= json.getString("prepayid");
							req.nonceStr		= json.getString("noncestr");
							req.timeStamp		= json.getString("timestamp");
							req.packageValue	= json.getString("package");
							req.sign			= json.getString("sign");
							req.extData			= "app data"; // optional
							// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
							api.sendReq(req);
						}else{
							Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
							Toast.makeText(PayActivity.this, "返回错误"+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
						}
					}else{
						Log.d("PAY_GET", "服务器请求错误");
						Toast.makeText(PayActivity.this, "服务器请求错误", Toast.LENGTH_SHORT).show();
					}
				} catch(Exception e){
					Log.e("PAY_GET", "异常："+e.getMessage());
					Toast.makeText(PayActivity.this, "异常："+e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});

	}
	
}
