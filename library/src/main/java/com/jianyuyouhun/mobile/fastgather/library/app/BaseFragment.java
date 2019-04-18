package com.jianyuyouhun.mobile.fastgather.library.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.jianyuyouhun.inject.ViewInjector;
import com.jianyuyouhun.mobile.fastgather.library.utils.ToastUtil;
import com.jianyuyouhun.mobile.fastgather.library.utils.injector.ManagerInjector;

/**
 * fragment基类
 * Created by wangyu on 2017/12/7.
 */

public abstract class BaseFragment extends Fragment {
    private boolean isDestroy = false;
    private long mInsertDt = System.currentTimeMillis();
    protected String TAG;
    public View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        isDestroy = false;
        ManagerInjector.injectManager(this);
    }

    /**
     * 请在下面方法中实现逻辑
     * {@link #onCreateView(View, ViewGroup, Bundle)}
     *
     * @param inflater           LayoutInflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return view
     */
    @Nullable
    @Deprecated
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {
            ViewParent parent = rootView.getParent();
            if (parent != null) {
                ViewGroup viewGroup = (ViewGroup) parent;
                viewGroup.removeView(rootView);
            }
        } else {
            int layoutResId = getLayoutResId();
            if (layoutResId != 0) {
                rootView = inflater.inflate(layoutResId, container, false);
                ViewInjector.inject(this, rootView);
            } else {
                TextView tView = new TextView(getContext());
                tView.setText(this.getClass().getSimpleName());
                rootView = tView;
            }
        }
        onCreateView(rootView, container, savedInstanceState);
        return rootView;
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * 绑定好layoutId之后的onCreateView
     *
     * @param rootView           rootView
     * @param parent             viewGroup
     * @param savedInstanceState savedInstanceState
     */
    protected void onCreateView(View rootView, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestroy = true;
    }

    public boolean isDestroy() {
        return isDestroy;
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @NonNull
    public Context getContext() {
        return getBaseActivity();
    }

    public long getInsertDt() {
        return mInsertDt;
    }

    public void showToast(@StringRes int resId) {
        ToastUtil.showToast(resId);
    }

    public void showToast(String toast) {
        ToastUtil.showToast(toast);
    }

    public void showProgressDialog() {
        showProgressDialog("");
    }

    public void showProgressDialog(String message) {
        if (isDestroy) return;
        getBaseActivity().showProgressDialog(message);
    }

    public void dismissProgressDialog() {
        if (isDestroy) return;
        getBaseActivity().dismissProgressDialog();
    }

    /**
     * 启动页面
     */
    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(getBaseActivity(), cls));
    }

    /**
     * 启动页面
     */
    public void startActivityForResult(Class<? extends Activity> cls, int requestCode) {
        startActivityForResult(new Intent(getBaseActivity(), cls), requestCode);
    }

    /**
     * 获取handler
     *
     * @return app的handler
     */
    protected Handler getHandler() {
        return JApp.getHandler();
    }
}
