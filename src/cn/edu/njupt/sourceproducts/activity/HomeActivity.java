package cn.edu.njupt.sourceproducts.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import cn.edu.njupt.sourceproducts.R;
import cn.edu.njupt.sourceproducts.fragment.CategoryFragment;
import cn.edu.njupt.sourceproducts.fragment.HomeFragment;
import cn.edu.njupt.sourceproducts.fragment.MeFragment;
import cn.edu.njupt.sourceproducts.fragment.ShopcartFragment;

/**
 * 显示主界面的Activity
 * 
 * @author hhw
 */
public class HomeActivity extends Activity implements OnClickListener {

	private TextView tv_home;
	private TextView tv_category;
	private TextView tv_shopcart;
	private TextView tv_me;

	private HomeFragment fragment_home;
	private CategoryFragment fragment_category;
	private ShopcartFragment fragment_shopcart;
	private MeFragment fragment_me;

	private FragmentManager mFragmentManager;
	private FragmentTransaction mTransaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		initUI();
		initHome();
	}

	/**
	 * 加载主界面的Fragment
	 */
	private void initHome() {
		mFragmentManager = getFragmentManager();
		mTransaction = mFragmentManager.beginTransaction();

		resetSelectedState();
		tv_home.setSelected(true);

		fragment_home = new HomeFragment();
		mTransaction.add(R.id.fl_container, fragment_home);

		mTransaction.commit();
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		tv_home = (TextView) findViewById(R.id.tv_home);
		tv_category = (TextView) findViewById(R.id.tv_category);
		tv_shopcart = (TextView) findViewById(R.id.tv_shopcart);
		tv_me = (TextView) findViewById(R.id.tv_me);

		tv_home.setOnClickListener(this);
		tv_category.setOnClickListener(this);
		tv_shopcart.setOnClickListener(this);
		tv_me.setOnClickListener(this);
	}

	/**
	 * 隐藏所有的Fragment
	 * 
	 * @param transaction
	 *            Fragment事务
	 */
	public void hideAllFragments(FragmentTransaction transaction) {
		if (fragment_home != null) {
			transaction.hide(fragment_home);
		}
		if (fragment_category != null) {
			transaction.hide(fragment_category);
		}
		if (fragment_shopcart != null) {
			transaction.hide(fragment_shopcart);
		}
		if (fragment_me != null) {
			transaction.hide(fragment_me);
		}
	}

	/**
	 * 重置所有TextView的选中状态
	 */
	public void resetSelectedState() {
		tv_home.setSelected(false);
		tv_category.setSelected(false);
		tv_shopcart.setSelected(false);
		tv_me.setSelected(false);
	}

	@Override
	public void onClick(View v) {
		mTransaction = mFragmentManager.beginTransaction();

		hideAllFragments(mTransaction);

		switch (v.getId()) {

		case R.id.tv_home:
			resetSelectedState();
			tv_home.setSelected(true);

			if (fragment_home == null) {
				fragment_home = new HomeFragment();
				mTransaction.add(R.id.fl_container, fragment_home);
			} else {
				mTransaction.show(fragment_home);
			}
			break;

		case R.id.tv_category:
			resetSelectedState();
			tv_category.setSelected(true);

			if (fragment_category == null) {
				fragment_category = new CategoryFragment();
				mTransaction.add(R.id.fl_container, fragment_category);
			} else {
				mTransaction.show(fragment_category);
			}
			break;

		case R.id.tv_shopcart:
			resetSelectedState();
			tv_shopcart.setSelected(true);

			fragment_shopcart = new ShopcartFragment();
			mTransaction.add(R.id.fl_container, fragment_shopcart);
			break;

		case R.id.tv_me:
			resetSelectedState();
			tv_me.setSelected(true);

			if (fragment_me == null) {
				fragment_me = new MeFragment();
				mTransaction.add(R.id.fl_container, fragment_me);
			} else {
				mTransaction.show(fragment_me);
			}
			break;
		}

		mTransaction.commit();
	}

}
