package com.mao.viewcomponents.button.round;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.mao.viewcomponents.R;

/**
 * @author: mao
 * @date: 20-10-23
 * @desc: 带圆角的按钮
 */
public class RoundButton extends AppCompatButton {

    private ViewRoundDelegate mViewRoundDelegate;

    public RoundButton(Context context) {
        this(context,null);
    }

    public RoundButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.buttonStyle);
    }

    public RoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
}
