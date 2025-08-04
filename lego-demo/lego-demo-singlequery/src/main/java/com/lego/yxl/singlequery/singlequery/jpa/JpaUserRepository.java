package com.lego.yxl.singlequery.singlequery.jpa;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;


public interface JpaUserRepository extends Repository<JpaUser, Long>, JpaSpecificationExecutor<JpaUser> {
}
