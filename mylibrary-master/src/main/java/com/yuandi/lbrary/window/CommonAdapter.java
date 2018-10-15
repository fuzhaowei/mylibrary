package com.yuandi.lbrary.window;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yuandi.lbrary.R;
import com.yuandi.lbrary.base.BaseLGAdapter;
import com.yuandi.lbrary.holder.ViewHolder;

import java.util.List;

/**
 * Created by EdgeDi
 * 2017/9/18 15:14
 */

public class CommonAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int layout;
    private Context context;
    private List<CommonString> list;

    public CommonAdapter(int layout, Context context, List<CommonString> list) {
        this.layout = layout;
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<CommonString> getList() {
        return list;
    }

    public void setList(List<CommonString> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CommonString getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setText(R.id.common_text, list.get(position).toResult());
        return convertView;
    }
}
