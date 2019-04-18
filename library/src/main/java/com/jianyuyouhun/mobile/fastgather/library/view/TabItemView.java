package com.jianyuyouhun.mobile.fastgather.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jianyuyouhun.mobile.fastgather.library.R;
import com.jianyuyouhun.mobile.fastgather.library.app.BaseFragment;
import com.jianyuyouhun.mobile.fastgather.library.utils.AppUtils;


/**
 * tabItemView
 * Created by wangyu on 2018/7/6.
 */

public class TabItemView extends FrameLayout {

    protected ImageView iconView;
    protected TextView textView;
    protected TextView badgeView;

    @Nullable
    private Drawable selectedDrawable = null;
    @Nullable
    private Drawable normalDrawable = null;
    @ColorInt
    private int normalColor;
    @ColorInt
    private int selectedColor;
    private boolean isItemSelected = false;
    private Class<? extends BaseFragment> fragmentClass;

    private long lastClick = 0;
    private boolean selectBlock = false;//选中锁，如果为true表示此次事件不计入双击判定

    private OnTabItemStateWillChangeDelegate delegate;

    public TabItemView(@NonNull Context context) {
        this(context, null);
    }

    public TabItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrs(context, attrs, defStyleAttr);
        registerListener();
        onViewInited();
    }

    private void initView(@NonNull Context context) {
        setClickable(true);
        View view = inflate(context, R.layout.view_tab_item, this);
        iconView = view.findViewById(R.id.tab_item_icon);
        textView = view.findViewById(R.id.tab_item_text);
        badgeView = view.findViewById(R.id.tab_item_badge);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabItemView, defStyleAttr, 0);
        String text = typedArray.getString(R.styleable.TabItemView_itemText);
        setText(text);
        int textSize = typedArray.getDimensionPixelSize(R.styleable.TabItemView_itemTextSize, AppUtils.dipToPx(context, 12));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        selectedDrawable = typedArray.getDrawable(R.styleable.TabItemView_itemImageSelected);
        normalDrawable = typedArray.getDrawable(R.styleable.TabItemView_itemImage);

        normalColor = typedArray.getColor(R.styleable.TabItemView_itemTextColor,
                getResources().getColor(R.color.colorPrimary));
        selectedColor = typedArray.getColor(R.styleable.TabItemView_itemTextColorSelected,
                getResources().getColor(R.color.colorAccent));
        isItemSelected = typedArray.getBoolean(R.styleable.TabItemView_isItemSelected, false);

        checkState();

        int width = typedArray.getDimensionPixelSize(R.styleable.TabItemView_itemWidth, AppUtils.dipToPx(context, 28));
        int height = typedArray.getDimensionPixelSize(R.styleable.TabItemView_itemHeight, AppUtils.dipToPx(context, 28));

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iconView.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        iconView.setLayoutParams(layoutParams);

        int padding = typedArray.getDimensionPixelSize(R.styleable.TabItemView_imagePadding, 0);
        int pl = typedArray.getDimensionPixelSize(R.styleable.TabItemView_imagePaddingLeft, padding);
        int pt = typedArray.getDimensionPixelSize(R.styleable.TabItemView_imagePaddingTop, padding);
        int pr = typedArray.getDimensionPixelSize(R.styleable.TabItemView_imagePaddingRight, padding);
        int pb = typedArray.getDimensionPixelSize(R.styleable.TabItemView_imagePaddingBottom, padding);

        iconView.setPadding(pl, pt, pr, pb);

        typedArray.recycle();
    }

    private void registerListener() {
        setOnClickListener(v -> {
            selectBlock = !isItemSelected;
            if (delegate == null) {
                isItemSelected = !isItemSelected;
                checkState();
            } else {
                boolean shouldChange = delegate.shouldChangeTabItemState(TabItemView.this);
                if (shouldChange) {
                    isItemSelected = !isItemSelected;
                    checkState();
                    delegate.onTabItemStateChanged(TabItemView.this);
                }
            }
            refreshCountEvent();
            if (!selectBlock) {
                long newClickTime = System.currentTimeMillis();
                if (newClickTime - lastClick < 400) {
                    afterDoubleClick();
                }
                lastClick = System.currentTimeMillis();
            }
        });
        refreshCountEvent();
        setOnLongClickListener(v -> {
            if (isItemSelected) {
                return afterLongClick();
            } else {
                return false;
            }
        });
    }

    protected void afterDoubleClick() {

    }

    protected boolean afterLongClick() {
        return false;
    }

    protected void onViewInited() {
        //重写逻辑代码，可能用于气泡拖拽之类的实现
    }

    public void setText(@StringRes int resId) {
        textView.setText(getResources().getString(resId));
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public String getText() {
        return textView.getText().toString().trim();
    }

    /**
     * 当前是否选中
     */
    public boolean isItemSelected() {
        return isItemSelected;
    }

    /**
     * 设置选中状态
     *
     * @param isItemSelected 是否选中
     */
    public void setItemSelected(boolean isItemSelected) {
        this.isItemSelected = isItemSelected;
        checkState();
    }

    private void checkState() {
        if (isItemSelected) {
            textView.setTextColor(selectedColor);
            iconView.setImageDrawable(selectedDrawable);
        } else {
            textView.setTextColor(normalColor);
            iconView.setImageDrawable(normalDrawable);
        }
        refreshCountEvent();
    }

    private void refreshCountEvent() {
        badgeView.setEnabled(isItemSelected());
    }

    /**
     * 设置正常图标
     *
     * @param normalDrawable 正常图标
     */
    public void setNormalDrawable(@NonNull Drawable normalDrawable) {
        this.normalDrawable = normalDrawable;
        checkState();
    }

    /**
     * 设置选中图标
     *
     * @param selectedDrawable 选中图标
     */
    public void setSelectedDrawable(@NonNull Drawable selectedDrawable) {
        this.selectedDrawable = selectedDrawable;
        checkState();
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        throw new IllegalStateException("不支持setSelected");
    }

    /**
     * 设置角标数目
     *
     * @param count 数目
     */
    public void setBadge(int count) {
        if (count > 0) {
            badgeView.setVisibility(VISIBLE);
            String badgeText;
            if (count > 99) {
                badgeText = "99";
            } else {
                badgeText = count + "";
            }
            badgeView.setText(badgeText);
        } else {
            badgeView.setVisibility(GONE);
        }
    }

    public Class<? extends BaseFragment> getFragmentClass() {
        return fragmentClass;
    }

    public void setFragmentClass(Class<? extends BaseFragment> fragmentClass) {
        this.fragmentClass = fragmentClass;
    }

    /**
     * 设置状态监听代理
     *
     * @param delegate 状态监听
     */
    public void setDelegate(OnTabItemStateWillChangeDelegate delegate) {
        this.delegate = delegate;
    }

    public interface OnTabItemStateWillChangeDelegate {
        boolean shouldChangeTabItemState(TabItemView tabItemView);

        void onTabItemStateChanged(TabItemView tabItemView);
    }

}
