package com.zerosymbol.directorylisting.custom;

/**
 * Created by root on 03-01-2017.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class CustomTextViewTitle extends android.support.v7.widget.AppCompatTextView {


    public CustomTextViewTitle(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "font/arial.ttf");
        this.setTypeface(face);
    }

    public CustomTextViewTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "font/arial.ttf");
        this.setTypeface(face);
    }

    public CustomTextViewTitle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "font/arial.ttf");
        this.setTypeface(face);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

}