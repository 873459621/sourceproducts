package cn.edu.njupt.sourceproducts.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.edu.njupt.sourceproducts.R;
import cn.edu.njupt.sourceproducts.engine.ConstantValue;
import cn.edu.njupt.sourceproducts.utils.HttpUtils;
import cn.edu.njupt.sourceproducts.utils.MD5Utils;
import cn.edu.njupt.sourceproducts.utils.SPUtils;
import cn.edu.njupt.sourceproducts.utils.ToastUtils;
import cn.edu.njupt.sourceproducts.view.InputItemView;

/**
 * 显示登录界面的Activity
 * 
 * @author hhw
 */
public class LoginActivity extends Activity {

	private InputItemView iiv_username;
	private InputItemView iiv_password;
	private Button btn_login;
	private TextView tv_regist;

	private String mUsername;
	private String mPassword;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			ToastUtils.show(getApplicationContext(), (String) msg.obj);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initUI();
	}

	/**
	 * 初始化UI控件
	 */
	private void initUI() {
		iiv_username = (InputItemView) findViewById(R.id.iiv_username);
		iiv_password = (InputItemView) findViewById(R.id.iiv_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		tv_regist = (TextView) findViewById(R.id.tv_regist);

		mUsername = SPUtils.getString(this, ConstantValue.USERNAME, null);
		iiv_username.setText(mUsername);

		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkET()) {
					login();
				}
			}
		});

		tv_regist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						RegistActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * 用户登录
	 */
	private void login() {
		new Thread() {

			@Override
			public void run() {
				String path = ConstantValue.IP_ADDRESS + "/LoginServlet";
				String data = "username=" + mUsername + "&password="
						+ mPassword;

				String result = HttpUtils.getStringByPost(path, data);

				if (result.equals("user")) {
					Message msg = Message.obtain();
					msg.obj = "用户不存在！";
					mHandler.sendMessage(msg);

				} else if (result.equals("pwd")) {
					Message msg = Message.obtain();
					msg.obj = "密码错误！";
					mHandler.sendMessage(msg);

				} else {
					try {
						JSONObject obj = new JSONObject(result);
						int uid = obj.getInt("uid");
						String username = obj.getString("username");
						String password = obj.getString("password");

						SPUtils.putInt(getApplicationContext(),
								ConstantValue.UID, uid);
						SPUtils.putString(getApplicationContext(),
								ConstantValue.USERNAME, username);
						SPUtils.putString(getApplicationContext(),
								ConstantValue.PASSWORD,
								MD5Utils.encode(password));
						SPUtils.putBoolean(getApplicationContext(),
								ConstantValue.HAS_LOGGED_IN, true);

						setResult(10,
								new Intent().putExtra("username", username));

						Message msg = Message.obtain();
						msg.obj = "登录成功！";
						mHandler.sendMessage(msg);

						finish();

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	/**
	 * 检查用户名密码是否为空，同时初始化mUsername和mPassword的值
	 * 
	 * @return 返回true代表没有问题，返回false代表有问题
	 */
	private boolean checkET() {
		mUsername = iiv_username.getText();
		mPassword = iiv_password.getText();

		if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)) {
			ToastUtils.show(getApplicationContext(), "用户名或密码不能为空！");
			return false;
		}

		return true;
	}

}
