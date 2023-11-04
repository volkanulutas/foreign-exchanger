package com.ozapp.foreignexchanger.data.model.conversionlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

@Data
public class ConversionListRequestModel {
    @JsonProperty("criteria")
    @Parameter(name = "criteria", description = "Result target amount converted by the base currency and exchange rate.", example = "38.2")
    private ConversionListCriteriaModel criteria;

    @JsonProperty("sort")
    @Parameter(name = "sort", description = "Result target amount converted by the base currency and exchange rate.", example = "38.2")
    private ConversionListSortModel sort;
}
