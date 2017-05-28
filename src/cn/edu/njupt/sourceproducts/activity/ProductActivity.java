package cn.edu.njupt.sourceproducts.activity;

import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.edu.njupt.sourceproducts.R;
import cn.edu.njupt.sourceproducts.engine.ConstantValue;

/**
 * 显示产品信息界面的Activity
 * 
 * @author hhw
 * 
 */
public class ProductActivity extends Activity {

	private SmartImageView siv_image;
	private EditText et_number;
	private ImageButton ib_minus;
	private ImageButton ib_add;
	private TextView tv_pname;
	private TextView tv_price;
	private TextView tv_des;
	private TextView tv_location;
	private TextView tv_shopcart;
	private TextView tv_buy;

	private String mPid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);

		initUI();
		initData();
	}

	/**
	 * 初始化显示的数据
	 */
	private void initData() {
		Intent intent = getIntent();

		mPid = intent.getStringExtra("pid");

		String url = ConstantValue.IP_ADDRESS + "/"
				+ intent.getStringExtra("image");
		siv_image.setImageUrl(url);

		et_number.setText("0");
		tv_pname.setText(intent.getStringExtra("pname"));
		tv_price.setText("¥ " + intent.getDoubleExtra("price", 0));
		tv_des.setText(intent.getStringExtra("des"));
	}

	/**
	 * 初始化UI控件
	 */
	private void initUI() {
		siv_image = (SmartImageView) findViewById(R.id.siv_image);
		et_number = (EditText) findViewById(R.id.et_number);
		ib_minus = (ImageButton) findViewById(R.id.ib_minus);
		ib_add = (ImageButton) findViewById(R.id.ib_add);
		tv_pname = (TextView) findViewById(R.id.tv_pname);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_des = (TextView) findViewById(R.id.tv_des);
		tv_location = (TextView) findViewById(R.id.tv_location);
		tv_shopcart = (TextView) findViewById(R.id.tv_shopcart);
		tv_buy = (TextView) findViewById(R.id.tv_buy);

		ib_minus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int number = getNumber();
				if (number > 0) {
					et_number.setText(number - 1 + "");
				}
			}
		});

		ib_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				et_number.setText(getNumber() + 1 + "");
			}
		});

		tv_location.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		tv_shopcart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		tv_buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog();
			}
		});
	}

	/**
	 * 获取EditText中的数字
	 * 
	 * @return EditText中的数字
	 */
	private int getNumber() {
		String text = et_number.getText().toString().trim();
		if (TextUtils.isEmpty(text)) {
			return 0;
		}
		return Integer.parseInt(text);
	}

	/**
	 * 显示确认密码的对话框
	 */
	private void showDialog() {
		// TODO Auto-generated method stub
	}

}
