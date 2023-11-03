package com.ozapp.foreignexchanger.controller;

import com.ozapp.foreignexchanger.data.exception.ValidationException;
import com.ozapp.foreignexchanger.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exchange-rate")
@CrossOrigin(origins = "*", allowedHeaders = {"*"})
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping(value = "/{currency1}/{currency2}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getExchangeRate(@PathVariable("currency1") Double currency1, @PathVariable("currency2") Double currency2) {
        try {
            double exchangeRate = exchangeRateService.calculateExchangeRate(currency1, currency2);
            return ResponseEntity.ok(exchangeRate);
        } catch (ValidationException ex) {
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getExchangeRate2() {
        try {
            double exchangeRate = exchangeRateService.calculateExchangeRate(Double.valueOf(1L), Double.valueOf(1L));
            return ResponseEntity.ok(exchangeRate);
        } catch (ValidationException ex) {
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
