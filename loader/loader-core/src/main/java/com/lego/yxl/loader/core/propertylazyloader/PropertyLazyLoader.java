package com.lego.yxl.loader.core.propertylazyloader;

import lombok.Getter;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Field;

/**

 */
public class PropertyLazyLoader {
    @Getter
    private final Field field;
    private final StandardEvaluationContext evaluationContext;
    private final Expression expression;

    public PropertyLazyLoader(Field field, BeanResolver beanResolver, String loadEl){
        this.field = field;
        ExpressionParser parser = new SpelExpressionParser();
        TemplateParserContext templateParserContext = new TemplateParserContext();
        this.expression = parser.parseExpression(loadEl, templateParserContext);
        this.evaluationContext = new StandardEvaluationContext();
        this.evaluationContext.setBeanResolver(beanResolver);
    }
    public Object loadData(Object o) {
        return expression.getValue(evaluationContext, o);
    }
}
