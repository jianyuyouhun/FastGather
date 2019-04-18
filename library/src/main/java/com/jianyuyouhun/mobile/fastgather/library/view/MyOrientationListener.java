package com.jianyuyouhun.mobile.fastgather.library.view;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.OrientationEventListener;

import com.jianyuyouhun.mobile.okrequester.library.Logger;


public class MyOrientationListener extends OrientationEventListener {

    private static String TAG = MyOrientationListener.class.getSimpleName();

    private Activity activity;

    public MyOrientationListener(Activity context) {
        super(context);
        this.activity = context;
    }

    public MyOrientationListener(Activity context, int rate) {
        super(context, rate);
        this.activity = context;
    }

    @Override
    public void onOrientationChanged(int orientation) {
        Logger.d(TAG, "orientation" + orientation);
        int screenOrientation = activity.getResources().getConfiguration().orientation;
        if (((orientation >= 0) && (orientation < 45)) || (orientation > 315)) {//设置竖屏
            if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT && orientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
                Logger.d(TAG, "设置竖屏");
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        } else if (orientation > 225 && orientation < 315) { //设置横屏
            Logger.d(TAG, "设置横屏");
            if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        } else if (orientation > 45 && orientation < 135) {// 设置反向横屏
            Logger.d(TAG, "反向横屏");
            if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
//                    oriBtn.setText("反向横屏");
            }
        } else if (orientation > 135 && orientation < 225) {
            Logger.d(TAG, "反向竖屏");
            if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
//                oriBtn.setText("反向竖屏");
            }
        }
    }
}
