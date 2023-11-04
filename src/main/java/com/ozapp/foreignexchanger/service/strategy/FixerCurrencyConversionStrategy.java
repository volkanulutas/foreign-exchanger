package com.ozapp.foreignexchanger.service.strategy;

import com.ozapp.foreignexchanger.data.currencyconversion.CurrencyConversionDto;
import com.ozapp.foreignexchanger.data.dto.exchangeRate.ExchangeErrorDto;
import com.ozapp.foreignexchanger.data.dto.exchangeRate.ExchangeRateDto;
import com.ozapp.foreignexchanger.data.exception.RestClientException;
import com.ozapp.foreignexchanger.data.exception.ValidationException;
import com.ozapp.foreignexchanger.restclient.FixerExchangeRestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FixerCurrencyConversionStrategy implements CurrencyConversionStrategy {
    private final FixerExchangeRestClient fixerExchangeRestClient;

    @Autowired
    public FixerCurrencyConversionStrategy(FixerExchangeRestClient fixerExchangeRestClient) {this.fixerExchangeRestClient = fixerExchangeRestClient;}

    @Override
    public CurrencyConversionDto convert(double sourceAmount, String sourceCurrency,
                                         String targetCurrency) throws ValidationException, RestClientException {
        validateCurrency(sourceAmount, sourceCurrency, targetCurrency);
        try {
            ExchangeRateDto exchangeRateDto = this.calculateExchangeRate(sourceCurrency, targetCurrency);
            Double exchangeRate = exchangeRateDto.getRates().get(targetCurrency);
            double targetAmount = exchangeRate * sourceAmount;
            return new CurrencyConversionDto(targetAmount, exchangeRate);
        } catch (RestClientException ex) {
            throw new RestClientException(ex.getCode(), ex.getType(), ex.getDetail());
        }
    }

    @Override
    public ExchangeRateDto calculateExchangeRate(String sourceCurrency, String targetCurrency) throws ValidationException, RestClientException {
        validate(sourceCurrency, targetCurrency);
        ExchangeRateDto exchangeRate = fixerExchangeRestClient.getExchangeRate(sourceCurrency, targetCurrency);
        if (exchangeRate.isSuccess()) {
            return exchangeRate;
        } else {
            ExchangeErrorDto error = exchangeRate.getError();
            throw new RestClientException(error.getCode(), error.getType(), error.getInfo());
        }
    }

    public void validate(String currency1, String currency2) throws ValidationException {
        if (currency1 == null || currency2 == null || currency1.equals("") || currency2.equals("")) {
            throw new ValidationException("Validation ex message");
        }
    }

    private void validateCurrency(Double sourceAmount, String sourceCurrency, String targetCurrency) throws ValidationException {
        if (sourceAmount == null || sourceCurrency == null || sourceCurrency.equals("") || targetCurrency == null || targetCurrency.equals("")) {
            throw new ValidationException("Error is occurred while converting the currencies.");
        }
    }
}
