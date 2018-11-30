package com.zc.democoolwidget.recycleview.excelcalendar;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zc.democoolwidget.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.VerticalItemHolder> {

    private List<Map<String,Object>> priceList = new ArrayList<>();
    /**是否加载了网络数据*/
    private boolean isDateAccept = true;
    private OnItemClickListener mOnItemClickListener;

    public SimpleAdapter(List<Map<String,Object>> dateList) {
        this.priceList = dateList;
        notifyDataSetChanged();
    }

    public void reflushView(List<Map<String,Object>> dateList) {
        this.priceList = dateList;
        isDateAccept = true;
        notifyDataSetChanged();
    }

    @Override
    public VerticalItemHolder onCreateViewHolder(ViewGroup container, int viewType) {
        View root = LayoutInflater.from(container.getContext()).inflate(R.layout.item_back_calendar_content, container, false);
        return new VerticalItemHolder(root);
    }

    @Override
    public void onBindViewHolder(final VerticalItemHolder itemHolder, int position) {
        Map<String, Object> mapInfo = priceList.get(position);
        String rowColSelect = MapUtils.getObject(mapInfo.get("rowColSelect"));
        String price = MapUtils.getObject(mapInfo.get("price"));
        if ("--".equals(price)) {
            itemHolder.text.setText("--");
        } else {
            itemHolder.text.setText("¥"+price);
        }
        if ("1".equals(rowColSelect)) {//行列浅红色
            itemHolder.text.setBackgroundResource(R.color.color_red_fff2f3);
            itemHolder.text.setTextColor(Color.parseColor("#696969"));
        } else if ("2".equals(rowColSelect)) {//选中红色
            itemHolder.text.setBackgroundResource(R.color.red_B100E);
            itemHolder.text.setTextColor(Color.WHITE);
        } else {
            itemHolder.text.setTextColor(Color.parseColor("#696969"));
            itemHolder.text.setBackgroundResource(R.color.white);
        }
        itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemHolderClick(itemHolder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return priceList.size();
    }

    public interface  OnItemClickListener {
        /**@param  rowPosition 行位置
         * @param colPosition 列位置*/
        void onItemClick(int adapterPosition, int rowPosition, int colPosition);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private void onItemHolderClick(VerticalItemHolder itemHolder) {
        if (mOnItemClickListener != null) {
            if (!isDateAccept) {//没有加载完数据
                return;
            }
            int adapterPosition = itemHolder.getAdapterPosition();
            Map<String, Object> selectMapInfo = priceList.get(adapterPosition);
            int rowPosition = adapterPosition / 31;//行
            int colPosition = adapterPosition % 31;//列

            for (int index=0; index < priceList.size(); index++) {
                Map<String, Object> mapInfo = priceList.get(index);
                String rowColSelectString = MapUtils.getObject(mapInfo.get("rowColSelect"));
                if (!"0".equals(rowColSelectString)) {//局部更新 先将展示的所有的选中数据（红色粉红色）全部清除
                    mapInfo.put("rowColSelect","0");
                    notifyItemChanged(index);
                }
                if (rowPosition == (index / 31 ) || colPosition == (index % 31)) {
                    mapInfo.put("rowColSelect","1");
                    notifyItemChanged(index);
                } else {
                    mapInfo.put("rowColSelect","0");
                }
            }
            selectMapInfo.put("rowColSelect","2");
            notifyItemChanged(adapterPosition);
            mOnItemClickListener.onItemClick(adapterPosition, rowPosition,colPosition);
        }
    }

    public static class VerticalItemHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public VerticalItemHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.tv_price);
        }
    }


}
