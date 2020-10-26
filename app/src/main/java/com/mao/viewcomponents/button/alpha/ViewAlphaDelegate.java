package com.mao.viewcomponents.button.alpha;

import android.view.View;

import java.lang.ref.WeakReference;

/**
 * @author: mao
 * @date: 20-10-23
 * @desc: view的工具类
 */
public class ViewAlphaDelegate implements IViewAlpha{
    //与透明度相关的设置
    private boolean mNeedChangeAlphaWhenPress = true;
    private boolean mNeedChangeAlphaWhenDisable = true;
    private float mNormalAlpha = 1f;
    private float mPressedAlpha = .5f;
    private float mDisabledAlpha = .5f;

    private WeakReference<View> mHost;

    public ViewAlphaDelegate(View target) {
        this.mHost = new WeakReference<>(target);
    }

    public void setPressed(View current, boolean pressed) {
        View host = mHost.get();
        if (host == null|current==null) {
            return;
        }
        if (current.isEnabled()) {
            host.setAlpha(mNeedChangeAlphaWhenPress && pressed && current.isClickable() ? mPressedAlpha : mNormalAlpha);
        } else {
            if (mNeedChangeAlphaWhenDisable) {
                host.setAlpha(mDisabledAlpha);
            }
        }
    }

    public void setEnabled(View current, boolean enabled) {
        View host = mHost.get();
        if (host == null) {
            return;
        }
        float alphaForIsEnable;
        if (mNeedChangeAlphaWhenDisable) {
            alphaForIsEnable = enabled ? mNormalAlpha : mDisabledAlpha;
        } else {
            alphaForIsEnable = mNormalAlpha;
        }
        if (current != host && host.isEnabled() != enabled) {
            host.setEnabled(enabled);
        }
        host.setAlpha(alphaForIsEnable);
    }

    @Override
    public void setNeedChangeAlphaWhenDisable(boolean needChangeAlphaWhenDisable) {
        mNeedChangeAlphaWhenDisable = needChangeAlphaWhenDisable;
        View host = mHost.get();
        if (host != null) {
            setEnabled(host, host.isEnabled());
        }
    }

    @Override
    public void setNeedChangeAlphaWhenPress(boolean needChangeAlphaWhenPress) {
        mNeedChangeAlphaWhenPress= needChangeAlphaWhenPress;
        View host = mHost.get();
        if (host != null) {
            setPressed(host, host.isPressed());
        }
    }


    public void setNormalAlpha(float normalAlpha) {
        this.mNormalAlpha = normalAlpha;
    }

    public void setPressedAlpha(float pressedAlpha) {
        this.mPressedAlpha = pressedAlpha;
    }

    public void setDisabledAlpha(float disabledAlpha) {
        this.mDisabledAlpha = disabledAlpha;
    }
}
