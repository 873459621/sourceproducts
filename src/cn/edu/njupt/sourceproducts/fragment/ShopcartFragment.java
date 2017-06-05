package cn.edu.njupt.sourceproducts.fragment;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.njupt.sourceproducts.R;
import cn.edu.njupt.sourceproducts.activity.LoginActivity;
import cn.edu.njupt.sourceproducts.activity.ProductActivity;
import cn.edu.njupt.sourceproducts.dao.OrderDao;
import cn.edu.njupt.sourceproducts.db.dao.ItemDao;
import cn.edu.njupt.sourceproducts.db.domain.Item;
import cn.edu.njupt.sourceproducts.engine.ConstantValue;
import cn.edu.njupt.sourceproducts.utils.MD5Utils;
import cn.edu.njupt.sourceproducts.utils.SPUtils;
import cn.edu.njupt.sourceproducts.utils.ToastUtils;

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
			mTotal = 0.0;
			for (Item i : mItemList) {
				mTotal += i.getSubtotal();
			}
			tv_total.setText("¥ " + mTotal);

			if (msg.what == 1) {
				ToastUtils.show(getActivity(), "下单成功！");
			}
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
				if (mItemList.size() > 0) {
					boolean has_logged_in = SPUtils.getBoolean(getActivity(),
							ConstantValue.HAS_LOGGED_IN, false);
					if (has_logged_in) {
						showDialog();
					} else {
						startActivity(new Intent(getActivity(),
								LoginActivity.class));
						ToastUtils.show(getActivity(), "请先登录！");
					}
				} else {
					ToastUtils.show(getActivity(), "购物车为空！");
				}
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
	 * 显示确认密码的对话框
	 */
	private void showDialog() {
		Builder builder = new AlertDialog.Builder(getActivity());
		final AlertDialog dialog = builder.create();
		final View view = View.inflate(getActivity(),
				R.layout.dialog_confirm_pwd, null);
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();

		TextView tv_total = (TextView) view.findViewById(R.id.tv_total);
		Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

		tv_total.setText("¥ " + mTotal);

		btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText et_confirm_pwd = (EditText) view
						.findViewById(R.id.et_confirm_pwd);
				String confirmPwd = et_confirm_pwd.getText().toString().trim();

				if (!TextUtils.isEmpty(confirmPwd)) {
					confirmPwd = MD5Utils.encode(confirmPwd);
					String pwd = SPUtils.getString(getActivity(),
							ConstantValue.PASSWORD, null);

					if (confirmPwd.equals(pwd)) {
						summitOrder();
						dialog.dismiss();
					} else {
						ToastUtils.show(getActivity(), "密码不一致！");
					}
				} else {
					ToastUtils.show(getActivity(), "请输入密码！");
				}
			}
		});

		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	/**
	 * 提交订单到服务端
	 */
	private void summitOrder() {
		new Thread() {

			public void run() {
				int uid = SPUtils.getInt(getActivity(), ConstantValue.UID, 0);

				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put("uid", uid);
					jsonObject.put("total", mTotal);
					JSONArray jsonArray = new JSONArray();
					for (Item item : mItemList) {
						JSONObject obj = new JSONObject();
						obj.put("pid", item.getPid());
						obj.put("number", item.getNumber());
						obj.put("subtotal", item.getSubtotal());
						jsonArray.put(obj);
					}
					jsonObject.put("itemList", jsonArray);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				String data = "json=" + jsonObject.toString();

				OrderDao orderDao = OrderDao.getInstance();
				String result = orderDao.summitOrder(data);
				if (!TextUtils.isEmpty(result)) {
					mDao.clear();
					mItemList.clear();
					mHandler.sendEmptyMessage(1);
				}
			};
		}.start();
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

			final Item item = mItemList.get(position);

			holder.iv_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mDao.delete(item.getPid());
					mItemList.remove(position);
					mHandler.sendEmptyMessage(0);
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
