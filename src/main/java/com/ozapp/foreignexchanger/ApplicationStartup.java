package com.ozapp.foreignexchanger;

import com.ozapp.foreignexchanger.data.dto.ConversionDto;
import com.ozapp.foreignexchanger.data.exception.ValidationException;
import com.ozapp.foreignexchanger.service.ConversionService;
import com.ozapp.foreignexchanger.service.DbSourceExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationStartup {
    private final ConversionService conversionService;

    private final DbSourceExchangeRateService dbSourceExchangeRateService;

    @Autowired
    public ApplicationStartup(ConversionService conversionService, DbSourceExchangeRateService dbSourceExchangeRateService) {
        this.conversionService = conversionService;
        this.dbSourceExchangeRateService = dbSourceExchangeRateService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void afterStartup() throws ValidationException {
        // Db source initialization.
        dbSourceExchangeRateService.initializeDbSource();
        // For testing data, ease the usage.
        for (int i = 0; i < 1; i++) {
            double value = i + 1;
            ConversionDto conversionDto = conversionService.convertCurrency(value, "EUR", "USD");
            System.err.println("Created test conversion data, transaction Id: " + conversionDto.getId());
        }
    }
}
