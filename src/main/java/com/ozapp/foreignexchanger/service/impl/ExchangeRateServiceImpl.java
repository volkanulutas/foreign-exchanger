package com.ozapp.foreignexchanger.service.impl;

import com.ozapp.foreignexchanger.data.exception.ValidationException;
import com.ozapp.foreignexchanger.service.ExchangeRateService;

public class ExchangeRateServiceImpl implements ExchangeRateService {
    @Override
    public double calculateExchangeRate(Double currency1, Double currency2)  throws ValidationException{
        validate(currency1, currency2);
        return 0;
    }

    public void validate(Double currency1, Double currency2) throws ValidationException {
        throw new ValidationException("Validation ex message");
    }
}
