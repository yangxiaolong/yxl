package com.geekhalo.like.domain;

import com.geekhalo.like.domain.user.ActionUser;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @auther yangxiaolong
 * @create 2025/10/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ActionId implements Serializable {
    private Long id;
    private ActionUser user;
}