package cn.edu.njupt.sourceproducts.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import cn.edu.njupt.sourceproducts.R;

/**
 * 显示载入界面的Activity
 * 
 * @author hhw
 */
public class SplashActivity extends Activity {

	private RelativeLayout rl_root;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			Intent intent = new Intent(getApplicationContext(),
					HomeActivity.class);
			startActivity(intent);

			finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		initUI();
		initAnimation();
		mHandler.sendEmptyMessageDelayed(0, 1500);
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		rl_root = (RelativeLayout) findViewById(R.id.rl_root);
	}

	/**
	 * 初始化进入动画
	 */
	private void initAnimation() {
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
		alphaAnimation.setDuration(1000);
		rl_root.startAnimation(alphaAnimation);
	}
}
