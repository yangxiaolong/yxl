package com.lego.yxl.support;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Date;

@Data
@MappedSuperclass
@Setter(AccessLevel.PRIVATE)
public abstract class AbstractEntity {
    @Version
    private Integer vsn;

    @Column(name = "create_time")
    private Date createAt;

    @Column(name = "update_time")
    private Date updateAt;

    @Column(name = "delete_time")
    private Date deleteAt;

    @PrePersist
    public void prePersist(){
        if (createAt == null){
            setCreateAt(new Date());
        }
    }

    @PreUpdate
    protected void preUpdate(){
        setUpdateAt(new Date());
    }

    @PreRemove
    protected void preRemove(){
        setDeleteAt(new Date());
    }
}
