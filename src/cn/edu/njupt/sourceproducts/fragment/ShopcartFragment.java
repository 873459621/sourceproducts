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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.njupt.sourceproducts.R;
import cn.edu.njupt.sourceproducts.activity.ProductActivity;
import cn.edu.njupt.sourceproducts.db.dao.ItemDao;
import cn.edu.njupt.sourceproducts.db.domain.Item;
import cn.edu.njupt.sourceproducts.engine.ConstantValue;

import com.loopj.android.image.SmartImageView;

/**
 * 显示购物车页面的Fragment
 * 
 * @author hhw
 */
public class ShopcartFragment extends Fragment {

	private View mView;
	private TextView tv_buy;
	private TextView tv_total;
	private ListView lv_shopcart;

	private MyAdapter mAdapter;
	private List<Item> mItemList;
	private ItemDao mDao;
	private double mTotal;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			if (mAdapter == null) {
				mAdapter = new MyAdapter();
				lv_shopcart.setAdapter(mAdapter);
			} else {
				mAdapter.notifyDataSetChanged();
			}
			tv_total.setText("¥ " + mTotal);
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_shopcart, container, false);

		initUI();
		initData();

		return mView;
	}

	/**
	 * 初始化控件的数据
	 */
	private void initData() {
		new Thread() {

			public void run() {
				mDao = ItemDao.getInstance(getActivity());
				mItemList = mDao.query();

				mTotal = 0.0;
				for (Item i : mItemList) {
					mTotal += i.getSubtotal();
				}

				mHandler.sendEmptyMessage(0);
			};
		}.start();
	}

	/**
	 * 初始化UI控件
	 */
	private void initUI() {
		tv_buy = (TextView) mView.findViewById(R.id.tv_buy);
		tv_total = (TextView) mView.findViewById(R.id.tv_total);
		lv_shopcart = (ListView) mView.findViewById(R.id.lv_shopcart);

		tv_buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		lv_shopcart.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				turnToProductActivity(position);
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
		Intent intent = new Intent(getActivity(), ProductActivity.class);

		Item item = mItemList.get(position);

		intent.putExtra("pid", item.getPid());
		intent.putExtra("pname", item.getPname());
		intent.putExtra("price", item.getPrice());
		intent.putExtra("des", item.getDes());
		intent.putExtra("image", item.getImage());

		startActivity(intent);
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mItemList.size();
		}

		@Override
		public Object getItem(int position) {
			return mItemList.get(position);
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
						R.layout.listview_shopcart_item, null);

				holder = new ViewHolder();

				holder.iv_delete = (ImageView) convertView
						.findViewById(R.id.iv_delete);
				holder.siv_image = (SmartImageView) convertView
						.findViewById(R.id.siv_image);
				holder.tv_pname = (TextView) convertView
						.findViewById(R.id.tv_pname);
				holder.tv_price = (TextView) convertView
						.findViewById(R.id.tv_price);
				holder.tv_number = (TextView) convertView
						.findViewById(R.id.tv_number);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Item item = mItemList.get(position);

			holder.iv_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

				}
			});

			holder.siv_image.setImageUrl(ConstantValue.IP_ADDRESS + "/"
					+ item.getImage());
			holder.tv_pname.setText(item.getPname());
			holder.tv_price.setText("¥ " + item.getPrice());
			holder.tv_number.setText("× " + item.getNumber());

			return convertView;
		}

	}

	private static class ViewHolder {
		public ImageView iv_delete;
		public SmartImageView siv_image;
		public TextView tv_pname;
		public TextView tv_price;
		public TextView tv_number;
	}

}
