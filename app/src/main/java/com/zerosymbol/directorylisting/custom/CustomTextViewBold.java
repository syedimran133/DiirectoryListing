package com.zerosymbol.directorylisting.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Toast;

import com.zerosymbol.directorylisting.support.AppSingle;
import com.zerosymbol.directorylisting.support.AppValidate;

public class CustomTextViewBold extends android.support.v7.widget.AppCompatTextView {


    //com.zerosymbol.directorylisting.custom.CustomTextView
    public CustomTextViewBold(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "font/ArialMTBold.ttf");
        this.setTypeface(face);
    }

    public CustomTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "font/ArialMTBold.ttf");
        this.setTypeface(face);
    }

    public CustomTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "font/ArialMTBold.ttf");
        this.setTypeface(face);
    }

    @Override
    public void setError(CharSequence error) {
        super.setError(error);
        try {
            if (AppValidate.isValidString(error.toString()))
                Toast.makeText(AppSingle.getInstance().getActivity(), error, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

}
