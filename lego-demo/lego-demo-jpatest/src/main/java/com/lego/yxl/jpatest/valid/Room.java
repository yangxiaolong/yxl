package com.lego.yxl.jpatest.valid;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;


/**
 * @auther yangxiaolong
 * @create 2024/12/29
 */
@ValidStudentCount
public class Room {

    @NotNull
    public String name;

    @AssertTrue
    public boolean finished;

    private Collection<Object> studentNames;
    private int maxStuNum;

    public Collection<Object> getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(Collection<Object> studentNames) {
        this.studentNames = studentNames;
    }

    public int getMaxStuNum() {
        return maxStuNum;
    }

    public void setMaxStuNum(int maxStuNum) {
        this.maxStuNum = maxStuNum;
    }
}