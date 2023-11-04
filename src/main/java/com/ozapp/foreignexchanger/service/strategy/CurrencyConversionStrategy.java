package com.ozapp.foreignexchanger.service.strategy;

import com.ozapp.foreignexchanger.data.currencyconversion.CurrencyConversionDto;
import com.ozapp.foreignexchanger.data.dto.exchangeRate.ExchangeRateDto;
import com.ozapp.foreignexchanger.data.exception.RestClientException;
import com.ozapp.foreignexchanger.data.exception.ValidationException;

public interface CurrencyConversionStrategy {
    CurrencyConversionDto convert(double sourceAmount, String sourceCurrency, String targetCurrency) throws ValidationException;

    ExchangeRateDto calculateExchangeRate(String sourceCurrency, String targetCurrency) throws ValidationException, RestClientException;
}
