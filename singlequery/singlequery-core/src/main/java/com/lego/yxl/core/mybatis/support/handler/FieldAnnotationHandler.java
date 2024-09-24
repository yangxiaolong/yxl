package com.lego.yxl.core.mybatis.support.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


public interface FieldAnnotationHandler<A extends Annotation> {
    /**
     * 是否能支持 A 注解
     * @param a
     * @return
     */
    boolean support(A a);

    /**
     * 读取数据，并添加 Criteria 条件
     * @param criteria criteria 对象
     * @param a 注解
     * @param value 字段值
     * @throws Exception
     */
    void addCriteria(Object criteria, A a, Object value) throws Exception;

    /**
     * 获取 CriteriaMethod，用于判断配置是否生效
     * @param criteriaCls criteria类型信息
     * @param a 注解
     * @param valueCls 字段值类型
     * @return
     * @throws Exception
     */
    Method getCriteriaMethod(Class criteriaCls, A a, Class valueCls) throws Exception;
}
