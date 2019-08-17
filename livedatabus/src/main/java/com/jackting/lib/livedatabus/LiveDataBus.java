package com.jackting.lib.livedatabus;

import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 总线的管理类，用map来存储多个LivaData数据
 */
public class LiveDataBus {
    private  Map<String, MyMutableLiveData<Object>> bus;

    private LiveDataBus(){
        bus = new HashMap<>();
    }

    private static class SingleHolder{
        private static final LiveDataBus DATA_BUS = new LiveDataBus();
    }

    public static LiveDataBus get(){
        return SingleHolder.DATA_BUS;
    }

    public <T> MyMutableLiveData<T> with(String key,Class<T> type){
        if(!bus.containsKey(key)){
            bus.put(key,new MyMutableLiveData<Object>());
        }
        return (MyMutableLiveData<T>)bus.get(key);
    }

    public MyMutableLiveData<Object> with(String key){
        return with(key,Object.class);
    }


    public <T> void post(String key,T t){
        if(Looper.getMainLooper() == Looper.myLooper()){
            //主线程
            with(key).setValue(t);
        }else {
            //子线程
            with(key).postValue(t);
        }
    }

    public static class MyMutableLiveData<T> extends MutableLiveData<T>{
        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
            super.observe(owner, observer);
            try {
                //该hook方法，是为了解决 以下问题
                // 订阅者可以收到订阅前发送者发送的消息，不应该收到的
                hook(observer);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        private void hook(Observer<? super T> observer) throws Exception {
            Class<LiveData> classLiveData = LiveData.class;
            Field fieldObservers = classLiveData.getDeclaredField("mObservers");
            fieldObservers.setAccessible(true);
            Object mObservers = fieldObservers.get(this);
            Class<?> classObservers = mObservers.getClass();

            Method methodGet = classObservers.getDeclaredMethod("get",Object.class);
            methodGet.setAccessible(true);
            Object objectWrapperEntry = methodGet.invoke(mObservers,observer);
            Object objectWrapper = ((Map.Entry)objectWrapperEntry).getValue();
            Class<?> classObserverWrapper = objectWrapper.getClass().getSuperclass();

            Field fieldLastVersion = classObserverWrapper.getDeclaredField("mLastVersion");
            fieldLastVersion.setAccessible(true);
            Field fieldVersion = classLiveData.getDeclaredField("mVersion");
            fieldVersion.setAccessible(true);
            Object objectVersion = fieldVersion.get(this);
            fieldLastVersion.set(objectWrapper,objectVersion);

        }
    }













}
