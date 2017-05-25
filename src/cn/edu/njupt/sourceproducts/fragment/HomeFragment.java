package cn.edu.njupt.sourceproducts.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.njupt.sourceproducts.R;
import cn.edu.njupt.sourceproducts.dao.ProductDao;
import cn.edu.njupt.sourceproducts.domain.Product;

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
	private MyAdapter mAdapter;
	private List<Product> mProductList;

	private int mTotal;
	private boolean isLoading;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			if (mAdapter == null) {
				mAdapter = new MyAdapter();
				lv_products.setAdapter(mAdapter);
			} else {
				mAdapter.notifyDataSetChanged();
			}
		};
	};

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
		loadData();
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
				// TODO Auto-generated method stub

			}
		});

		lv_products.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (mTotal > mProductList.size()
						&& scrollState == OnScrollListener.SCROLL_STATE_IDLE
						&& lv_products.getLastVisiblePosition() >= mProductList
								.size() - 1 && !isLoading) {
					loadData();
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		isLoading = true;
		new Thread() {

			public void run() {
				mDao = ProductDao.getInstance();

				if (mProductList == null) {
					mProductList = mDao.getProductList(0);
					mTotal = mDao.getTotal();
				} else {
					List<Product> list = mDao.getProductList(mProductList
							.size());
					mProductList.addAll(list);
				}

				isLoading = false;

				mHandler.sendEmptyMessage(0);
			};
		}.start();
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mProductList.size();
		}

		@Override
		public Object getItem(int position) {
			return mProductList.get(position);
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
						R.layout.listview_product_item, null);

				holder = new ViewHolder();

				holder.tv_pname = (TextView) convertView
						.findViewById(R.id.tv_pname);
				holder.tv_price = (TextView) convertView
						.findViewById(R.id.tv_price);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Product product = mProductList.get(position);

			holder.tv_pname.setText(product.getPname());
			holder.tv_price.setText("¥ " + product.getPrice());

			holder.tv_pname.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				}
			});

			return convertView;
		}

	}

	private static class ViewHolder {
		public TextView tv_pname;
		public TextView tv_price;
	}

}
