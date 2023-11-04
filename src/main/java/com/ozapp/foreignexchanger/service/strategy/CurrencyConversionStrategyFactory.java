package com.ozapp.foreignexchanger.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConversionStrategyFactory {
    private final DbCurrencyConversionStrategy dbCurrencyConversionStrategy;

    private final FixerCurrencyConversionStrategy fixerCurrencyConversionStrategy;

    @Autowired
    public CurrencyConversionStrategyFactory(DbCurrencyConversionStrategy dbCurrencyConversionStrategy,
                                             FixerCurrencyConversionStrategy fixerCurrencyConversionStrategy) {
        this.dbCurrencyConversionStrategy = dbCurrencyConversionStrategy;
        this.fixerCurrencyConversionStrategy = fixerCurrencyConversionStrategy;
    }

    public CurrencyConversionStrategy getStrategy(EnumConversionType type) {
        if (EnumConversionType.DB.equals(type)) {
            return dbCurrencyConversionStrategy;
        } else if (EnumConversionType.FIXER.equals(type)) {
            return fixerCurrencyConversionStrategy;

        }
        return null;
    }
}
