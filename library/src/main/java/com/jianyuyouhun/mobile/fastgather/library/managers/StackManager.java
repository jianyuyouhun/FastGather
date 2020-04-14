package com.jianyuyouhun.mobile.fastgather.library.managers;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.jianyuyouhun.mobile.fastgather.library.app.BaseActivity;
import com.jianyuyouhun.mobile.fastgather.library.app.BaseManager;
import com.jianyuyouhun.mobile.fastgather.library.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * activity栈管理器
 * Created by wangyu on 2017/12/8.
 */

public class StackManager extends BaseManager {

    private List<Activity> activityList = new ArrayList<>();

    @Override
    public void onManagerCreate(Application application) {
        super.onManagerCreate(application);
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (!(activity instanceof BaseActivity)) {
                    Logger.w(TAG, activity.toString() + "is not instance of BaseActivity, please be insure that is your individualized design");
                }
                activityList.add(activity);
                Logger.d(TAG, "onActivityCreated " + activity.toString());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Logger.d(TAG, "onActivityStarted " + activity.toString());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Logger.d(TAG, "onActivityResumed " + activity.toString());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Logger.d(TAG, "onActivityPaused " + activity.toString());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Logger.d(TAG, "onActivityStopped " + activity.toString());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Logger.d(TAG, "onActivityDestoryed " + activity.toString());
                activityList.remove(activity);
            }
        });
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    /**
     * 判断app还有没有activity活跃
     *
     * @return
     */
    public AppState getAppState() {
        return activityList.size() != 0 ? AppState.RUNNING : AppState.DIED;
    }

    public enum AppState {
        /** app运行状态 */
        RUNNING,
        DIED,
        ;
    }
}
