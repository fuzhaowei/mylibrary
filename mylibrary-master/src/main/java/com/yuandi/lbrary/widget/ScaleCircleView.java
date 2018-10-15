package com.yuandi.lbrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yuandi.lbrary.util.Conversio;

import java.util.List;

/**
 * Created by EdgeDi
 * 2018/2/11 15:15
 */

public class ScaleCircleView extends View {

    private Paint paint;
    private int m_width, m_height;
    private List<ScaleBean> list;
    private final int max_degree = 360;
    private double[] value_degree;
    private boolean isDraw = false;

    public ScaleCircleView(Context context) {
        super(context);
        initPaint();
    }

    public ScaleCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public ScaleCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    public List<ScaleBean> getList() {
        return list;
    }

    private RectF rectf;

    public void setList(List<ScaleBean> list, double max_value) {
        this.list = list;
        if (this.list != null && this.list.size() > 0) {
            isDraw = true;
            value_degree = new double[list.size()];
            for (int i = 0; i < list.size(); i++) {
                value_degree[i] = (list.get(i).getValue() / max_value) * max_degree;
            }
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        m_width = w;
        m_height = h;
        rectf = new RectF(dp(20f), dp(15f), m_width - dp(20f), m_height - dp(15f));
        invalidate();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1f);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isDraw && rectf != null) {
            int j = -90;
            for (int i = 0; i < list.size(); i++) {
                paint.setColor(list.get(i).getColor());
                canvas.drawArc(rectf, j, (float) value_degree[i], true, paint);
                j += value_degree[i];
            }
        }
    }

    private int dp(float dp) {
        return Conversio.dip2px(getContext(), dp);
    }
}