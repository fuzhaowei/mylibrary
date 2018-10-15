package com.yuandi.lbrary.window;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.yuandi.lbrary.R;


/**
 * Created by EdgeDi
 * 2017/9/18 14:27
 */

public abstract class BaseWindow {

    private PopupWindow window;
    private Activity context;
    private View view;
    private int flag = 0;

    private OnWindowListener listener;

    public BaseWindow(Activity context) {
        this.context = context;
        views = new SparseArray<>();
    }

    public BaseWindow(Activity context, int flag) {
        this.context = context;
        this.flag = flag;
        views = new SparseArray<>();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    public OnWindowListener getListener() {
        return listener;
    }

    public void setListener(OnWindowListener listener) {
        this.listener = listener;
    }

    protected void onCreate() {
        if (window == null) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(getLayout(), null);
                initUI();
                setListener();
            }
            if (flag == 0) {
                window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable());
                window.setOutsideTouchable(true);
            } else {
                window = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setOutsideTouchable(true);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            window.setAnimationStyle(R.style.popup_window_anim);
            window.setTouchable(true);
            window.setFocusable(true);
            window.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (flag == 0) {
                        setLuminance(1.0f);
                    }
                    if (getListener() != null) getListener().dismiss();
                }
            });
        }
    }

    public void Show(View view) {
        onCreate();
        if (flag == 0) {
            setLuminance(0.5f);
        }
        window.showAsDropDown(view);
    }

    @SuppressLint("RtlHardcoded")
    public void Show(View view, int x, int y) {
        onCreate();
        if (flag == 0) {
            setLuminance(0.5f);
        }
        window.showAsDropDown(view, x, y);
    }

    public void ShowBottom(View view) {
        onCreate();
        if (flag == 0) {
            setLuminance(0.5f);
        }
        window.showAsDropDown(view);
    }

    public void Hide() {
        if (window.isShowing()) {
            window.dismiss();
        }
    }

    private void setLuminance(float level) {
        WindowManager.LayoutParams lp = context.getWindow()
                .getAttributes();
        lp.alpha = level;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    private SparseArray<View> views;

    protected <T extends View> T bind(@IdRes int rid) {
        if (views.get(rid) == null) {
            views.append(rid, view.findViewById(rid));
        }
        return (T) views.get(rid);
    }

    @LayoutRes
    protected abstract int getLayout();

    protected abstract void initUI();

    protected abstract void setListener();

    public interface OnWindowListener {
        void dismiss();
    }

}