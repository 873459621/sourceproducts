package cn.edu.njupt.sourceproducts.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.njupt.sourceproducts.R;
import cn.edu.njupt.sourceproducts.activity.SearchActivity;
import cn.edu.njupt.sourceproducts.dao.CategoryDao;
import cn.edu.njupt.sourceproducts.dao.ProductDao;
import cn.edu.njupt.sourceproducts.domain.Category;
import cn.edu.njupt.sourceproducts.domain.Product;
import cn.edu.njupt.sourceproducts.engine.ProductListView;
import cn.edu.njupt.sourceproducts.engine.ProductListView.ProductData;

/**
 * 显示分类页面的Fragment
 * 
 * @author hhw
 */
public class CategoryFragment extends Fragment {

	private View mView;
	private TextView tv_search;
	private ListView lv_category;
	private ListView lv_products;

	private CategoryDao mCDao;
	private ProductDao mPDao;
	private List<Category> mCategoryList;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			lv_category.setAdapter(new MyAdapter());
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_category, container, false);
		mPDao = ProductDao.getInstance();

		initUI();
		initData();

		return mView;
	}

	/**
	 * 初始化控件数据
	 */
	private void initData() {
		new Thread() {

			public void run() {
				mCDao = CategoryDao.getInstance();
				mCategoryList = mCDao.getCategoryList();
				mHandler.sendEmptyMessage(0);
			};
		}.start();
	}

	/**
	 * 初始化UI控件
	 */
	private void initUI() {
		tv_search = (TextView) mView.findViewById(R.id.tv_search);
		lv_category = (ListView) mView.findViewById(R.id.lv_category);
		lv_products = (ListView) mView.findViewById(R.id.lv_products);

		tv_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), SearchActivity.class));
			}
		});

		lv_category.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO
				ProductListView productListView = new ProductListView(
						lv_products, getActivity());

				productListView.setData(new ProductData() {

					@Override
					public int getTotal() {
						return mPDao.getTotal();
					}

					@Override
					public List<Product> getProductList(int index) {
						return mPDao.getProductList(index);
					}
				});
			}
		});
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mCategoryList.size();
		}

		@Override
		public Object getItem(int position) {
			return mCategoryList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(getActivity(),
						R.layout.listview_category_item, null);
				holder = new ViewHolder();
				holder.tv_category = (TextView) convertView
						.findViewById(R.id.tv_category);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Category category = mCategoryList.get(position);
			holder.tv_category.setText(category.getCname());
			return convertView;
		}

	}

	private static class ViewHolder {
		public TextView tv_category;
	}

}
