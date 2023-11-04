package com.ozapp.foreignexchanger.data.model.conversionlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

@Data
public class ConversionListSortModel {
    @JsonProperty("column")
    @Parameter(name="column", description = "Result target amount converted by the base currency and exchange rate.", example = "38.2")

    private EnumConversionListColumn column;

    @JsonProperty("type")
    @Parameter(name="type", description = "Result target amount converted by the base currency and exchange rate.", example = "38.2")

    private EnumConversionListSortType type;
}
