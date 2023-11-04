package com.ozapp.foreignexchanger.service.impl;

import com.ozapp.foreignexchanger.data.dto.fixer.FixerErrorDto;
import com.ozapp.foreignexchanger.data.dto.fixer.FixerExchangeRateDto;
import com.ozapp.foreignexchanger.data.exception.RestClientException;
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
    public double calculateExchangeRate(String sourceCurrency, String targetCurrency) throws ValidationException, RestClientException {
        validate(sourceCurrency, targetCurrency);
        FixerExchangeRateDto exchangeRate = exchangeRateRestClient.getExchangeRate(sourceCurrency, targetCurrency);
        if (exchangeRate.isSuccess()) {
            return exchangeRate.getRates().get(targetCurrency);
        } else {
            FixerErrorDto error = exchangeRate.getError();
            throw new RestClientException(error.getCode(), error.getType(), error.getInfo());
        }
    }

    public void validate(String currency1, String currency2) throws ValidationException {
        if (currency1 == null || currency2 == null || currency1.equals("") || currency2.equals("")) {
            throw new ValidationException("Validation ex message");
        }
    }
}
