package com.lego.yxl.command.demo.command;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
public class EventListeners {
    private List<Object> events = Lists.newArrayList();

    @EventListener
    public void onEvent(OrderEvent event){
        this.events.add(event);
    }

    public void clear(){
        this.events.clear();
    }
}
