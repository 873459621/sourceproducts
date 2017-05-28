package cn.edu.njupt.sourceproducts.activity;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.edu.njupt.sourceproducts.R;
import cn.edu.njupt.sourceproducts.dao.ProductDao;
import cn.edu.njupt.sourceproducts.domain.Product;

/**
 * 显示搜索界面的Activity
 * 
 * @author hhw
 */
public class SearchActivity extends ProductListActivity {

	private EditText et_text;
	private Button btn_search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		initUI();
	}

	/**
	 * 初始化UI控件
	 */
	private void initUI() {
		et_text = (EditText) findViewById(R.id.et_text);
		btn_search = (Button) findViewById(R.id.btn_search);

		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String text = et_text.getText().toString().trim();
				loadData();
			}
		});
	}

}
