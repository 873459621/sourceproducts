package cn.edu.njupt.sourceproducts.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.njupt.sourceproducts.R;

/**
 * 显示分类页面的Fragment
 * 
 * @author hhw
 */
public class CategoryFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_category, container,
				false);
		return view;
	}
}
