package com.jianyuyouhun.mobile.fastgather.library.view.dialog;

public interface ProgressAction {
    void setMessage(CharSequence message);
    boolean isShowing();
    void show();
    void dismiss();
}
