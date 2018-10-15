package com.yuandi.lbrary.holder;

import android.support.annotation.IdRes;
import android.view.View;

/**
 * Created by EdgeDi
 * 2018/2/7 15:42
 */

public interface Holder {

    <T extends View> T bind(int id);

    Holder setText(@IdRes int id, CharSequence content);

    Holder setImage(@IdRes int id, int rid);

    Holder setBackgroundColor(@IdRes int id, int color);

    Holder setStatue(@IdRes int id, boolean status);
}
