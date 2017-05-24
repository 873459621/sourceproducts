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
	}

}
