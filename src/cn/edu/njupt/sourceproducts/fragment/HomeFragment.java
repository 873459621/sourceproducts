package cn.edu.njupt.sourceproducts.fragment;

import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.njupt.sourceproducts.R;
import cn.edu.njupt.sourceproducts.activity.SearchActivity;
import cn.edu.njupt.sourceproducts.dao.ProductDao;
import cn.edu.njupt.sourceproducts.domain.Product;
import cn.edu.njupt.sourceproducts.engine.ProductListView;
import cn.edu.njupt.sourceproducts.engine.ProductListView.ProductData;

/**
 * 显示首页的Fragment
 * 
 * @author hhw
 */
public class HomeFragment extends Fragment {

	private View mView;
	private TextView tv_search;
	private ListView lv_products;

	private ProductDao mDao;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_home, container, false);
		mDao = ProductDao.getInstance();

		initUI();
		initData();

		return mView;
	}

	/**
	 * 初始化控件显示的数据
	 */
	private void initData() {
		final ProductListView productListView = new ProductListView(
				lv_products, getActivity());

		productListView.setData(new ProductData() {

			@Override
			public int getTotal() {
				return mDao.getTotal();
			}

			@Override
			public List<Product> getProductList(int index) {
				return mDao.getProductList(index);
			}
		});
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		tv_search = (TextView) mView.findViewById(R.id.tv_search);
		lv_products = (ListView) mView.findViewById(R.id.lv_products);

		tv_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), SearchActivity.class));
			}
		});
	}

}
