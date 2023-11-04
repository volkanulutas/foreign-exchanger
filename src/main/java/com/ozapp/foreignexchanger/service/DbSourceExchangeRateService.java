package com.ozapp.foreignexchanger.service;

import java.util.Map;

public interface DbSourceExchangeRateService {
    void initializeDbSource();

    Map<String, Double> getExchangeRate(String baseCurrency);
}
