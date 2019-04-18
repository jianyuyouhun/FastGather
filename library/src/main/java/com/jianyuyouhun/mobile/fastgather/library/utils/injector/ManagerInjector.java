package com.jianyuyouhun.mobile.fastgather.library.utils.injector;


import com.jianyuyouhun.mobile.fastgather.library.app.BaseManager;
import com.jianyuyouhun.mobile.fastgather.library.app.JApp;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 注解manager
 * Created by wangyu on 2017/6/30.
 */

public class ManagerInjector {

    public static void injectManager(Object object) {
        Class<?> aClass = object.getClass();

        while (aClass != BaseManager.class && aClass != Object.class) {
            Field[] declaredFields = aClass.getDeclaredFields();
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    int modifiers = field.getModifiers();
                    if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) {
                        // 忽略掉static 和 final 修饰的变量
                        continue;
                    }

                    if (!field.isAnnotationPresent(Manager.class)) {
                        continue;
                    }

                    Class<?> type = field.getType();
                    if (!BaseManager.class.isAssignableFrom(type)) {
                        throw new RuntimeException("@Manager 只能在BaseManager子类中使用");
                    }

                    @SuppressWarnings("unchecked")
                    BaseManager baseManager = JApp.getInstance().getManager((Class<? extends BaseManager>) type);

                    if (baseManager == null) {
                        throw new RuntimeException(type.getSimpleName() + " Manager还未初始化！");
                    }

                    if (!field.isAccessible())
                        field.setAccessible(true);

                    try {
                        field.set(object, baseManager);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            aClass = aClass.getSuperclass();
        }
    }
}
