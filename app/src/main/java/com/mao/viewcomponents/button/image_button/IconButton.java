package com.mao.viewcomponents.button.image_button;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.mao.viewcomponents.R;
import com.mao.viewcomponents.button.round.ViewRoundDelegate;

/**
 * @author: mao
 * @date: 20-10-23
 * @desc: 带图标的按钮
 */
public class IconButton extends FrameLayout {

    private final LinearLayout mRealLayout;
    private final ImageView mLeftView;
    private final ImageView mRightView;
    private final TextView mCenterView;
    private ViewRoundDelegate mViewRoundDelegate;

    public IconButton(Context context) {
        this(context,null);
    }

    public IconButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.buttonStyle);
    }

    public IconButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRealLayout = new LinearLayout(context);
        mLeftView = new ImageView(context);
        mCenterView = new TextView(context);
        mRightView = new ImageView(context);

        LinearLayout.LayoutParams leftParams = generateWrapLayoutParams();
        leftParams.gravity = Gravity.CENTER_VERTICAL;
        mRealLayout.addView(mLeftView, leftParams);

        LinearLayout.LayoutParams centerParams = generateWrapLayoutParams();
        centerParams.gravity = Gravity.CENTER_VERTICAL;
        mRealLayout.addView(mCenterView, centerParams);

        LinearLayout.LayoutParams rightParams = generateWrapLayoutParams();
        rightParams.gravity = Gravity.CENTER_VERTICAL;
        mRealLayout.addView(mRightView, rightParams);

        addView(mRealLayout,  new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER));

        if (getBackground() == null) {
            StateListDrawable drawable = new StateListDrawable();
            drawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(Color.GREEN));
            drawable.addState(new int[]{android.R.attr.state_selected}, new ColorDrawable(Color.RED));
            drawable.addState(new int[]{android.R.attr.state_focused}, new ColorDrawable((Color.GREEN)));
            drawable.addState(new int[]{}, new ColorDrawable(Color.WHITE));
            setBackground(drawable);
            setFocusable(true);
            setClickable(true);
        }

        setCenterText("你好");
        setLeftIcon(R.mipmap.user);
        setRightIcon(R.mipmap.user);
        setLeftIconPadding(30,0,20,0);
    }

    private ViewRoundDelegate getRoundViewHelper() {
        if (mViewRoundDelegate == null) {
            mViewRoundDelegate = new ViewRoundDelegate(this);
        }
        return mViewRoundDelegate;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        getRoundViewHelper().dispatchRoundDraw(canvas);
    }

    /**
     * 设置左边的图标
     */
    public IconButton setLeftIcon(@DrawableRes int id) {
        setLeftIcon(ContextCompat.getDrawable(getContext(), id));
        return this;
    }

    public IconButton setLeftIcon(Drawable drawable) {
        mLeftView.setImageDrawable(drawable);
        return this;
    }

    /**
     * 设置右边的图标
     */
    public IconButton setRightIcon(@DrawableRes int id) {
        setRightIcon(ContextCompat.getDrawable(getContext(), id));
        return this;
    }

    public IconButton setRightIcon(Drawable drawable) {
        mRightView.setImageDrawable(drawable);
        return this;
    }

    /**
     * 设置左边图标的padding
     */
    public IconButton setLeftIconPaddingLeft(int leftPadding){
        setLeftIconPadding(0,0,leftPadding,0);
        return this;
    }

    public IconButton setLeftIconPaddingRight(int rightPadding){
        setLeftIconPadding(0,0,rightPadding,0);
        return this;
    }

    public IconButton setLeftIconPaddingTop(int topPadding){
        setLeftIconPadding(0,0,topPadding,0);
        return this;
    }

    public IconButton setLeftIconPaddingBottom(int bottomPadding){
        setLeftIconPadding(0,0,0,bottomPadding);
        return this;
    }

    public IconButton setLeftIconPadding(int leftPadding,
                                         int topPadding,
                                         int rightPadding,
                                         int bottomPadding){
        mLeftView.setPaddingRelative(leftPadding,topPadding,rightPadding,bottomPadding);
        return this;
    }

    /**
     * 设置右边图标的padding
     */
    public IconButton setRightIconPaddingLeft(int leftPadding){
        setRightIconPadding(0,0,leftPadding,0);
        return this;
    }

    public IconButton setRightIconPaddingRight(int rightPadding){
        setRightIconPadding(0,0,rightPadding,0);
        return this;
    }

    public IconButton setRightIconPaddingTop(int topPadding){
        setRightIconPadding(0,0,topPadding,0);
        return this;
    }

    public IconButton setRightIconPaddingBottom(int bottomPadding){
        setRightIconPadding(0,0,0,bottomPadding);
        return this;
    }

    public IconButton setRightIconPadding(int leftPadding,
                                         int topPadding,
                                         int rightPadding,
                                         int bottomPadding){
        mRightView.setPaddingRelative(leftPadding,topPadding,rightPadding,bottomPadding);
        return this;
    }


    /**
     * 设置中间的文本
     */
    public IconButton setCenterText(@StringRes int id) {
        return setCenterText(getResources().getString(id));
    }

    public IconButton setCenterText(CharSequence text) {
        mCenterView.setText(text);
        return this;
    }

    /**
     * 设置中间的文本字体大小
     */
    public IconButton setCenterTextSize(int unit, float size) {
        mCenterView.setTextSize(unit, size);
        return this;
    }

    /**
     * 设置中间的文本的颜色
     */
    public IconButton setCenterTextColor(@ColorInt int color) {
        mCenterView.setTextColor(color);
        return this;
    }

    public LinearLayout.LayoutParams generateWrapLayoutParams() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable == null)
            return null;
        else if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();

        if (!(intrinsicWidth > 0 && intrinsicHeight > 0))
            return null;

        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = createBitmapSafely(intrinsicWidth, intrinsicHeight, config,1);
        if (bitmap==null) return null;
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {
        try {
            return Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (retryCount > 0) {
                System.gc();
                return createBitmapSafely(width, height, config, retryCount - 1);
            }
            return null;
        }
    }
}
