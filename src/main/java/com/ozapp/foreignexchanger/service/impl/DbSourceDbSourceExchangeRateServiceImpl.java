package com.ozapp.foreignexchanger.service.impl;

import com.ozapp.foreignexchanger.data.dto.exchangeRate.ExchangeRateDto;
import com.ozapp.foreignexchanger.data.entity.DbSourceExchangeRateEntity;
import com.ozapp.foreignexchanger.repository.DbSourceExchangeRateRepository;
import com.ozapp.foreignexchanger.restclient.FixerExchangeRestClient;
import com.ozapp.foreignexchanger.service.DbSourceExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class DbSourceDbSourceExchangeRateServiceImpl implements DbSourceExchangeRateService {
    private final DbSourceExchangeRateRepository dbSourceExchangeRateRepository;

    private final FixerExchangeRestClient restClient;

    @Autowired
    public DbSourceDbSourceExchangeRateServiceImpl(DbSourceExchangeRateRepository dbSourceExchangeRateRepository,
                                                   FixerExchangeRestClient restClient) {
        this.dbSourceExchangeRateRepository = dbSourceExchangeRateRepository;
        this.restClient = restClient;

    }

    @Override
    public void initializeDbSource() {
        // Initial DB data provided by Fixer again. It can be other datasource such as XML, other API.
        ExchangeRateDto exchangeInformation = restClient.getAllExchangeRate("EUR");
        DbSourceExchangeRateEntity entity = new DbSourceExchangeRateEntity();
        entity.setBase(exchangeInformation.getBase());
        entity.setJsonDataFromMap(exchangeInformation.getRates());
        DbSourceExchangeRateEntity save = dbSourceExchangeRateRepository.save(entity);
        if (save != null) {
            log.info("Db Source of exchange rates are initialized.");
        }
    }

    @Override
    public Map<String, Double> getExchangeRate(String baseCurrency) {
        Optional<DbSourceExchangeRateEntity> opt = dbSourceExchangeRateRepository.findById(baseCurrency);
        if (opt.isPresent()) {
            return opt.get().getJsonDataAsMap();
        }
        return Collections.emptyMap();
    }
}
