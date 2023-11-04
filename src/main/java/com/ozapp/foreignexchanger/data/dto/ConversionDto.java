package com.ozapp.foreignexchanger.data.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionDto {
    @Parameter(description = "id which defines transaction uniquely conversion issue id.", example = "0930f9f2-7add-11ee-b962-0242ac120002")
    private String id;

    @Parameter(description = "Result target amount converted by the base currency and exchange rate.", example = "38.2")
    private Double targetAmount;
}
