package com.safetaxik;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.safetaxik.util.Utils;

public class SearchActivity extends FragmentActivity {

	Intent	mIntent;
	String	carNo;

	String	c_name, d_name, c_num, d_num;
	TextView	company_name, driver_name, company_num, driver_num;
	ImageView	img_driver, img_left, img_right;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_fragment2);

		mIntent = new Intent(this.getIntent());
		carNo = mIntent.getStringExtra("CARNO");

		init();
	}

	void init() {
		company_name = (TextView) findViewById(R.id.company_name);
		driver_name = (TextView) findViewById(R.id.driver_name);
		company_num = (TextView) findViewById(R.id.company_num);
		driver_num = (TextView) findViewById(R.id.driver_num);
		img_driver = (ImageView) findViewById(R.id.img_driver);

		carNo = carNo.trim();
		if (carNo.length() < 5) {
			carNo = "34아2095";
		}
		String firstCarNo = carNo.substring(0, 2);
		String midCarNo = carNo.substring(2, 3);
		String lastCarNo = carNo.substring(3, 5);

		if (lastCarNo.equals("12")) {
			c_name = "삼안통상";
			img_driver.setImageResource(R.drawable.info_gys);
			driver_name.setText("사장 : 권영선");
			driver_num.setText("서울시 강북구 수유4동 535-13");
		} else if (lastCarNo.equals("87")) {
			c_name = "성진운수";
			img_driver.setImageResource(R.drawable.info_kjo);
			driver_name.setText("사장 : 김정옥");
			driver_num.setText("서울시 도봉구 방학1동 705-11");
		} else if (lastCarNo.equals("53")) {
			c_name = "상연기업";
			img_driver.setImageResource(R.drawable.info_kjo);
			driver_name.setText("사장 : 김정옥");
			driver_num.setText("서울시 도봉구 방학1동 705-11");
		} else if (lastCarNo.equals("10")) {
			c_name = "국제운수";
			img_driver.setImageResource(R.drawable.info_lsd);
			driver_name.setText("사장 : 이성동");
			driver_num.setText("서울시 도봉구 방학1동 705-11");
		} else if (lastCarNo.equals("92")) {
			c_name = "성진운수";
			img_driver.setImageResource(R.drawable.info_ygs);
			driver_name.setText("사장 : 유규상");
			driver_num.setText("서울시 강북구 수유4동 535-13");
		} else if (lastCarNo.equals("81")) {
			c_name = "삼덕상운";
			img_driver.setImageResource(R.drawable.info_kjo);
			driver_name.setText("사장 : 김정옥");
			driver_num.setText("서울시 도봉구 방학1동 705-11");
		} else if (lastCarNo.equals("83")) {
			c_name = "한일택시";
			img_driver.setImageResource(R.drawable.info_lsd);
			driver_name.setText("사장 : 이성동");
			driver_num.setText("서울시 강북구 수유4동 535-13");
		} else if (lastCarNo.equals("45")) {
			c_name = "통운산업";
			img_driver.setImageResource(R.drawable.info_lsd);
			driver_name.setText("사장 : 이성동");
			driver_num.setText("서울시 강북구 수유4동 535-13");
		} else if (lastCarNo.equals("69")) {
			c_name = "불루택시";
			img_driver.setImageResource(R.drawable.info_kjo);
			driver_name.setText("사장 : 김정옥");
			driver_num.setText("서울시 도봉구 방학1동 705-11");
		} else if (lastCarNo.equals("81")) {
			c_name = "천진교통";
			img_driver.setImageResource(R.drawable.info_ygs);
			driver_name.setText("사장 : 유규상");
			driver_num.setText("서울시 강북구 수유4동 535-13");
		} else if (lastCarNo.equals("32")) {
			c_name = "삼일운수";
			img_driver.setImageResource(R.drawable.info_lsd);
			driver_name.setText("사장 : 이성동");
			driver_num.setText("서울시 강북구 수유4동 535-13");
		} else {
			c_name = "국도산업";
			img_driver.setImageResource(R.drawable.info_kjo);
			driver_name.setText("사장 : 김정옥");
			driver_num.setText("서울시 도봉구 방학1동 705-11");
		}
		if ((!midCarNo.equals("아")) && (!midCarNo.equals("바")) && (!midCarNo.equals("사")) && (!midCarNo.equals("자"))) {
			c_name = "미등록택시";
		}
		company_name.setText(c_name);
	}
}
