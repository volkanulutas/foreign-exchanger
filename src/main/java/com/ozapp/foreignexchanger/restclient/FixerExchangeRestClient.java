package com.ozapp.foreignexchanger.restclient;

import com.ozapp.foreignexchanger.config.AppProperty;
import com.ozapp.foreignexchanger.data.dto.exchangeRate.ExchangeRateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class FixerExchangeRestClient {
    private final RestTemplate restTemplate;

    private final AppProperty appProperty;

    @Autowired
    public FixerExchangeRestClient(RestTemplate restTemplate, AppProperty appProperty) {
        this.restTemplate = restTemplate;
        this.appProperty = appProperty;
    }

    public ExchangeRateDto getExchangeRate(String baseCurrency, String targetCurrency) throws RestClientException {
        try {
            return restTemplate.getForObject(
                    appProperty.getFixerUrl() + appProperty.getFixerAccessKey() + "&base=" + baseCurrency + "&symbols=" + targetCurrency,
                    ExchangeRateDto.class);
        } catch (Exception ex) {
            throw new RestClientException("Error is occurred while communicating with Fixer Api. Detail: ", ex);
        }
    }

    public ExchangeRateDto getAllExchangeRate(String baseCurrency) throws RestClientException {
        try {
            return restTemplate.getForObject(appProperty.getFixerUrl() + appProperty.getFixerAccessKey(), ExchangeRateDto.class);

        } catch (Exception ex) {
            throw new RestClientException("Error is occurred while communicating with Fixer Api. Detail: ", ex);
        }
    }
}
