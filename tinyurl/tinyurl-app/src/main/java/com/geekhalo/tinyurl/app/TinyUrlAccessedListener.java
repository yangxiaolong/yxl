package com.geekhalo.tinyurl.app;

import com.geekhalo.tinyurl.domain.IncrAccessCountCommand;
import com.geekhalo.tinyurl.domain.TinyUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TinyUrlAccessedListener {
    @Autowired
    private TinyUrlCommandApplicationService commandApplicationService;

    @EventListener
    public void handleEvent(TinyUrlAccessedEvent event){
        TinyUrl tinyUrl = event.getTinyUrl();
        if (tinyUrl.needUpdateAccessCount()) {
            IncrAccessCountCommand incrAccessCountCommand = new IncrAccessCountCommand();
            incrAccessCountCommand.setId(tinyUrl.getId());
            incrAccessCountCommand.setIncrCount(1);

            this.commandApplicationService.incrAccessCount(incrAccessCountCommand);
        }
    }
}
