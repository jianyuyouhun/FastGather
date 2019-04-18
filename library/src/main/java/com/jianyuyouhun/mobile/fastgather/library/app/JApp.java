package com.jianyuyouhun.mobile.fastgather.library.app;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.jianyuyouhun.mobile.fastgather.library.BuildConfig;
import com.jianyuyouhun.mobile.fastgather.library.app.exception.ExceptionCaughtAdapter;
import com.jianyuyouhun.mobile.fastgather.library.managers.SettingCacheManager;
import com.jianyuyouhun.mobile.fastgather.library.managers.StackManager;
import com.jianyuyouhun.mobile.fastgather.library.utils.AppUtils;
import com.jianyuyouhun.mobile.fastgather.library.utils.injector.ManagerInjector;
import com.jianyuyouhun.mobile.fastgather.library.utils.kt.LoggerKt;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * app基类
 * Created by wangyu on 2018/3/26.
 */

public abstract class JApp extends Application {

    private static String TAG;

    private static String buglyId;

    private static JApp instance;

    private static boolean isDebug;

    private static Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 是否是在主线程中
     */
    private boolean mIsMainProcess = false;

    private HashMap<String, BaseManager> managersMap = new HashMap<>();

    public static Handler getHandler() {
        return handler;
    }

    public static JApp getInstance() {
        return instance;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TAG = getClass().getSimpleName();
        if (instance != null) {
            instance = this;
            return;
        }
        instance = this;
        isDebug = setDebugMode();
        buglyId = setBuglyId();
        initExceptionCatcher();
        initDependencies();
        String pidName = AppUtils.getUIPName(this);
        mIsMainProcess = pidName.equals(getPackageName());
        initApp();
    }

    protected boolean setDebugMode() {
        return BuildConfig.DEBUG;
    }

    /**
     * 设置bugly崩溃统计
     * @return
     */
    protected String setBuglyId() {
        return "";
    }

    /**
     * 第三方框架初始化
     */
    protected void initDependencies() {

    }

    private void initExceptionCatcher() {
        if (isDebug) {
            //debug版本   启用崩溃日志捕获
            Thread.UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
            ExceptionCaughtAdapter exceptionCaughtAdapter = new ExceptionCaughtAdapter(handler);
            Thread.setDefaultUncaughtExceptionHandler(exceptionCaughtAdapter);
        } else {
            try {
                // 正式版本  启用崩溃提交
                CrashReport.initCrashReport(this, buglyId, false);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private void initApp() {
        List<BaseManager> managerList = new ArrayList<>();
        initCommonManagers(managerList);
        initManagers(managerList);
        for (BaseManager manager : managerList) {
            long time = System.currentTimeMillis();
            ManagerInjector.injectManager(manager);
            manager.onManagerCreate(this);
            Class<? extends BaseManager> baseManagerClass = manager.getClass();
            String name = baseManagerClass.getName();
            managersMap.put(name, manager);
            // 打印初始化耗时
            long spendTime = System.currentTimeMillis() - time;
            LoggerKt.lgE(TAG, baseManagerClass.getSimpleName() + "启动耗时(毫秒)：" + spendTime);
        }
        for (BaseManager manager : managerList) {
            manager.onAllManagerCreate();
        }
    }

    private void initCommonManagers(@NonNull List<BaseManager> managerList) {
        managerList.add(new StackManager());            //activity栈管理
        managerList.add(new SettingCacheManager());     //app设置缓存管理器，与用户相关的缓存建议另建立manager
    }

    /**
     * 注册控制器
     * @param managerList
     */
    protected abstract void initManagers(@NonNull List<BaseManager> managerList);


    /**
     * app是否处在主进程
     */
    public boolean isMainProcess() {
        return mIsMainProcess;
    }

    /**
     * 获取后台常驻Manager
     *
     * @param <Manager> Manager类
     * @return manager
     */
    @SuppressWarnings("unchecked")
    public <Manager extends BaseManager> Manager getManager(Class<Manager> manager) {
        return getManager(manager.getName());
    }

    @SuppressWarnings("unchecked")
    public <Manager extends BaseManager> Manager getManager(String managerName) {
        Manager result = (Manager) managersMap.get(managerName);
        if (result == null) {
            throw new NullPointerException("无法获取到已注册的" + managerName + "，请确保目标Manager为后台常驻Manager类型");
        }
        return result;
    }
}
