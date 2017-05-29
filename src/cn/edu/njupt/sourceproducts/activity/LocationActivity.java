package cn.edu.njupt.sourceproducts.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.njupt.sourceproducts.R;
import cn.edu.njupt.sourceproducts.dao.LocationDao;
import cn.edu.njupt.sourceproducts.domain.Location;

/**
 * 显示产品溯源信息界面的Activity
 * 
 * @author hhw
 * 
 */
public class LocationActivity extends Activity {

	private ListView lv_location;

	private LocationDao mDao;
	private List<Location> mLocationList;
	private int mPid;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			lv_location.setAdapter(new MyAdapter());
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);

		initUI();
		initData();
	}

	/**
	 * 获取黑名单数据库数据
	 */
	private void initData() {
		Intent intent = getIntent();
		mPid = intent.getIntExtra("pid", 1);

		new Thread() {

			public void run() {
				mDao = LocationDao.getInstance();
				mLocationList = mDao.getLocationList(mPid);
				mHandler.sendEmptyMessage(0);
			};
		}.start();
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		lv_location = (ListView) findViewById(R.id.lv_location);
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mLocationList.size();
		}

		@Override
		public Object getItem(int position) {
			return mLocationList.get(position);
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
						R.layout.listview_location_item, null);

				holder = new ViewHolder();
				holder.tv_ltime = (TextView) convertView
						.findViewById(R.id.tv_ltime);
				holder.tv_location = (TextView) convertView
						.findViewById(R.id.tv_location);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Location location = mLocationList.get(position);

			holder.tv_ltime.setText(location.getLtime());
			holder.tv_location.setText(location.getLocation());

			return convertView;
		}

	}

	private static class ViewHolder {
		public TextView tv_ltime;
		public TextView tv_location;
	}

}
