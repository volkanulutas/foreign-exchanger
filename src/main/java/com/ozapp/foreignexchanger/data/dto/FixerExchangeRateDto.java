package com.ozapp.foreignexchanger.data.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class FixerExchangeRateDto {

    private boolean success;

    private long timestamp;

    private String date;

    private String base;

    private Map<String, Double> rates = new HashMap<>();
}
