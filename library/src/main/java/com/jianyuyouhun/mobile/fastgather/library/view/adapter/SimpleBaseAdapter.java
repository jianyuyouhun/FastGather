package com.jianyuyouhun.mobile.fastgather.library.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jianyuyouhun.inject.ViewInjector;
import com.jianyuyouhun.mobile.fastgather.library.utils.injector.ManagerInjector;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * 适配器基类封装
 * Created by wangyu on 2018/1/20.
 */

public abstract class SimpleBaseAdapter<Data, VH extends SimpleBaseAdapter.ViewHolder> extends BaseAdapter {

    private Context context;

    protected List<Data> dataList = new ArrayList<>();

    public SimpleBaseAdapter(Context context) {
        this.context = context;
        ManagerInjector.injectManager(this);
    }

    /**
     * 添加要显示的数据到末尾 注意：调用本方法设置数据，listView不需要再调用：notifyDataSetChanged
     *
     * @param datas 数据列表
     */
    public void addData(List<Data> datas) {
        this.dataList.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 删除某条数据
     *
     * @param position
     */
    public void deleteData(int position) {
        this.dataList.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 获取所有的数据
     *
     * @return 适配器数据源
     */
    public List<Data> getData() {
        return new ArrayList<>(dataList);
    }

    /**
     * 设置要显示的数据，注意：调用本方法设置数据，listView不需要再调用：notifyDataSetChanged
     *
     * @param datas 数据
     */
    public void setData(List<Data> datas) {
        this.dataList.clear();
        addData(datas);
    }

    /**
     * 替换指定位置的数据
     *
     * @param data 新数据
     * @param pos  位置
     */
    public boolean changeItem(Data data, int pos) {
        if (pos >= getCount()) {
            return false;
        }
        dataList.remove(pos);
        dataList.add(pos, data);
        notifyDataSetChanged();
        return true;
    }

    public void addToLast(Data data) {
        dataList.add(data);
        notifyDataSetChanged();
    }

    public void addToPosition(int position, Data data) {
        dataList.add(position, data);
        notifyDataSetChanged();
    }

    public void addToFirst(Data data) {
        dataList.add(0, data);
        notifyDataSetChanged();
    }

    /**
     * 获取适配器的最后一项，如果适配器大小等于0，将返回null
     */
    public Data getLastItem() {
        int count = getCount();
        if (count == 0) {
            return null;
        } else {
            return getItem(count - 1);
        }
    }

    /**
     * 获取适配器的第一项，如果适配器大小等于0，将返回null
     */
    public Data getFirstItem() {
        int count = getCount();
        if (count == 0) {
            return null;
        } else {
            return getItem(0);
        }
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Data getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH viewHolder;
        if (convertView == null) {
            Class<VH> viewHolderCls = (Class<VH>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[1];
            try {
                viewHolder = viewHolderCls.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
                throw new RuntimeException("初始化ViewHolder失败： 请确保" + viewHolderCls.getSimpleName() + "为static class 并拥有无参构造函数");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("请确保" + viewHolderCls.getSimpleName() + "拥有public的无参构造函数");
            }
            convertView = LayoutInflater.from(context).inflate(getLayoutId(), parent, false);
            viewHolder.setItemView(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (VH) convertView.getTag();
        }

        Data data = getItem(position);
        bindView(viewHolder, data, position);
        return convertView;
    }

    /**
     * 获得资源文件的ID
     *
     * @return 资源文件id
     */
    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void bindView(@NonNull VH viewHolder, @NonNull Data data, int position);

    protected Context getContext() {
        return context;
    }

    public abstract static class ViewHolder {
        private View itemView;

        public final View getItemView() {
            return itemView;
        }

        final void setItemView(View itemView) {
            this.itemView = itemView;
            ViewInjector.inject(this, itemView);
        }
    }
}