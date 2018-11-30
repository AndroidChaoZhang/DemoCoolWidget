package com.zc.democoolwidget.casetotal;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zc.democoolwidget.R;

import java.util.ArrayList;

public class PwActivity extends Activity{
	private EditText et_input;
	private ImageView down_arrow;
	private ListView listView;
	private ArrayList<String> arrayList;
	/**
	 * 使用PopupWindow可实现弹出窗口效果,，其实和AlertDialog一样，也是一种对话框
	 */
	private PopupWindow popupWindow;
	private MyAdapter myAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pw);
		myAdapter = new MyAdapter();
		et_input = (EditText) findViewById(R.id.et_input);
		down_arrow = (ImageView) findViewById(R.id.down_arrow);
		listView = new ListView(this);
		//设置背景，不然为黑色的难看
		listView.setBackgroundResource(R.drawable.listview_background);
		//禁用竖起方向的滑动条
		listView.setVerticalScrollBarEnabled(false);
		arrayList = new ArrayList<String>();
		for (int i = 0; i < 30; i++) {
			arrayList.add("zhangsan"+i);
		}
		/**
		 * 3.通过ListView的setAdapter()方法绑定
		 */
		listView.setAdapter(myAdapter);
		/**
		 * 1.点击事件  点击小图标  下拉
		 */
		down_arrow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (popupWindow  == null) {
					popupWindow = new PopupWindow();
					popupWindow.setWidth(et_input.getWidth());
					popupWindow.setHeight(200);
					//设置popWindow的内容
					popupWindow.setContentView(listView);
					//设置popWindow可获得焦点，否则listView的条目点击无作用  popWindow不获得焦点所以手动获得
					popupWindow.setFocusable(true);
//					//设 置 点击 popWindow以外的区域，让 popWindow自动隐藏
					popupWindow.setBackgroundDrawable(new BitmapDrawable());
//					popupWindow.setOutsideTouchable(true);
				}
				popupWindow.showAsDropDown(et_input,0,0);//默认状态 0,0是出现在底部居中
			}
		});
		/**
		 * 4.点击条目选中
		 */
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				//设置点击条目后文本框输入信息
				String text = arrayList.get(position);
				et_input.setText(text);
				//隐藏popWindow
				popupWindow.dismiss();
			}
		});
	}
	/**
	 * 2.MyAdapter
	 */
	public class MyAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			return arrayList.size();
		}
		@Override
		public Object getItem(int arg0) {
			return null;
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder vh;//此类是优化作用
			if (null == convertView) {
				vh = new ViewHolder();
				convertView = View.inflate(getApplicationContext(), R.layout.list_item, null);
				vh.delete = (ImageView) convertView.findViewById(R.id.iv_delete_item);
				vh.msg = (TextView) convertView.findViewById(R.id.tv_msg_item);
				//setTag把查找的view缓存起来方便多次重用
				convertView.setTag(vh);
			}else {
				//View中的setTag(Object)表示给View添加一个格外的数据，以后可以用getTag()将这个数据取出来
				vh = (ViewHolder) convertView.getTag();
			}
			vh.msg.setText(arrayList.get(position));
			/**
			 * 点击删除图表删除用户
			 */
			vh.delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//删除列表中的内容
					arrayList.remove(position);
					//刷新listView
					notifyDataSetChanged();
				}
			});
			return convertView;
		}
	}
	/**
	 * 优化   静态容器
	 * @author ZC
	 *
	 */
	static class ViewHolder{
		TextView msg;
		ImageView delete;
	}
}
