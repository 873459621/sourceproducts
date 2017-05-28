package cn.edu.njupt.sourceproducts.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.njupt.sourceproducts.R;
import cn.edu.njupt.sourceproducts.dao.ProductDao;
import cn.edu.njupt.sourceproducts.domain.Product;
import cn.edu.njupt.sourceproducts.engine.ConstantValue;

import com.loopj.android.image.SmartImageView;

/**
 * 具有显示产品列表功能的Activity
 * 
 * @author hhw
 */
public abstract class ProductListActivity extends Activity {

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
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		mDao = ProductDao.getInstance();
		initUI();
	};

	/**
	 * 初始化UI
	 */
	private void initUI() {
		lv_products = (ListView) findViewById(R.id.lv_products);

		lv_products.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				turnToProductActivity(position);
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
	 * 跳转到ProductActivity
	 * 
	 * @param position
	 *            点击的位置
	 */
	private void turnToProductActivity(int position) {
		Intent intent = new Intent(this, ProductActivity.class);

		Product product = mProductList.get(position);

		intent.putExtra("pid", product.getPid());
		intent.putExtra("pname", product.getPname());
		intent.putExtra("price", product.getPrice());
		intent.putExtra("des", product.getDes());
		intent.putExtra("image", product.getImage());

		startActivity(intent);
	}

	/**
	 * 加载数据
	 */
	protected void loadData() {
		isLoading = true;
		new Thread() {

			public void run() {

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
				convertView = View.inflate(getApplicationContext(),
						R.layout.listview_product_item, null);

				holder = new ViewHolder();

				holder.siv_image = (SmartImageView) convertView
						.findViewById(R.id.siv_image);
				holder.tv_pname = (TextView) convertView
						.findViewById(R.id.tv_pname);
				holder.tv_price = (TextView) convertView
						.findViewById(R.id.tv_price);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Product product = mProductList.get(position);

			holder.siv_image.setImageUrl(ConstantValue.IP_ADDRESS + "/"
					+ product.getImage());
			holder.tv_pname.setText(product.getPname());
			holder.tv_price.setText("¥ " + product.getPrice());

			return convertView;
		}

	}

	private static class ViewHolder {
		public SmartImageView siv_image;
		public TextView tv_pname;
		public TextView tv_price;
	}

}
