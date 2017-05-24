package cn.edu.njupt.sourceproducts.fragment;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.njupt.sourceproducts.R;
import cn.edu.njupt.sourceproducts.domain.Product;
import cn.edu.njupt.sourceproducts.engine.ConstantValue;
import cn.edu.njupt.sourceproducts.utils.HttpUtils;

/**
 * 显示首页的Fragment
 * 
 * @author hhw
 */
public class HomeFragment extends Fragment {

	private View mView;
	private TextView tv_search;
	private ListView lv_products;

	private List<Product> mPproductList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_home, container, false);

		initUI();
		initData();

		return mView;
	}

	/**
	 * 初始化首页的数据
	 */
	private void initData() {
		new Thread() {

			@Override
			public void run() {
				String path = ConstantValue.IP_ADDRESS
						+ "/ProductServlet?index=";

				String json = HttpUtils.getStringByGet(path);

				mPproductList = Product.toProductList(json);
			}
		}.start();
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		tv_search = (TextView) mView.findViewById(R.id.tv_search);
		lv_products = (ListView) mView.findViewById(R.id.lv_products);
	}

}
