package com.zc.democoolwidget.casetotal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.zc.democoolwidget.R;
import com.zc.democoolwidget.casetotal.customer.CanvasViewActivity;
import com.zc.democoolwidget.casetotal.loadingview.LoadingViewActivity;

public class MainCaseTotalActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_casetotal_main);
		findViewById(R.id.bt_customer_view).setOnClickListener(this);
		findViewById(R.id.bt_popupWindow).setOnClickListener(this);
		findViewById(R.id.bt_rocket).setOnClickListener(this);
		findViewById(R.id.bt_definiteSwitch).setOnClickListener(this);
		findViewById(R.id.bt_myViewPage).setOnClickListener(this);
		findViewById(R.id.bt_advert).setOnClickListener(this);
		findViewById(R.id.bt_water).setOnClickListener(this);
		findViewById(R.id.bt_loading_view).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
			case R.id.bt_customer_view://自定义View的相关内容练习
				intent = new Intent(this, CanvasViewActivity.class);
				break;
			case R.id.bt_popupWindow:
				intent = new Intent(this,PwActivity.class);
				break;
			case R.id.bt_rocket:
				intent = new Intent(this,RocketActivity.class);
				break;
			case R.id.bt_definiteSwitch:
				intent = new Intent(this,MyActivity.class);
				break;
			case R.id.bt_advert:
				intent = new Intent(this,AdvertActivity.class);
				break;
			case R.id.bt_myViewPage:
				intent = new Intent(this,MyViewPageActivity.class);
				break;
			case R.id.bt_water:
				intent = new Intent(this,WaterActivity.class);
				break;
			case R.id.bt_loading_view:
				intent = new Intent(this,LoadingViewActivity.class);
				break;
		}
		startActivity(intent);

	}


}
