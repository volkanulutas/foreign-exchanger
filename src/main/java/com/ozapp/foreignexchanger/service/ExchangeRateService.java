package com.ozapp.foreignexchanger.service;

import com.ozapp.foreignexchanger.data.exception.RestClientException;
import com.ozapp.foreignexchanger.data.exception.ValidationException;

public interface ExchangeRateService {
    double calculateExchangeRate(String sourceCurrency,
                                 String targetCurrency) throws ValidationException, RestClientException, IllegalAccessException;
}
