package com.lego.yxl;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class BaseIdempotentService {
    private Map<String, Long> data = Maps.newHashMap();

    public void clean(){
        this.data.clear();
    }

    public Long getValue(String key){
        return this.data.get(key);
    }

    protected Long put(String key, Long data){
         this.data.put(key, data);
         return data;
    }

    protected Long putException(String key, Long data){
        this.data.put(key, data);
        throw new IdempotentTestException();
    }

    protected Long putForWait(String key, Long data){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return put(key, data);
    }


    public abstract Long putForResult(String key, Long data);

    public abstract Long putForError(String key, Long data);

    public abstract Long putExceptionForResult(String key, Long data);

    public abstract Long putExceptionForError(String key, Long data);

    public abstract Long putWaitForResult(String key, Long data);

    public abstract Long putWaitForError(String key, Long data);
}
