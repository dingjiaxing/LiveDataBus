package com.jackting.lib.livedatabus;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

/**
 * 总线的管理类，用map来存储多个LivaData数据
 */
public class LiveDataBusBeta {
    private  Map<String, MutableLiveData<Object>> bus;

    private LiveDataBusBeta(){
        bus = new HashMap<>();
    }

    private static class SingleHolder{
        private static final LiveDataBusBeta DATA_BUS = new LiveDataBusBeta();
    }

    public static LiveDataBusBeta get(){
        return SingleHolder.DATA_BUS;
    }

    public <T> MutableLiveData<T> with(String target,Class<T> type){
        if(!bus.containsKey(target)){
            bus.put(target,new MutableLiveData<Object>());
        }
        return (MutableLiveData<T>)bus.get(target);
    }

    public MutableLiveData<Object> with(String target){
        return with(target,Object.class);
    }

}
