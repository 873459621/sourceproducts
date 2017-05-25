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
import cn.edu.njupt.sourceproducts.R;
import cn.edu.njupt.sourceproducts.engine.ConstantValue;
import cn.edu.njupt.sourceproducts.utils.HttpUtils;
import cn.edu.njupt.sourceproducts.utils.ToastUtils;
import cn.edu.njupt.sourceproducts.view.InputItemView;

/**
 * 显示用户注册界面的Activity
 * 
 * @author hhw
 */
public class RegistActivity extends Activity {

	private InputItemView iiv_username;
	private InputItemView iiv_password;
	private InputItemView iiv_confirm_password;
	private InputItemView iiv_email;
	private InputItemView iiv_phone;
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
		iiv_username = (InputItemView) findViewById(R.id.iiv_username);
		iiv_password = (InputItemView) findViewById(R.id.iiv_password);
		iiv_confirm_password = (InputItemView) findViewById(R.id.iiv_confirm_password);
		iiv_email = (InputItemView) findViewById(R.id.iiv_email);
		iiv_phone = (InputItemView) findViewById(R.id.iiv_phone);
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
		mUsername = iiv_username.getText();
		mPassword = iiv_password.getText();
		mConfirmPassword = iiv_confirm_password.getText();
		mEmail = iiv_email.getText();
		mPhone = iiv_phone.getText();

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
