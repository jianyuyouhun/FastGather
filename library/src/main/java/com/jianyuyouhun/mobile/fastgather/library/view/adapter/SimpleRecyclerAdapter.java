package com.jianyuyouhun.mobile.fastgather.library.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jianyuyouhun.inject.ViewInjector;
import com.jianyuyouhun.mobile.fastgather.library.utils.injector.ManagerInjector;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * recyclerView适配器
 * Created by wangyu on 2018/4/3.
 */

public abstract class SimpleRecyclerAdapter <T, VH extends SimpleRecyclerAdapter.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected Context context;
    private List<T> data;

    public SimpleRecyclerAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
        ManagerInjector.injectManager(this);
    }

    public void setData(List<T> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(T data) {
        this.data.add(data);
        notifyDataSetChanged();
    }

    public void addData(T data, int pos) {
        this.data.add(pos, data);
        notifyDataSetChanged();
    }

    public void addToFirst(T data) {
        this.data.add(0, data);
        notifyDataSetChanged();
    }

    public void removeData(T data) {
        this.data.remove(data);
        notifyDataSetChanged();
    }

    public void removeData(List<T> data) {
        this.data.removeAll(data);
        notifyDataSetChanged();
    }

    /**
     * 替换指定位置的数据
     *
     * @param data 新数据
     * @param pos  位置
     */
    public boolean changeItem(T data, int pos) {
        if (pos >= getItemCount()) {
            return false;
        }
        this.data.remove(pos);
        this.data.add(pos, data);
        notifyDataSetChanged();
        return true;
    }

    public List<T> getData() {
        return new ArrayList<>(data);
    }

    /**
     * 获取适配器的最后一项，如果适配器大小等于0，将返回null
     */
    @Nullable
    public T getLastItem() {
        int count = getItemCount();
        if (count == 0) {
            return null;
        } else {
            return getItem(count - 1);
        }
    }

    /**
     * 获取适配器的第一项，如果适配器大小等于0，将返回null
     */
    @Nullable
    public T getFirstItem() {
        int count = getItemCount();
        if (count == 0) {
            return null;
        } else {
            return getItem(0);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public T getItem(int pos) {
        return data.get(pos);
    }

    @Deprecated
    @Override
    public VH onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        if (getLayoutId() == 0) {
            return onCreateViewHolder(null, viewType, parent);
        }
        View view = LayoutInflater.from(context).inflate(getLayoutId(), parent, false);
        return onCreateViewHolder(view, viewType, parent);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    public abstract VH onCreateViewHolder(View view, int viewType, @NonNull ViewGroup parent);

    @Deprecated
    @Override
    public void onBindViewHolder(@NotNull VH holder, int position) {
        onBindViewHolder(holder, getItem(position), position);
    }

    public abstract void onBindViewHolder(@NonNull VH holder, @NonNull T data, int position);

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ViewInjector.inject(this, itemView);
        }
    }
}

