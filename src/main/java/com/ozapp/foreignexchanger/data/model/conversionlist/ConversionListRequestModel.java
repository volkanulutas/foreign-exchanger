package com.ozapp.foreignexchanger.data.model.conversionlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ConversionListRequestModel {
    @JsonProperty("detectionCriteria")
    private ConversionListCriteriaModel criteria;

    @JsonProperty("detectionSort")
    private ConversionListSortModel sort;
}
