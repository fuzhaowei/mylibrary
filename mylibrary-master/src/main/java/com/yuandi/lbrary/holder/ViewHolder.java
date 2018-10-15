package com.yuandi.lbrary.holder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.yuandi.lbrary.util.JsonUtil;

import java.util.ArrayList;

/**
 * Created by EdgeDi
 * 2018/2/7 15:43
 */

public class ViewHolder extends RecyclerView.ViewHolder implements Holder {

    private SparseArray<View> VIEWS = new SparseArray<>();
    private View itemView;

    public ViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public View getItemView() {
        return itemView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }

    @Override
    public <T extends View> T bind(int id) {
        if (VIEWS.get(id) == null) {
            VIEWS.append(id, itemView.findViewById(id));
        }
        return (T) VIEWS.get(id);
    }

    @Override
    public Holder setText(int id, CharSequence content) {
        TextView view = bind(id);
        view.setText(content);
        return this;
    }

    @Override
    public Holder setImage(int id, int rid) {
        ImageView view = bind(id);
        view.setImageResource(rid);
        return this;
    }

    @Override
    public Holder setBackgroundColor(int id, int color) {
        View view = bind(id);
        view.setBackgroundColor(color);
        return this;
    }

    @Override
    public Holder setStatue(int id, boolean status) {
        View view = bind(id);
        view.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
        return this;
    }
}
