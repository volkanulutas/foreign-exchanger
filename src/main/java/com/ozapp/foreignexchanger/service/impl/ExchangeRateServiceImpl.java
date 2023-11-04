package com.ozapp.foreignexchanger.service.impl;

import com.ozapp.foreignexchanger.data.dto.exchangeRate.ExchangeErrorDto;
import com.ozapp.foreignexchanger.data.dto.exchangeRate.ExchangeRateDto;
import com.ozapp.foreignexchanger.data.exception.RestClientException;
import com.ozapp.foreignexchanger.data.exception.ValidationException;
import com.ozapp.foreignexchanger.service.ExchangeRateService;
import com.ozapp.foreignexchanger.service.strategy.CurrencyConversionStrategy;
import com.ozapp.foreignexchanger.service.strategy.CurrencyConversionStrategyFactory;
import com.ozapp.foreignexchanger.service.strategy.EnumConversionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final CurrencyConversionStrategyFactory currencyConversionStrategyFactory;

    @Autowired
    public ExchangeRateServiceImpl(CurrencyConversionStrategyFactory currencyConversionStrategyFactory) {
        this.currencyConversionStrategyFactory = currencyConversionStrategyFactory;
    }

    @Override
    public double calculateExchangeRate(String sourceCurrency,
                                        String targetCurrency) throws ValidationException, RestClientException, IllegalAccessException {
        validate(sourceCurrency, targetCurrency);
        CurrencyConversionStrategy strategy ;
        ExchangeRateDto exchangeRate;
        try {
            strategy = currencyConversionStrategyFactory.getStrategy(EnumConversionType.FIXER);
            if (strategy == null) {
                throw new RuntimeException("Conversion service is not available.");
            }
            exchangeRate = strategy.calculateExchangeRate(sourceCurrency, targetCurrency);
        } catch (RestClientException ex) { // If Fixer api fails, create db strategy.
            strategy = currencyConversionStrategyFactory.getStrategy(EnumConversionType.DB);
            exchangeRate = strategy.calculateExchangeRate(sourceCurrency, targetCurrency);
        }
        if (exchangeRate.isSuccess()) {
            return exchangeRate.getRates().get(targetCurrency);
        } else {
            ExchangeErrorDto error = exchangeRate.getError();
            throw new RestClientException(error.getCode(), error.getType(), error.getInfo());
        }
    }

    public void validate(String currency1, String currency2) throws ValidationException {
        if (currency1 == null || currency2 == null || currency1.equals("") || currency2.equals("")) {
            throw new ValidationException("Currency parameter(s) not valid!");
        }
    }
}
