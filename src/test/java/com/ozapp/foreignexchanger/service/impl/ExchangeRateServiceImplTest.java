package com.ozapp.foreignexchanger.service.impl;

import com.ozapp.foreignexchanger.data.dto.exchangeRate.ExchangeRateDto;
import com.ozapp.foreignexchanger.data.exception.RestClientException;
import com.ozapp.foreignexchanger.data.exception.ValidationException;
import com.ozapp.foreignexchanger.service.DbSourceExchangeRateService;
import com.ozapp.foreignexchanger.service.strategy.CurrencyConversionStrategyFactory;
import com.ozapp.foreignexchanger.service.strategy.DbCurrencyConversionStrategy;
import com.ozapp.foreignexchanger.service.strategy.EnumConversionType;
import com.ozapp.foreignexchanger.service.strategy.FixerCurrencyConversionStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateServiceImplTest {
    @Mock
    private CurrencyConversionStrategyFactory currencyConversionStrategyFactory;

    @Mock
    private DbCurrencyConversionStrategy dbCurrencyConversionStrategy;

    @Mock
    private FixerCurrencyConversionStrategy fixerCurrencyConversionStrategy;

    @Mock
    private DbSourceExchangeRateService dbSourceExchangeRateService;

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    @Test
    void calculateExchangeRate() throws ValidationException, IllegalAccessException {
        Mockito.when(currencyConversionStrategyFactory.getStrategy(EnumConversionType.FIXER)).thenReturn(fixerCurrencyConversionStrategy);
        ExchangeRateDto exchangeRateDto = getMockExchangeData();
        Mockito.when(fixerCurrencyConversionStrategy.calculateExchangeRate("EUR", "USD")).thenReturn(exchangeRateDto);
        double resultExchangeRate = exchangeRateService.calculateExchangeRate("EUR", "USD");
        Assertions.assertEquals(resultExchangeRate, 1.073594);
    }

    @Test
    void calculateExchangeRate_Exception_DbSource() throws ValidationException, IllegalAccessException {
        Mockito.when(currencyConversionStrategyFactory.getStrategy(EnumConversionType.FIXER))
                .thenThrow(new RestClientException(101, "invalid_base", null));
        Mockito.when(currencyConversionStrategyFactory.getStrategy(EnumConversionType.DB)).thenReturn(dbCurrencyConversionStrategy);
        Map<String, Double> mockExchangeRateMap = getMockExchangeRateMap();
        ExchangeRateDto exchangeRateDto = getMockExchangeData();
        Mockito.when(dbCurrencyConversionStrategy.calculateExchangeRate("EUR", "USD")).thenReturn(exchangeRateDto);
        double resultExchangeRate = exchangeRateService.calculateExchangeRate("EUR", "USD");
        Assertions.assertEquals(1.073594, resultExchangeRate);
    }

    @Test()
    void validation() {
        Assertions.assertThrows(ValidationException.class, () -> exchangeRateService.calculateExchangeRate(null, null));
        Assertions.assertThrows(ValidationException.class, () -> exchangeRateService.calculateExchangeRate("EUR", ""));
        Assertions.assertThrows(ValidationException.class, () -> exchangeRateService.calculateExchangeRate("", "USD"));
        Assertions.assertThrows(ValidationException.class, () -> exchangeRateService.calculateExchangeRate(null, "USD"));
        Assertions.assertThrows(ValidationException.class, () -> exchangeRateService.calculateExchangeRate("EUR", null));
        Assertions.assertThrows(ValidationException.class, () -> exchangeRateService.calculateExchangeRate("", null));
        ValidationException ex = Assertions.assertThrows(ValidationException.class, () -> exchangeRateService.calculateExchangeRate(null, ""));
        Assertions.assertEquals("Currency parameter(s) not valid!", ex.getMessage());
    }

    private static ExchangeRateDto getMockExchangeData() {
        Map<String, Double> rateMap = getMockExchangeRateMap();
        return new ExchangeRateDto(true, System.currentTimeMillis(), "data", "EUR", rateMap, null);
    }

    private static Map<String, Double> getMockExchangeRateMap() {
        Map<String, Double> rateMap = new HashMap<>();
        rateMap.put("AED", 3.943357);
        rateMap.put("AFN", 78.216881);
        rateMap.put("AUD", 1.648666);
        rateMap.put("GBP", 0.867551);
        rateMap.put("USD", 1.073594);
        rateMap.put("TRY", 30.486973);
        return rateMap;
    }
}