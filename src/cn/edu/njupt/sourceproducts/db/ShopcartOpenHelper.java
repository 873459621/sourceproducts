package cn.edu.njupt.sourceproducts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 购物车的数据库
 * 
 * @author hhw
 */
public class ShopcartOpenHelper extends SQLiteOpenHelper {

	public ShopcartOpenHelper(Context context) {
		super(context, "shopcart.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE SHOPCART (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "PID INTEGER, PNAME VARCHAR(100), PRICE DOUBLE, IMAGE VARCHAR(200), "
				+ "DES VARCHAR(400), NUMBER INTEGER, SUBTOTAL DOUBLE)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
