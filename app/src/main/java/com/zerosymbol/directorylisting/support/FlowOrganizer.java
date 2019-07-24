package com.zerosymbol.directorylisting.support;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.Toast;


/**
 * Created by root on 12-12-2016.
 */

public class FlowOrganizer {

    private static FlowOrganizer _app;
    private static FrameLayout frameParent;

    public static FlowOrganizer getInstance() {
        if (_app == null) {
            _app = new FlowOrganizer();
        }
        return _app;
    }

    public void initParentFrame(FrameLayout frameParent) {
        this.frameParent = frameParent;
    }

    private FlowOrganizer() {

    }

    public void add(Fragment fragment) {
        add(fragment, false, null);
    }

    public void add(Fragment fragment, boolean isToAddBack) {
        add(fragment, isToAddBack, null);
    }

    public void add(Fragment fragment, boolean isToAddBack, Bundle bundle) {
        try {
            if (frameParent == null) {
                Toast.makeText(AppSingle.getInstance(), "No Parant Attached to FlowOrganizer", Toast.LENGTH_SHORT).show();
                return;
            }
            FragmentManager fm = AppSingle.getInstance().getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment.setArguments(bundle);
            ft.add(frameParent.getId(), fragment);
            if (isToAddBack)
                ft.addToBackStack(fragment.getClass().getName());
            else
                clearBackStack();
            ft.commit();
        } catch (Exception e) {
        }
    }

    public void replace(Fragment fragment) {
        replace(fragment, false, null);
    }

    public void replace(Fragment fragment, boolean isToAddBack) {
        replace(fragment, isToAddBack, null);
    }

    public void replace(Fragment fragment, boolean isToAddBack, Bundle bundle) {
        try {
            if (frameParent == null) {
                Toast.makeText(AppSingle.getInstance(), "No Parant Attached to FlowOrganizer", Toast.LENGTH_SHORT).show();
                return;
            }
            FragmentManager fm = AppSingle.getInstance().getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment.setArguments(bundle);
            ft.replace(frameParent.getId(), fragment);
            if (isToAddBack)
                ft.addToBackStack(fragment.getClass().getName());
            else
                clearBackStack();
            ft.commit();
        } catch (Exception e) {
        }
    }

    public boolean hasNoMoreBacks() {
        FragmentManager fm = AppSingle.getInstance().getActivity().getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        if (count < 1)
            return true;
        else
            return false;
    }

    public void clearBackStack() {
        FragmentManager fm = AppSingle.getInstance().getActivity().getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public void popUpBackToMain() {
        FragmentManager fm = AppSingle.getInstance().getActivity().getSupportFragmentManager();
        int size = fm.getBackStackEntryCount();
        for (int i = 0; i < size; i++) {
            fm.popBackStack();
        }
    }

    public void popUpBackTo(int skipNoOfFragment) {
        try {
            FragmentManager fm = AppSingle.getInstance().getActivity().getSupportFragmentManager();
            int size = fm.getBackStackEntryCount();
            if (skipNoOfFragment > size)
                return;
            else
                size = skipNoOfFragment;
            for (int i = 0; i < size; ++i) {
                fm.popBackStack();
            }
        } catch (Exception e) {
        }
    }

    public void popUpBackTo(Fragment fragment) {
        FragmentManager fm = AppSingle.getInstance().getActivity().getSupportFragmentManager();
        fm.popBackStack(fragment.getClass().getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}
