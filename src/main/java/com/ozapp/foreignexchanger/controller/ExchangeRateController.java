package com.ozapp.foreignexchanger.controller;

import com.ozapp.foreignexchanger.data.exception.RestClientException;
import com.ozapp.foreignexchanger.data.exception.ValidationException;
import com.ozapp.foreignexchanger.service.ExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exchange-rate")
@CrossOrigin(origins = "*", allowedHeaders = {"*"})
@Tag(name = "Exchange API", description = "Exchange Management APIs")
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @Operation(summary = "Exchange Rate API." ,description = "Exchange rate is listed between two currencies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exchange rate successfully listed.",
                         content = { @Content(mediaType = "application/json",
                                              schema = @Schema(implementation = Double.class))  }),
            @ApiResponse(responseCode = "400", description = "Invalid source or target currency is supported.",
                         content = @Content)})

    @GetMapping(value = "/{sourceCurrency}/{targetCurrency}")
    public ResponseEntity<?> getExchangeRate(@PathVariable("sourceCurrency") String sourceCurrency, @PathVariable("targetCurrency") String targetCurrency) {
        try {
            double exchangeRate = exchangeRateService.calculateExchangeRate(sourceCurrency, targetCurrency);
            return ResponseEntity.ok(exchangeRate);
        } catch (ValidationException ex) {
            return ResponseEntity.badRequest().build();
        } catch (RestClientException ex) {
            return new ResponseEntity<>(ex.getDetail(), HttpStatus.valueOf(ex.getCode()));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
