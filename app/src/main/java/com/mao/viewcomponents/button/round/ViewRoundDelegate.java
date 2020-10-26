package com.mao.viewcomponents.button.round;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/**
 * @author: mao
 * @date: 20-10-23
 * @desc: view的工具类
 */
public class ViewRoundDelegate implements IViewRound{

    public static final int SIDE_NONE = 0;
    public static final int SIDE_TOP = 1;
    public static final int SIDE_RIGHT = 2;
    public static final int SIDE_BOTTOM = 3;
    public static final int SIDE_LEFT = 4;
    private final RectF mBorderRect;

    @IntDef(value = {
            SIDE_NONE,
            SIDE_TOP,
            SIDE_RIGHT,
            SIDE_BOTTOM,
            SIDE_LEFT})
    @Retention(RetentionPolicy.SOURCE)
    @interface NotRadiusSide {}

    public static final int RADIUS_OF_HALF_VIEW_HEIGHT = -1;
    public static final int RADIUS_OF_HALF_VIEW_WIDTH = -2;

    private WeakReference<View> mHost;
    private boolean mShouldUseRadiusArray=false;
    private int mRadius;
    private float[] mRadiusArray;

    private int mOuterNormalColor = Color.WHITE;
    private Paint mClipPaint;
    private PorterDuffXfermode mMode;
    private Path mPath = new Path();

    private  int mNotRadiusSide = SIDE_RIGHT;

    public ViewRoundDelegate(View target) {
        this.mHost = new WeakReference<>(target);
        mBorderRect = new RectF();
        mMode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        mClipPaint = new Paint();
        mClipPaint.setAntiAlias(true);
        setRadiusAndShadow(40, SIDE_NONE);
    }

    public void dispatchRoundDraw(Canvas canvas) {
        View owner = mHost.get();
        if (owner == null) {
            return;
        }
        if (useFeature()) {
            return;
        }
        int radius = getRealRadius();
        boolean needCheckFakeOuterNormalDraw = radius > 0;
        if (!needCheckFakeOuterNormalDraw) {
            return;
        }
        int width = canvas.getWidth(), height = canvas.getHeight();
        canvas.save();
        canvas.translate(owner.getScrollX(), owner.getScrollY());

        mBorderRect.set(
                owner.getPaddingLeft(),
                owner.getPaddingTop(),
                width - owner.getPaddingRight(),
                height - owner.getPaddingBottom());

        if(mShouldUseRadiusArray){
            if(mRadiusArray == null){
                mRadiusArray = new float[8];
            }
            if (mNotRadiusSide == SIDE_TOP) {
                mRadiusArray[4] = radius;
                mRadiusArray[5] = radius;
                mRadiusArray[6] = radius;
                mRadiusArray[7] = radius;
            } else if (mNotRadiusSide == SIDE_RIGHT) {
                mRadiusArray[0] = radius;
                mRadiusArray[1] = radius;
                mRadiusArray[6] = radius;
                mRadiusArray[7] = radius;
            } else if (mNotRadiusSide == SIDE_BOTTOM) {
                mRadiusArray[0] = radius;
                mRadiusArray[1] = radius;
                mRadiusArray[2] = radius;
                mRadiusArray[3] = radius;
            } else if (mNotRadiusSide == SIDE_LEFT) {
                mRadiusArray[2] = radius;
                mRadiusArray[3] = radius;
                mRadiusArray[4] = radius;
                mRadiusArray[5] = radius;
            }
        }

        int layerId = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawColor(mOuterNormalColor);
        mClipPaint.setColor(mOuterNormalColor);
        mClipPaint.setStyle(Paint.Style.FILL);
        mClipPaint.setXfermode(mMode);
        if (!mShouldUseRadiusArray) {
            canvas.drawRoundRect(mBorderRect, radius, radius, mClipPaint);
        } else {
            drawRoundRect(canvas, mBorderRect, mRadiusArray, mClipPaint);
        }
        mClipPaint.setXfermode(null);
        canvas.restoreToCount(layerId);

        canvas.restore();
    }

    /**
     * 获取radius
     * @return radius
     */
    private int getRealRadius(){
        View host = mHost.get();
        if (host == null) {
            return mRadius;
        }
        if(mRadius == RADIUS_OF_HALF_VIEW_HEIGHT){
            return host.getHeight() /2;
        }else if(mRadius == RADIUS_OF_HALF_VIEW_WIDTH){
            return host.getWidth() / 2;
        }else{
            return mRadius;
        }
    }

    private void drawRoundRect(Canvas canvas, RectF rect, float[] radiusArray, Paint paint) {
        mPath.reset();
        mPath.addRoundRect(rect, radiusArray, Path.Direction.CW);
        canvas.drawPath(mPath, paint);

    }

    public void setRadiusAndShadow(int radius, int hideRadiusSide) {
        final View owner = mHost.get();
        if (owner == null) {
            return;
        }
        mRadius = radius;
        mNotRadiusSide = hideRadiusSide;
        mShouldUseRadiusArray = isRadiusWithSideHidden();

        if (useFeature()) {
            owner.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                @TargetApi(21)
                public void getOutline(View view, Outline outline) {
                    int w = view.getWidth();
                    int h = view.getHeight();
                    if (w == 0 || h == 0) {
                        return;
                    }
                    float radius = getRealRadius();
                    int min = Math.min(w, h);
                    if (radius * 2 > min) {
                        radius = min / 2F;
                    }
                    if (mShouldUseRadiusArray) {
                        int left = 0, top = 0, right = w, bottom = h;
                        if (mNotRadiusSide == SIDE_LEFT) {
                            left -= radius;
                        } else if (mNotRadiusSide == SIDE_TOP) {
                            top -= radius;
                        } else if (mNotRadiusSide == SIDE_RIGHT) {
                            right += radius;
                        } else if (mNotRadiusSide == SIDE_BOTTOM) {
                            bottom += radius;
                        }
                        outline.setRoundRect(left, top,
                                right, bottom, radius);
                        return;
                    }
                    int left = view.getPaddingLeft();
                    int top = view.getPaddingTop();
                    int right = Math.max(left + 1, w - view.getPaddingRight());
                    int bottom = Math.max(top + 1, h - view.getPaddingBottom());
                    if (radius <= 0) {
                        outline.setRect(left, top,
                                right, bottom);
                    } else {
                        outline.setRoundRect(left, top,
                                right, bottom, radius);
                    }
                }
            });
            owner.setClipToOutline(mRadius == RADIUS_OF_HALF_VIEW_WIDTH || mRadius == RADIUS_OF_HALF_VIEW_HEIGHT || mRadius > 0);

        }
        owner.invalidate();
    }

    public boolean isRadiusWithSideHidden() {
        return (mRadius == RADIUS_OF_HALF_VIEW_HEIGHT ||
                mRadius == RADIUS_OF_HALF_VIEW_WIDTH ||
                mRadius > 0) && mNotRadiusSide != SIDE_NONE;
    }

    public static boolean useFeature() {
        return Build.VERSION.SDK_INT >= 21;
    }
}
