package com.lego.yxl.core.singlequery.jpa.support;

import com.lego.yxl.core.singlequery.jpa.SpecificationConverterFactory;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.query.JpaQueryMethodFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class JpaBasedQueryObjectRepositoryFactoryBean<T extends Repository<S, ID>, S, ID>
        extends JpaRepositoryFactoryBean<T, S, ID> {
    @Autowired
    private SpecificationConverterFactory specificationConverterFactory;

    @Value("${lego.query.startPageIndex:0}")
    private Integer startPageIndex;

    public JpaBasedQueryObjectRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        JpaRepositoryFactory jpaRepositoryFactory =
                new JpaBasedQueryObjectRepositoryFactory(entityManager, specificationConverterFactory, startPageIndex);
        jpaRepositoryFactory.setEntityPathResolver(readFromField("entityPathResolver"));
        jpaRepositoryFactory.setEscapeCharacter(readFromField("escapeCharacter"));
        JpaQueryMethodFactory queryMethodFactory = readFromField("queryMethodFactory");
        if (queryMethodFactory != null) {
            jpaRepositoryFactory.setQueryMethodFactory(queryMethodFactory);
        }
        return jpaRepositoryFactory;
    }

    private <T> T readFromField(String fieldName) {
        try {
            return (T) FieldUtils.readField(this, fieldName, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
