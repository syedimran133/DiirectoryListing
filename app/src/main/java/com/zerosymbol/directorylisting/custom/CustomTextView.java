package com.zerosymbol.directorylisting.custom;

/**
 * Created by root on 03-01-2017.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Toast;

import com.zerosymbol.directorylisting.support.AppSingle;
import com.zerosymbol.directorylisting.support.AppValidate;

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

//com.zerosymbol.directorylisting.custom.CustomTextView
    public CustomTextView(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "font/arial.ttf");//ArialMTBold.ttf
        this.setTypeface(face);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "font/arial.ttf");
        this.setTypeface(face);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "font/arial.ttf");
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