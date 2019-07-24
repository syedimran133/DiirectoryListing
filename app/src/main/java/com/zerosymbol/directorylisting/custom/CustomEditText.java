package com.zerosymbol.directorylisting.custom;

/**
 * Created by root on 03-01-2017.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.zerosymbol.directorylisting.support.AppValidate;

public class CustomEditText extends android.support.v7.widget.AppCompatEditText {

    private Drawable drawableRight;
    private Drawable drawableLeft;
    private Drawable drawableTop;
    private Drawable drawableBottom;

    int actionX, actionY;
    public CustomEditText(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "font/arial.ttf");
        this.setTypeface(face);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "font/arial.ttf");
        this.setTypeface(face);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "font/arial.ttf");
        this.setTypeface(face);
    }

    @Override
    public void setError(CharSequence error) {
        super.setError(error);
        try {
            if (AppValidate.isValidString(error.toString()))
                requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top,
                                     Drawable right, Drawable bottom) {
        if (left != null) {
            drawableLeft = left;
        }
        if (right != null) {
            drawableRight = right;
        }
        if (top != null) {
            drawableTop = top;
        }
        if (bottom != null) {
            drawableBottom = bottom;
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }
}