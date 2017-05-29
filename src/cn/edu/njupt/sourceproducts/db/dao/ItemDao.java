package cn.edu.njupt.sourceproducts.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.edu.njupt.sourceproducts.db.ShopcartOpenHelper;
import cn.edu.njupt.sourceproducts.db.domain.Item;

/**
 * 购物车数据库的dao，单例设计模式
 * 
 * @author hhw
 */
public class ItemDao {

	private static ItemDao mInstance;

	private ShopcartOpenHelper mHelper;
	private SQLiteDatabase mDB;
	private ContentValues mValues;
	private Cursor mCursor;

	private ItemDao(Context context) {
		mHelper = new ShopcartOpenHelper(context);
	}

	/**
	 * 获取ItemDao的实例
	 * 
	 * @param context
	 *            上下文
	 * @return ItemDao的实例
	 */
	public static ItemDao getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new ItemDao(context);
		}
		return mInstance;
	}

	/**
	 * 向购物车中插入一条条目
	 * 
	 * @param item
	 *            条目
	 */
	public void insert(Item item) {
		mDB = mHelper.getWritableDatabase();

		mValues = new ContentValues();
		mValues.put("PID", item.getPid());
		mValues.put("PNAME", item.getPname());
		mValues.put("PRICE", item.getPrice());
		mValues.put("IMAGE", item.getImage());
		mValues.put("DES", item.getDes());
		mValues.put("NUMBER", item.getNumber());
		mValues.put("SUBTOTAL", item.getSubtotal());

		mDB.insert("SHOPCART", null, mValues);

		mDB.close();
	}

	/**
	 * 从购物车中删除一个条目
	 * 
	 * @param pid
	 *            产品ID
	 */
	public void delete(int pid) {
		mDB = mHelper.getWritableDatabase();

		mDB.delete("SHOPCART", "PID=?", new String[] { pid + "" });

		mDB.close();
	}

	/**
	 * 更新购物车中的一个条目
	 * 
	 * @param pid
	 *            产品ID
	 * @param number
	 *            产品数目
	 * @param subtotal
	 *            小计
	 */
	public void update(int pid, int number, double subtotal) {
		mDB = mHelper.getWritableDatabase();

		mValues = new ContentValues();
		mValues.put("NUMBER", number);
		mValues.put("SUBTOTAL", subtotal);
		mDB.update("SHOPCART", mValues, "PID=?", new String[] { pid + "" });

		mDB.close();
	}

	/**
	 * 获取购物车中所有条目
	 * 
	 * @return 条目的List集合
	 */
	public List<Item> query() {
		List<Item> list = new ArrayList<Item>();
		mDB = mHelper.getWritableDatabase();

		mCursor = mDB.query("SHOPCART", null, null, null, null, null, null);

		if (mCursor != null && mCursor.getCount() > 0) {
			while (mCursor.moveToNext()) {
				list.add(new Item(mCursor.getInt(1), mCursor.getString(2),
						mCursor.getDouble(3), mCursor.getString(4), mCursor
								.getString(5), mCursor.getInt(6), mCursor
								.getDouble(7)));
			}
			mCursor.close();
		}

		mDB.close();

		return list;
	}

	/**
	 * 获取购物车中产品的数目
	 * 
	 * @param pid
	 *            产品id
	 * @return 产品的数目
	 */
	public int getNumber(int pid) {
		mDB = mHelper.getWritableDatabase();

		mCursor = mDB.query("SHOPCART", new String[] { "NUMBER" }, "PID=?",
				new String[] { pid + "" }, null, null, null);

		int mode = 0;
		if (mCursor != null && mCursor.moveToNext()) {
			mode = mCursor.getInt(0);
			mCursor.close();
		}

		mDB.close();
		return mode;
	}

}
