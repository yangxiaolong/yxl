package com.lego.yxl.singlequery;

import com.lego.yxl.core.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@Validated
public interface UserSingleQueryService {

    void checkFor(Class cls);

    User oneOf(@Valid @NotNull Object query);

    List<? extends User> listOf(Object query);

    Long countOf(Object query);

    Page<? extends User> pageOf(Object query);
}
