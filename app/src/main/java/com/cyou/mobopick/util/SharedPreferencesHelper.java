package com.cyou.mobopick.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.cyou.mobopick.MyApplication;
import com.cyou.mobopick.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by chengfei on 14-10-21.
 */
//TODO add multiple builder support
public class SharedPreferencesHelper {

    private Context context;
    private SharedPreferences sharedPreferences;
    private Builder builder;
    private Class dataType;

    private SharedPreferencesHelper() {
        context = MyApplication.getInstance();
        builder = new Builder();
        withModule(R.string.module_default);
    }

    public static SharedPreferencesHelper getInstance() {
        return  new SharedPreferencesHelper();
    }

    public SharedPreferencesHelper withModule(int module) {
        builder.setModule(context.getString(module));
        return this;
    }

    public SharedPreferencesHelper withKey(int key) {
        builder.setKey(context.getString(key));
        return this;
    }

    public <T> SharedPreferencesHelper setData(Class<T> clazz, T data) {
        builder.setData(data);
        dataType = clazz;
        return this;
    }

    public<T> T get(Class<T> c, T defaultValue){
        sharedPreferences = context.getSharedPreferences(builder.module, Context.MODE_PRIVATE);
        StringBuilder methodBuilder = new StringBuilder("get");
        appendMethodName(methodBuilder, defaultValue.getClass());
        String methodName = methodBuilder.toString();
        try {
            Method method = sharedPreferences.getClass().getMethod(methodName, String.class, c);
            T result = (T) method.invoke(sharedPreferences, builder.key, defaultValue);
            return result;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public void commit() {
        sharedPreferences = context.getSharedPreferences(builder.module, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        StringBuilder methodBuilder = new StringBuilder("put");
        Object data = builder.data;
        appendMethodName(methodBuilder, data.getClass());

        String methodName = methodBuilder.toString();
        try {
            Method method = editor.getClass().getMethod(methodName,String.class, dataType);
            try {
                method.invoke(editor, builder.key, data);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    private void appendMethodName(StringBuilder methodBuilder, Class data) {
        if (data.isInstance(new Integer(0))) {

            methodBuilder.append("Int");
        } else if(data.isInstance("")) {
            methodBuilder.append("String");

        } else if(data.isInstance(true)) {

            methodBuilder.append("Boolean");

        } else if(data.isInstance(0.1f)) {

            methodBuilder.append("Float");

        } else if (data.isInstance(1l)) {
            methodBuilder.append("Long");
        }
    }

    private class Builder {
        private String module;
        private String key;
        private Object data;

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setData(Object data) {
            this.data = data;
            return this;
        }
    }

}
