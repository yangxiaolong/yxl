package com.lego.yxl.singlequery.jpa;

import com.lego.yxl.core.Page;
import com.lego.yxl.core.jpa.SpecificationConverterFactory;
import com.lego.yxl.core.jpa.support.BaseSpecificationQueryObjectRepository;
import com.lego.yxl.singlequery.User;
import com.lego.yxl.singlequery.UserSingleQueryService;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class JpaUserSingleQueryService
    extends BaseSpecificationQueryObjectRepository
    implements UserSingleQueryService {

    public JpaUserSingleQueryService(JpaUserRepository specificationExecutor,
                                     SpecificationConverterFactory specificationConverterFactory) {
        super(specificationExecutor, JpaUser.class, specificationConverterFactory, 0);
    }

    @Override
    public void checkFor(Class cls) {
        getSpecificationConverter().validate(cls);
    }

    @Override
    public User oneOf(Object query) {
        return (User) super.get(query);
    }

    @Override
    public List<? extends User> listOf(Object query) {
        return super.listOf(query);
    }

    @Override
    public Long countOf(Object query) {
        return super.countOf(query);
    }

    @Override
    public Page<? extends User> pageOf(Object query) {
        return super.pageOf(query);
    }
}
