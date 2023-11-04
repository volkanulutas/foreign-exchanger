package com.ozapp.foreignexchanger.data.currencyconversion;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CurrencyConversionDto {
    private double targetAmount;
    private double exchangeRate;
}
