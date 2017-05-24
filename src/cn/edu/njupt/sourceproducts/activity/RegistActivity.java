package cn.edu.njupt.sourceproducts.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.edu.njupt.sourceproducts.R;
import cn.edu.njupt.sourceproducts.engine.ConstantValue;
import cn.edu.njupt.sourceproducts.utils.HttpUtils;
import cn.edu.njupt.sourceproducts.utils.ToastUtils;

/**
 * 显示用户注册界面的Activity
 * 
 * @author hhw
 */
public class RegistActivity extends Activity {

	private EditText et_username;
	private EditText et_password;
	private EditText et_confirm_password;
	private EditText et_email;
	private EditText et_phone;
	private Button btn_regist;

	private String mUsername;
	private String mPassword;
	private String mConfirmPassword;
	private String mEmail;
	private String mPhone;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			ToastUtils.show(getApplicationContext(), (String) msg.obj);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);

		initUI();
	}

	/**
	 * 初始化UI控件
	 */
	private void initUI() {
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
		et_email = (EditText) findViewById(R.id.et_email);
		et_phone = (EditText) findViewById(R.id.et_phone);
		btn_regist = (Button) findViewById(R.id.btn_regist);

		btn_regist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkET()) {
					regist();
				}
			}
		});
	}

	/**
	 * 用户注册
	 */
	private void regist() {
		new Thread() {

			@Override
			public void run() {
				String path = ConstantValue.IP_ADDRESS + "/RegistServlet";
				String data = "username=" + mUsername + "&password="
						+ mPassword + "&email=" + mEmail + "&phone=" + mPhone;
				
				String result = HttpUtils.getStringByPost(path, data);
				
				if (result.startsWith("user")) {
					Message msg = Message.obtain();
					msg.obj = "用户已经存在！";
					mHandler.sendMessage(msg);

				} else if (result.startsWith("success")) {
					Message msg = Message.obtain();
					msg.obj = "注册成功！请登录";
					mHandler.sendMessage(msg);

					finish();
				}
			}
		}.start();
	}

	/**
	 * 检查EditText中信息输入是否有误
	 * 
	 * @return 返回true代表没有问题，返回false代表有问题
	 */
	private boolean checkET() {
		mUsername = et_username.getText().toString().trim();
		mPassword = et_password.getText().toString().trim();
		mConfirmPassword = et_confirm_password.getText().toString().trim();
		mEmail = et_email.getText().toString().trim();
		mPhone = et_phone.getText().toString().trim();

		if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)
				|| TextUtils.isEmpty(mConfirmPassword)
				|| TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mPhone)) {
			ToastUtils.show(getApplicationContext(), "信息不能为空！");
			return false;

		} else if (!mPassword.equals(mConfirmPassword)) {
			ToastUtils.show(getApplicationContext(), "密码不一致！");
			return false;
		}

		return true;
	}

}
