package com.ozapp.foreignexchanger.service;

import com.ozapp.foreignexchanger.data.exception.ValidationException;

public interface ExchangeRateService {
    double calculateExchangeRate(Double currency1, Double currency2) throws ValidationException;
}
