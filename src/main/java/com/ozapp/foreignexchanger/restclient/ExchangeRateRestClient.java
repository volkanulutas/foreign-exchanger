package com.ozapp.foreignexchanger.restclient;

import com.ozapp.foreignexchanger.config.AppProperty;
import com.ozapp.foreignexchanger.data.dto.FixerExchangeRateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExchangeRateRestClient {
    private final RestTemplate restTemplate;

    private final AppProperty appProperty;

    @Autowired
    public ExchangeRateRestClient(RestTemplate restTemplate, AppProperty appProperty) {
        this.restTemplate = restTemplate;
        this.appProperty = appProperty;
    }

    public FixerExchangeRateDto getExchangeRates() {
        try {
/*            FixerExchangeRateDto exchangeRateDto =
                    restTemplate.getForObject(appProperty.getFixerUrl() + appProperty.getFixerAccessKey(), FixerExchangeRateDto.class); */

            FixerExchangeRateDto exchangeRateDto =
                    restTemplate.getForObject("http://data.fixer.io/api/latest?access_key=af5616beecc053123d964ceb200d27b5", FixerExchangeRateDto.class);
            return exchangeRateDto;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }
}
