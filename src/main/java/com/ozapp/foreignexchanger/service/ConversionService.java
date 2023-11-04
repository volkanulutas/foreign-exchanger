package com.ozapp.foreignexchanger.service;

import com.ozapp.foreignexchanger.data.dto.ConversionDto;
import com.ozapp.foreignexchanger.data.exception.ValidationException;
import com.ozapp.foreignexchanger.data.model.conversionlist.ConversionListRequestModel;
import org.springframework.data.domain.Page;

public interface ConversionService {
    Page<ConversionDto> getConvertedCurrencyList(ConversionListRequestModel request, int page, int size) throws ValidationException;

    ConversionDto convertCurrency(Double sourceAmount, String sourceCurrency, String targetCurrency) throws ValidationException;
}
