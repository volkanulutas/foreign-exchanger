package com.ozapp.foreignexchanger.service.strategy;

import com.ozapp.foreignexchanger.data.currencyconversion.CurrencyConversionDto;
import com.ozapp.foreignexchanger.data.dto.exchangeRate.ExchangeRateDto;
import com.ozapp.foreignexchanger.data.exception.RestClientException;
import com.ozapp.foreignexchanger.data.exception.ValidationException;
import com.ozapp.foreignexchanger.service.DbSourceExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class DbCurrencyConversionStrategy implements CurrencyConversionStrategy {
    private final DbSourceExchangeRateService dbSourceExchangeRateService;

    public DbCurrencyConversionStrategy(DbSourceExchangeRateService dbSourceExchangeRateService) {
        this.dbSourceExchangeRateService = dbSourceExchangeRateService;
    }

    @Override
    public CurrencyConversionDto convert(double sourceAmount, String sourceCurrency, String targetCurrency) throws ValidationException {
        validate(sourceCurrency, targetCurrency);
        ExchangeRateDto exchangeRateDto = this.calculateExchangeRate(sourceCurrency, targetCurrency);
        Double exchangeRate = exchangeRateDto.getRates().get(targetCurrency);
        double targetAmount = exchangeRate * sourceAmount;
        return new CurrencyConversionDto(targetAmount, exchangeRate);
    }

    @Override
    public ExchangeRateDto calculateExchangeRate(String sourceCurrency, String targetCurrency) throws ValidationException, RestClientException {
        Map<String, Double> exchangeRateMap = dbSourceExchangeRateService.getExchangeRate(sourceCurrency);
        return new ExchangeRateDto(true, -1, "-1", sourceCurrency, exchangeRateMap, null);
    }

    public void validate(String currency1, String currency2) throws ValidationException {
        if (currency1 == null || currency2 == null || currency1.equals("") || currency2.equals("")) {
            throw new ValidationException("Validation ex message");
        }
    }
}
