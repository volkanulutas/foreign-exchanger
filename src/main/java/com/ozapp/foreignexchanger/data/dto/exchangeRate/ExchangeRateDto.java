package com.ozapp.foreignexchanger.data.dto.exchangeRate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExchangeRateDto {
    private boolean success;

    private long timestamp;

    private String date;

    private String base;

    private Map<String, Double> rates = new HashMap<>();

    private ExchangeErrorDto error;
}
