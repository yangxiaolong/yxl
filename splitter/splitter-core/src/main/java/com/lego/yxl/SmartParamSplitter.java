package com.lego.yxl;

/**
 *
 * 智能参数拆分器，通过 support 方法对类型进行验证。 <br />
 *
 */
public interface SmartParamSplitter<P> extends ParamSplitter<P>{
    /**
     * 是否能支持特定类型
     * @param paramType 参数类型
     * @return < br/>
     * 1. true 能支持 paramType 的拆分
     * 2. false 不能支持 paramType 的拆分
     */
    boolean support(Class<P> paramType);
}
