package com.mao.viewcomponents.button.alpha;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author: mao
 * @date: 20-10-23
 * @desc: 透明度按钮
 */
public class AlphaButton extends AppCompatTextView implements IViewAlpha{

    private ViewAlphaDelegate mViewAlphaDelegate;

    public AlphaButton(Context context) {
        super(context);
    }

    public AlphaButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ViewAlphaDelegate getAlphaViewHelper() {
        if (mViewAlphaDelegate == null) {
            mViewAlphaDelegate = new ViewAlphaDelegate(this);
        }
        return mViewAlphaDelegate;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        getAlphaViewHelper().setPressed(this,pressed);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        getAlphaViewHelper().setEnabled(this,enabled);
    }

    @Override
    public void setNeedChangeAlphaWhenDisable(boolean needChangeAlphaWhenDisable) {
        getAlphaViewHelper().setNeedChangeAlphaWhenDisable(needChangeAlphaWhenDisable);
    }

    @Override
    public void setNeedChangeAlphaWhenPress(boolean needChangeAlphaWhenPress) {
        GradientDrawable drawable=new GradientDrawable();
        getAlphaViewHelper().setNeedChangeAlphaWhenPress(needChangeAlphaWhenPress);
    }
}
