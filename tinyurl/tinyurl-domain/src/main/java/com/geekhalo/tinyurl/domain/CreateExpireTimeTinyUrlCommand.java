package com.geekhalo.tinyurl.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class CreateExpireTimeTinyUrlCommand extends AbstractCreateTinyUrlCommand{
    private Date beginTime;

    @NotNull
    private Date expireTime;

    public Date parseBeginTime(){
        return beginTime == null ? new Date() : beginTime;
    }
}
