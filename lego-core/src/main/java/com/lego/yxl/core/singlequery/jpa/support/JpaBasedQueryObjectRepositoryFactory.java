package com.lego.yxl.core.singlequery.jpa.support;

import com.lego.yxl.core.singlequery.jpa.SpecificationConverterFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.util.Assert;

import java.io.Serializable;

public class JpaBasedQueryObjectRepositoryFactory extends JpaRepositoryFactory {
    private final SpecificationConverterFactory specificationConverterFactory;
    private final Integer startPageIndex;
    public JpaBasedQueryObjectRepositoryFactory(EntityManager entityManager,
                                                SpecificationConverterFactory specificationConverterFactory,
                                                Integer startPageIndex) {
        super(entityManager);
        this.specificationConverterFactory = specificationConverterFactory;
        this.startPageIndex = startPageIndex;
    }

    @Override
    protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
        JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
        Object repository = getTargetRepositoryViaReflection(information, entityInformation, entityManager, specificationConverterFactory, this.startPageIndex);

        Assert.isInstanceOf(JpaBasedQueryObjectRepository.class, repository);
        ((JpaBasedQueryObjectRepository) repository).init();
        return (JpaRepositoryImplementation<?, ?>) repository;
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return JpaBasedQueryObjectRepository.class;
    }
}
