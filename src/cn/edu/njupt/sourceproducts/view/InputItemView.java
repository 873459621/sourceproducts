package cn.edu.njupt.sourceproducts.view;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.edu.njupt.sourceproducts.R;

/**
 * 自定输入框
 * 
 * @author hhw
 */
public class InputItemView extends RelativeLayout {

	private static final String NAMESPACE = "http://schemas.android.com/apk/res/cn.edu.njupt.sourceproducts";

	private TextView tv_name;
	private EditText et_input;

	private String mName;
	private String mHint;
	private String mInputType;

	public InputItemView(Context context) {
		this(context, null);
	}

	public InputItemView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public InputItemView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		View.inflate(context, R.layout.input_item_view, this);

		initAttrs(attrs);
		initUI();
	}

	/**
	 * 初始化属性集合中的自定义属性
	 * 
	 * @param attrs
	 *            属性集合
	 */
	private void initAttrs(AttributeSet attrs) {
		mName = attrs.getAttributeValue(NAMESPACE, "name");
		mHint = attrs.getAttributeValue(NAMESPACE, "hint");
		mInputType = attrs.getAttributeValue(NAMESPACE, "inputType");
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		tv_name = (TextView) findViewById(R.id.tv_name);
		et_input = (EditText) findViewById(R.id.et_input);

		tv_name.setText(mName);
		et_input.setHint(mHint);

		if ("textPassword".equals(mInputType)) {
			et_input.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}
	}

	/**
	 * 设置输入框中的内容
	 * 
	 * @param text
	 *            要设置的文本内容
	 */
	public void setText(String text) {
		et_input.setText(text);
	}

	/**
	 * 获取输入框中的文本内容
	 * 
	 * @return 输入框中的文本内容
	 */
	public String getText() {
		return et_input.getText().toString().trim();
	}

}
