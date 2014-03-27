package com.anning.chart.chart;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Anning Hu on 26/03/14.
 */
public class ChartView extends View {

    private final float INDICATOR_STROKE_WIDTH = 5f;
    private final float BAR_HIGHTLIGHT_WIDTH = 5f;
    private final float BAR_GRAPH_EMPTY_SPACE_RATIO = 0.4f;

    private float mMaxIndicator;
    private float mMinIndicator;
    private int mBarColor = 0xff4d4d4d;
    private int mBarHighlightColor = 0xfff9f9f9;
    private int mIndicatorColor = 0xffb66c17;
    private int mIndicatorRectColor = 0x22ffffff;
    private float[] mInput = new float[] {1.3f, 1.2f, 0.9f, 1.3f, 1.15f, 1.35f, 0.9f, 1.2f, 1f };;
    private float mMaxYValue;

    public ChartView(Context context) {

        super(context);
    }

    protected void onDraw(Canvas canvas) {

        calculateMaxYValue();
        drawBarGraph(canvas);
        drawIndicators(canvas);
    }

    private void calculateMaxYValue() {

        mMaxYValue = mMaxIndicator;

        for(int i = 0; i < mInput.length; i++) {

            if (mInput[i] > mMaxYValue) {

                mMaxYValue = mInput[i];
            }
        }
    }

    private void drawBarGraph(Canvas canvas) {

        Rect bound = canvas.getClipBounds();
        float barSize = bound.width() / mInput.length * (1 - BAR_GRAPH_EMPTY_SPACE_RATIO);
        float emptySize = bound.width() / mInput.length * (BAR_GRAPH_EMPTY_SPACE_RATIO);

        float xPosCursor = emptySize;

        Paint barPaint = new Paint();
        barPaint.setColor(mBarColor);

        Paint barHighlightPaint = new Paint();
        barHighlightPaint.setColor(mBarHighlightColor);

        for (int i = 0; i < mInput.length; i++) {

            float yPos = bound.height() * (1 - mInput[i] / mMaxYValue);

            if (mInput[i] < mMaxIndicator && mInput[i] > mMinIndicator) {

                canvas.drawRect(xPosCursor,
                        yPos,
                        xPosCursor + barSize,
                        (float) (bound.height()),
                        barHighlightPaint);

                canvas.drawRect(xPosCursor + BAR_HIGHTLIGHT_WIDTH,
                        yPos + BAR_HIGHTLIGHT_WIDTH,
                        xPosCursor + barSize - BAR_HIGHTLIGHT_WIDTH,
                        (float) (bound.height()) - BAR_HIGHTLIGHT_WIDTH,
                        barPaint);
            } else {

                canvas.drawRect(xPosCursor,
                        yPos,
                        xPosCursor + barSize,
                        (float) (bound.height()),
                        barPaint);
            }

            xPosCursor += barSize + emptySize;
        }
    }

    private void drawIndicators(Canvas canvas) {

        Rect bound = canvas.getClipBounds();
        float maxIndicatorYValue = bound.height() * (1 - mMaxIndicator / mMaxYValue);
        float minIndicatorYValue = bound.height() * (1 - mMinIndicator / mMaxYValue);

        Paint indicatorRectPaint = new Paint();
        indicatorRectPaint.setColor(mIndicatorRectColor);

        canvas.drawRect(0,
                maxIndicatorYValue,
                (float)(bound.width()),
                minIndicatorYValue,
                indicatorRectPaint);

        Paint indicatorPaint = new Paint();
        indicatorPaint.setColor(mIndicatorColor);
        indicatorPaint.setStrokeWidth(INDICATOR_STROKE_WIDTH);

        canvas.drawLine(0,
                        maxIndicatorYValue,
                        (float)(bound.width()),
                        maxIndicatorYValue,
                        indicatorPaint);

        canvas.drawLine(0,
                        minIndicatorYValue,
                        (float)(bound.width()),
                        minIndicatorYValue,
                        indicatorPaint);
    }

    public void setMaxIndicator(float maxIndicator) {

        mMaxIndicator = maxIndicator;
    }

    public void setMinIndicator(float minIndicator) {

        mMinIndicator = minIndicator;
    }

    public void setBarColor(int barColor) {

        mBarColor = barColor;
    }

    public void setBarHighlightColor(int barHighlightColor) {

        mBarHighlightColor = barHighlightColor;
    }

    public void setIndicatorColor(int indicatorColor) {

        mIndicatorColor = indicatorColor;
    }

    public void setInput(float[] input) {

        mInput = input;
    }

    private int dpToPx(int dp) {

        Resources r = getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }
}
