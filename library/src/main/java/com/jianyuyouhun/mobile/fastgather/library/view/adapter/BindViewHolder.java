package com.jianyuyouhun.mobile.fastgather.library.view.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * bindView放在ViewHolder内部执行
 *
 * @param <T>
 */
public abstract class BindViewHolder<T> extends SimpleRecyclerAdapter.ViewHolder {

    protected Context context;

    public BindViewHolder(@NonNull Context context, @NonNull View itemView) {
        super(itemView);
        this.context = context;
    }

    public abstract void onBindView(@NonNull T info, int pos, @NonNull SimpleRecyclerAdapter adapter);
}