package cn.edu.njupt.sourceproducts.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import cn.edu.njupt.sourceproducts.R;
import cn.edu.njupt.sourceproducts.dao.ProductDao;
import cn.edu.njupt.sourceproducts.domain.Product;
import cn.edu.njupt.sourceproducts.engine.ProductListView;
import cn.edu.njupt.sourceproducts.engine.ProductListView.ProductData;

/**
 * 显示搜索界面的Activity
 * 
 * @author hhw
 */
public class SearchActivity extends Activity {

	private EditText et_text;
	private Button btn_search;

	private ProductDao mDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		mDao = ProductDao.getInstance();
		initUI();
	}

	/**
	 * 初始化UI控件
	 */
	private void initUI() {
		et_text = (EditText) findViewById(R.id.et_text);
		btn_search = (Button) findViewById(R.id.btn_search);
		ListView lv_products = (ListView) findViewById(R.id.lv_products);

		final ProductListView productListView = new ProductListView(
				lv_products, this);

		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String text = et_text.getText().toString().trim();
				productListView.setData(new ProductData() {

					@Override
					public int getTotal() {
						// TODO
						return mDao.getTotal();
					}

					@Override
					public List<Product> getProductList(int index) {
						// TODO
						return mDao.getProductList(index);
					}
				});
			}
		});
	}

}
