//package com.lego.yxl.singlequery.jpa;
//
//import com.lego.yxl.core.Page;
//import com.lego.yxl.singlequery.User;
//import com.lego.yxl.singlequery.UserSingleQueryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//
////@Service
//public class JpaUserSingleQueryServiceV2 implements UserSingleQueryService {
////    @Autowired
//    private JpaUserRepositoryV2 jpaUserRepository;
//
//    @Override
//    public void checkFor(Class cls) {
//        jpaUserRepository.checkForQueryObject(cls);
//    }
//
//    @Override
//    public User oneOf(Object query) {
//        return jpaUserRepository.get(query);
//    }
//
//    @Override
//    public List<? extends User> listOf(Object query) {
//        return jpaUserRepository.listOf(query);
//    }
//
//    @Override
//    public Long countOf(Object query) {
//        return jpaUserRepository.countOf(query);
//    }
//
//    @Override
//    public Page<? extends User> pageOf(Object query) {
//        return this.jpaUserRepository.pageOf(query);
//    }
//}
