package cn.edu.njupt.sourceproducts.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.edu.njupt.sourceproducts.R;
import cn.edu.njupt.sourceproducts.activity.LoginActivity;
import cn.edu.njupt.sourceproducts.engine.ConstantValue;
import cn.edu.njupt.sourceproducts.utils.SPUtils;

/**
 * 显示我的页面的Fragment
 * 
 * @author hhw
 */
public class MeFragment extends Fragment {

	private View mView;
	private TextView tv_personal;
	private TextView tv_all_orders;
	private TextView tv_non_payment;
	private TextView tv_unfilled;
	private TextView tv_unrecrived;
	private TextView tv_unvalued;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_me, container, false);

		initUI();

		return mView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (data != null) {
			String username = data.getStringExtra("username");
			tv_personal.setText("您好：" + username);
			tv_personal.setClickable(false);
		}
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		tv_personal = (TextView) mView.findViewById(R.id.tv_personal);
		tv_all_orders = (TextView) mView.findViewById(R.id.tv_all_orders);
		tv_non_payment = (TextView) mView.findViewById(R.id.tv_non_payment);
		tv_unfilled = (TextView) mView.findViewById(R.id.tv_unfilled);
		tv_unrecrived = (TextView) mView.findViewById(R.id.tv_unrecrived);
		tv_unvalued = (TextView) mView.findViewById(R.id.tv_unvalued);

		boolean has_logged_in = SPUtils.getBoolean(getActivity(),
				ConstantValue.HAS_LOGGED_IN, false);

		if (has_logged_in) {
			String username = SPUtils.getString(getActivity(),
					ConstantValue.USERNAME, null);
			tv_personal.setText("您好：" + username);

		} else {
			tv_personal.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							LoginActivity.class);
					startActivityForResult(intent, 10);
				}
			});
		}

		tv_all_orders.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				reset();
			}
		});

		tv_non_payment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				reset();
				tv_non_payment.setSelected(true);
			}
		});

		tv_unfilled.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				reset();
				tv_unfilled.setSelected(true);
			}
		});

		tv_unrecrived.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				reset();
				tv_unrecrived.setSelected(true);
			}
		});

		tv_unvalued.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				reset();
				tv_unvalued.setSelected(true);
			}
		});
	}

	/**
	 * 重置选中状态
	 */
	private void reset() {
		tv_non_payment.setSelected(false);
		tv_unfilled.setSelected(false);
		tv_unrecrived.setSelected(false);
		tv_unvalued.setSelected(false);
	}

}
