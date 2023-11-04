package com.ozapp.foreignexchanger.data.model.conversionlist;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversionListCriteriaModel {
    @Parameter(description = "Result target amount converted by the base currency and exchange rate.", example = "38.2")
    private String id;

    @Parameter(description = "Result target amount converted by the base currency and exchange rate.", example = "38.2")
    private Long date;

}
