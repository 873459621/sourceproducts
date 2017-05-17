package com.nupt.sourceproducts.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.nupt.sourceproducts.R;
import com.nupt.sourceproducts.fragment.CategoryFragment;
import com.nupt.sourceproducts.fragment.HomeFragment;
import com.nupt.sourceproducts.fragment.MeFragment;
import com.nupt.sourceproducts.fragment.ShopcartFragment;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		initUI();
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
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		hideAllFragments(transaction);

		switch (v.getId()) {

		case R.id.tv_home:
			resetSelectedState();
			tv_home.setSelected(true);

			if (fragment_home == null) {
				fragment_home = new HomeFragment();
				transaction.add(R.id.fl_container, fragment_home);
			} else {
				transaction.show(fragment_home);
			}
			break;

		case R.id.tv_category:
			resetSelectedState();
			tv_category.setSelected(true);

			if (fragment_category == null) {
				fragment_category = new CategoryFragment();
				transaction.add(R.id.fl_container, fragment_category);
			} else {
				transaction.show(fragment_category);
			}
			break;

		case R.id.tv_shopcart:
			resetSelectedState();
			tv_shopcart.setSelected(true);

			if (fragment_shopcart == null) {
				fragment_shopcart = new ShopcartFragment();
				transaction.add(R.id.fl_container, fragment_shopcart);
			} else {
				transaction.show(fragment_shopcart);
			}
			break;

		case R.id.tv_me:
			resetSelectedState();
			tv_me.setSelected(true);

			if (fragment_me == null) {
				fragment_me = new MeFragment();
				transaction.add(R.id.fl_container, fragment_me);
			} else {
				transaction.show(fragment_me);
			}
			break;
		}

		transaction.commit();
	}

}
