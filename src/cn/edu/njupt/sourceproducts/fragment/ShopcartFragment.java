package cn.edu.njupt.sourceproducts.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.njupt.sourceproducts.R;

/**
 * 显示购物车页面的Fragment
 * 
 * @author hhw
 */
public class ShopcartFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_shopcart, container,
				false);
		return view;
	}

}
