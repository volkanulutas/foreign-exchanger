package com.ozapp.foreignexchanger.data.model.conversionlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ConversionListSortModel {
    @JsonProperty("column")
    private EnumDetectionColumn column;

    @JsonProperty("type")
    private EnumDetectionSortType type;
}
