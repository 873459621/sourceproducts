package cn.edu.njupt.sourceproducts.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.edu.njupt.sourceproducts.R;
import cn.edu.njupt.sourceproducts.dao.OrderDao;
import cn.edu.njupt.sourceproducts.db.dao.ItemDao;
import cn.edu.njupt.sourceproducts.db.domain.Item;
import cn.edu.njupt.sourceproducts.engine.ConstantValue;
import cn.edu.njupt.sourceproducts.utils.MD5Utils;
import cn.edu.njupt.sourceproducts.utils.SPUtils;
import cn.edu.njupt.sourceproducts.utils.ToastUtils;

import com.loopj.android.image.SmartImageView;

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

	private int mPid;
	private double mPrice;
	private String mPname;
	private String mDes;
	private String mImage;
	private double mTotal;
	private int mNumber;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			ToastUtils.show(getApplicationContext(), "下单成功！");
			finish();
		};
	};

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

		mPid = intent.getIntExtra("pid", 0);
		mPname = intent.getStringExtra("pname");
		mPrice = intent.getDoubleExtra("price", 0.0);
		mDes = intent.getStringExtra("des");
		mImage = intent.getStringExtra("image");

		String url = ConstantValue.IP_ADDRESS + "/" + mImage;
		siv_image.setImageUrl(url);

		et_number.setText("0");
		tv_pname.setText(mPname);
		tv_price.setText("¥ " + mPrice);
		tv_des.setText(mDes);
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
				Intent intent = new Intent(getApplicationContext(),
						LocationActivity.class);
				intent.putExtra("pid", mPid);
				startActivity(intent);
			}
		});

		tv_shopcart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int number = getNumber();
				if (number <= 0) {
					ToastUtils.show(getApplicationContext(), "产品数目不能为0！");
				} else {
					ItemDao itemDao = ItemDao
							.getInstance(getApplicationContext());

					int pNumber = itemDao.getNumber(mPid);
					if (pNumber > 0) {
						pNumber += number;
						itemDao.update(mPid, pNumber, pNumber * mPrice);
					} else {
						itemDao.insert(new Item(mPid, mPname, mPrice, mImage,
								mDes, number, number * mPrice));
					}

					ToastUtils.show(getApplicationContext(), "加入购物车成功！");

					finish();
				}
			}
		});

		tv_buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (getNumber() <= 0) {
					ToastUtils.show(getApplicationContext(), "产品数目不能为0！");
				} else {
					boolean has_logged_in = SPUtils.getBoolean(
							getApplicationContext(),
							ConstantValue.HAS_LOGGED_IN, false);
					if (has_logged_in) {
						showDialog();
					} else {
						startActivity(new Intent(getApplicationContext(),
								LoginActivity.class));
						ToastUtils.show(getApplicationContext(), "请先登录！");
					}
				}
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
		Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		final View view = View.inflate(this, R.layout.dialog_confirm_pwd, null);
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();

		TextView tv_total = (TextView) view.findViewById(R.id.tv_total);
		Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

		mNumber = getNumber();
		mTotal = mPrice * mNumber;

		tv_total.setText("¥ " + mTotal);

		btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText et_confirm_pwd = (EditText) view
						.findViewById(R.id.et_confirm_pwd);
				String confirmPwd = et_confirm_pwd.getText().toString().trim();

				if (!TextUtils.isEmpty(confirmPwd)) {
					confirmPwd = MD5Utils.encode(confirmPwd);
					String pwd = SPUtils.getString(getApplicationContext(),
							ConstantValue.PASSWORD, null);

					if (confirmPwd.equals(pwd)) {
						summitOrder();
						dialog.dismiss();
					} else {
						ToastUtils.show(getApplicationContext(), "密码不一致！");
					}
				} else {
					ToastUtils.show(getApplicationContext(), "请输入密码！");
				}
			}
		});

		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	/**
	 * 提交订单到服务端
	 */
	private void summitOrder() {
		new Thread() {

			public void run() {
				int uid = SPUtils.getInt(getApplicationContext(),
						ConstantValue.UID, 0);

				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put("uid", uid);
					jsonObject.put("total", mTotal);
					JSONArray jsonArray = new JSONArray();
					JSONObject obj = new JSONObject();
					obj.put("pid", mPid);
					obj.put("number", mNumber);
					obj.put("subtotal", mTotal);
					jsonArray.put(obj);
					jsonObject.put("itemList", jsonArray);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				String data = "json=" + jsonObject.toString();

				OrderDao orderDao = OrderDao.getInstance();
				String result = orderDao.summitOrder(data);
				if (!TextUtils.isEmpty(result)) {
					mHandler.sendEmptyMessage(0);
				}
			};
		}.start();
	}

}
