package cn.edu.njupt.sourceproducts.activity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
import android.widget.EditText;
import android.widget.TextView;
import cn.edu.njupt.sourceproducts.R;
import cn.edu.njupt.sourceproducts.engine.ConstantValue;
import cn.edu.njupt.sourceproducts.utils.SPUtils;
import cn.edu.njupt.sourceproducts.utils.StreamUtils;
import cn.edu.njupt.sourceproducts.utils.ToastUtils;

/**
 * 显示登录界面的Activity
 * 
 * @author hhw
 */
public class LoginActivity extends Activity {

	private EditText et_username;
	private EditText et_password;
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
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		tv_regist = (TextView) findViewById(R.id.tv_regist);

		mUsername = SPUtils.getString(this, ConstantValue.USERNAME, null);
		et_username.setText(mUsername);

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
				try {
					String path = ConstantValue.IP_ADDRESS + "/LoginServlet";
					String data = "username=" + mUsername + "&password="
							+ mPassword;

					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();

					conn.setRequestMethod("POST");
					conn.setConnectTimeout(5000);
					conn.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded");
					conn.setRequestProperty("Content-Length", data.length()
							+ "");
					conn.setDoOutput(true);

					conn.getOutputStream().write(data.getBytes());

					if (conn.getResponseCode() == 200) {
						InputStream in = conn.getInputStream();
						String result = StreamUtils.streamToString(in);

						if (result.startsWith("user")) {
							Message msg = Message.obtain();
							msg.obj = "用户不存在！";
							mHandler.sendMessage(msg);

						} else if (result.startsWith("pwd")) {
							Message msg = Message.obtain();
							msg.obj = "密码错误！";
							mHandler.sendMessage(msg);

						} else {
							JSONObject obj = new JSONObject(result);

							int uid = obj.getInt("uid");
							String username = obj.getString("username");

							SPUtils.putInt(getApplicationContext(),
									ConstantValue.UID, uid);
							SPUtils.putString(getApplicationContext(),
									ConstantValue.USERNAME, username);
							SPUtils.putBoolean(getApplicationContext(),
									ConstantValue.HAS_LOGGED_IN, true);

							setResult(10,
									new Intent().putExtra("username", username));

							finish();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
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
		mUsername = et_username.getText().toString().trim();
		mPassword = et_password.getText().toString().trim();

		if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)) {
			ToastUtils.show(getApplicationContext(), "用户名或密码不能为空！");
			return false;
		}

		return true;
	}

}