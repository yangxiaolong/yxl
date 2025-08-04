package com.lego.yxl.core.singlequery.jpa.support;

import com.lego.yxl.core.singlequery.Page;
import com.lego.yxl.core.singlequery.jpa.SpecificationConverterFactory;
import com.lego.yxl.core.singlequery.jpa.SpecificationQueryObjectRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;

public class JpaBasedQueryObjectRepository<E, ID>
        extends SimpleJpaRepository<E, ID>
        implements SpecificationQueryObjectRepository<E> {

    private final BaseSpecificationQueryObjectRepository<E> specificationQueryObjectRepository;

    public JpaBasedQueryObjectRepository(JpaEntityInformation<E, ?> entityInformation,
                                         EntityManager entityManager,
                                         SpecificationConverterFactory specificationConverterFactory,
                                         Integer startPageIndex) {
        super(entityInformation, entityManager);
        this.specificationQueryObjectRepository =
                new BaseSpecificationQueryObjectRepository(this,
                        entityInformation.getJavaType(),
                        specificationConverterFactory,
                        startPageIndex);
    }

    public JpaBasedQueryObjectRepository(Class<E> domainClass, EntityManager em, SpecificationConverterFactory specificationConverterFactory,
                                         Integer startPageIndex) {
        super(domainClass, em);
        this.specificationQueryObjectRepository = new BaseSpecificationQueryObjectRepository(this,
                domainClass,
                specificationConverterFactory,
                startPageIndex);
    }

    @Override
    public void checkForQueryObject(Class cls) {
        this.specificationQueryObjectRepository.checkForQueryObject(cls);
    }

    @Override
    public <Q> List<E> listOf(Q query) {
        return this.specificationQueryObjectRepository.listOf(query);
    }

    @Override
    public <Q> Long countOf(Q query) {
        return this.specificationQueryObjectRepository.countOf(query);
    }

    @Override
    public <Q> E get(Q query) {
        return this.specificationQueryObjectRepository.get(query);
    }

    @Override
    public <Q> Page<E> pageOf(Q query) {
        return this.specificationQueryObjectRepository.pageOf(query);
    }

    public void init() {
        this.specificationQueryObjectRepository.init();
    }
}
