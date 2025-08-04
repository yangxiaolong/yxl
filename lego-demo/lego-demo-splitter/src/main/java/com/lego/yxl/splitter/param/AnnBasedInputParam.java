package com.lego.yxl.splitter.param;

import com.lego.yxl.core.splitter.annotation.SplitParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnnBasedInputParam {

    @SplitParam
    private List<Long> numbers;

    private  Long other;

}
