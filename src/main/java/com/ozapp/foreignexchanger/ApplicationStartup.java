package com.ozapp.foreignexchanger;

import com.ozapp.foreignexchanger.data.dto.ConversionDto;
import com.ozapp.foreignexchanger.data.exception.ValidationException;
import com.ozapp.foreignexchanger.service.ConversionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationStartup {
    @Autowired
    private final ConversionService conversionService;

    public ApplicationStartup(ConversionService conversionService) {this.conversionService = conversionService;}

    @EventListener(ApplicationReadyEvent.class)
    public void afterStartup() throws ValidationException {
        for (int i = 0; i < 1; i++) {
            double value = i + 1;
            ConversionDto conversionDto = getConversionDto(value);
            System.err.println(conversionDto.getId());
        }
    }

    private ConversionDto getConversionDto(double value) throws ValidationException {
        return conversionService.convertCurrency(value, "EUR", "USD");
    }
}
