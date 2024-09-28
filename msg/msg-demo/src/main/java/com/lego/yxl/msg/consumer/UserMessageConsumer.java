package com.lego.yxl.msg.consumer;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserMessageConsumer{
    private final Map<Long, List<UserEvents.UserEvent>> events = Maps.newConcurrentMap();

    public void clean(){
        this.events.clear();
    }

    public List<UserEvents.UserEvent> getUserEvents(Long userId){
        return this.events.get(userId);
    }

    public void handle(UserEvents.UserCreatedEvent userCreatedEvent){
        List<UserEvents.UserEvent> userEvents = this.events.computeIfAbsent(userCreatedEvent.getUserId(), userId -> new ArrayList<>());
        userEvents.add(userCreatedEvent);
    }

    public void handle(UserEvents.UserEnableEvent userEnableEvent){
        List<UserEvents.UserEvent> userEvents = this.events.computeIfAbsent(userEnableEvent.getUserId(), userId -> new ArrayList<>());
        userEvents.add(userEnableEvent);
    }

    public void handle(UserEvents.UserDisableEvent userDisableEvent){
        List<UserEvents.UserEvent> userEvents = this.events.computeIfAbsent(userDisableEvent.getUserId(), userId -> new ArrayList<>());
        userEvents.add(userDisableEvent);
    }

    public void handle(UserEvents.UserDeletedEvent userDeletedEvent){
        List<UserEvents.UserEvent> userEvents = this.events.computeIfAbsent(userDeletedEvent.getUserId(), userId -> new ArrayList<>());
        userEvents.add(userDeletedEvent);
    }
}
