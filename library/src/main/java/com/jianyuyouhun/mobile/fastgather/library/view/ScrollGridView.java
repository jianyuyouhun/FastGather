package com.jianyuyouhun.mobile.fastgather.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 放在ScrollView中使用的GridView
 * Created by wangyu on 2017/5/3.
 */

public class ScrollGridView extends GridView {

    public ScrollGridView(Context context) {
        super(context);
    }

    public ScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
