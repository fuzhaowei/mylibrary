package com.yuandi.lbrary.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuandi.lbrary.holder.ViewHolder;

import java.util.List;

/**
 * Created by EdgeDi
 * 2018/2/8 9:13
 */

public abstract class BaseREAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    private int HEAD_KEY = 1100000;
    private int FOOT_KEY = 1200000;
    private List<T> list;
    private Context context;
    private int layout;
    private LayoutInflater inflater;
    private View head_view;
    private View foot_view;

    public BaseREAdapter(List<T> list, Context context, int layout) {
        this.list = list;
        this.context = context;
        this.layout = layout;
        inflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setHead_view(View head_view) {
        this.head_view = head_view;
    }

    public void setFoot_view(View foot_view) {
        this.foot_view = foot_view;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD_KEY) {
            return new ViewHolder(head_view);
        } else if (viewType == FOOT_KEY) {
            return new ViewHolder(foot_view);
        } else {
            return new ViewHolder(inflater.inflate(layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        T item = getItem(position);
        if (item == null) {
            return;
        }
        initialise(holder, item, position);
    }

    public abstract void initialise(ViewHolder holder, T item, int position);

    @Override
    public int getItemCount() {
        return list.size() + getViewSize(head_view) + getViewSize(foot_view);
    }

    private int getViewSize(View view) {
        return view == null ? 0 : 1;
    }

    private T getItem(int position) {
        boolean isHead = head_view != null;
        boolean isFoot = foot_view != null;
        if (isHead) {
            if (position == 0) {
                return null;
            } else if (position > list.size() && isFoot) {
                return null;
            } else {
                return list.get(position - 1);
            }
        } else {
            if (position == list.size() && isFoot) {
                return null;
            } else {
                return list.get(position);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (head_view != null) {
            if (position == 0) {
                return HEAD_KEY;
            } else {
                return position - 1;
            }
        } else if (foot_view != null && list.size() == position) {
            return FOOT_KEY;
        } else {
            return position;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager manager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = manager.getSpanSizeLookup();
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isHead = head_view != null;
                    boolean isFoot = foot_view != null;
                    if (position == 0 && isHead) {
                        return manager.getSpanCount();
                    } else if (position == list.size() && isFoot) {
                        return manager.getSpanCount();
                    }
                    if (spanSizeLookup != null) {
                        return spanSizeLookup.getSpanSize(position);
                    }
                    return 1;
                }
            });
            manager.setSpanCount(manager.getSpanCount());
        }
    }

}