package com.ozapp.foreignexchanger.service.impl;

import com.ozapp.foreignexchanger.data.dto.FixerExchangeRateDto;
import com.ozapp.foreignexchanger.data.exception.ValidationException;
import com.ozapp.foreignexchanger.restclient.ExchangeRateRestClient;
import com.ozapp.foreignexchanger.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRateRestClient exchangeRateRestClient;

    @Autowired
    public ExchangeRateServiceImpl(ExchangeRateRestClient exchangeRateRestClient) {this.exchangeRateRestClient = exchangeRateRestClient;}

    @Override
    public double calculateExchangeRate(Double currency1, Double currency2) throws ValidationException {
        FixerExchangeRateDto exchangeRates = exchangeRateRestClient.getExchangeRates();
        validate(currency1, currency2);
        return 0;
    }

    public void validate(Double currency1, Double currency2) throws ValidationException {
        throw new ValidationException("Validation ex message");
    }
}
