package com.ozapp.foreignexchanger.service.impl;

import com.ozapp.foreignexchanger.converter.ConversionConverter;
import com.ozapp.foreignexchanger.data.currencyconversion.CurrencyConversionDto;
import com.ozapp.foreignexchanger.data.dto.ConversionDto;
import com.ozapp.foreignexchanger.data.entity.ConversionEntity;
import com.ozapp.foreignexchanger.data.exception.RestClientException;
import com.ozapp.foreignexchanger.data.exception.ValidationException;
import com.ozapp.foreignexchanger.data.model.conversionlist.ConversionListCriteriaModel;
import com.ozapp.foreignexchanger.data.model.conversionlist.ConversionListRequestModel;
import com.ozapp.foreignexchanger.data.model.conversionlist.ConversionListSortModel;
import com.ozapp.foreignexchanger.data.model.conversionlist.EnumConversionListColumn;
import com.ozapp.foreignexchanger.repository.ConversionRepository;
import com.ozapp.foreignexchanger.service.ConversionService;
import com.ozapp.foreignexchanger.service.impl.conversionlist.search.GenericConversionListSpecification;
import com.ozapp.foreignexchanger.service.impl.conversionlist.search.SearchCriteria;
import com.ozapp.foreignexchanger.service.impl.conversionlist.search.SearchOperation;
import com.ozapp.foreignexchanger.service.strategy.CurrencyConversionStrategy;
import com.ozapp.foreignexchanger.service.strategy.CurrencyConversionStrategyFactory;
import com.ozapp.foreignexchanger.service.strategy.EnumConversionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConversionServiceImpl implements ConversionService {
    private final ConversionRepository conversionRepository;

    private final CurrencyConversionStrategyFactory currencyConversionStrategyFactory;

    @Autowired
    public ConversionServiceImpl(ConversionRepository conversionRepository, CurrencyConversionStrategyFactory currencyConversionStrategyFactory) {
        this.conversionRepository = conversionRepository;
        this.currencyConversionStrategyFactory = currencyConversionStrategyFactory;
    }

    @Override
    @Transactional
    public Page<ConversionDto> getConvertedCurrencyList(ConversionListRequestModel request, int page, int size) throws ValidationException {
        validate(request);
        GenericConversionListSpecification specification = handleCriteriaList(request);
        Page<ConversionEntity> allPage = conversionRepository.findAll(specification, PageRequest.of(page, size));
        List<ConversionDto> targetList = allPage.stream().map(e -> ConversionConverter.toDto(e)).collect(Collectors.toList());
        return new PageImpl<>(targetList, allPage.getPageable(), allPage.getTotalElements());

    }

    @Override
    public ConversionDto convertCurrency(Double sourceAmount, String sourceCurrency, String targetCurrency) throws ValidationException {
        validateCurrency(sourceAmount, sourceCurrency, targetCurrency);
        CurrencyConversionStrategy strategy;
        CurrencyConversionDto conversionDto;
        // strategy creation.
        try {
            strategy = currencyConversionStrategyFactory.getStrategy(EnumConversionType.FIXER);
            if (strategy == null) {
                throw new RuntimeException("Conversion service is not available.");
            }
            conversionDto = strategy.convert(sourceAmount, sourceCurrency, targetCurrency);
        } catch (RestClientException ex) { // If Fixer api fails, create db strategy.
            strategy = currencyConversionStrategyFactory.getStrategy(EnumConversionType.DB);
            conversionDto = strategy.convert(sourceAmount, sourceCurrency, targetCurrency);
        }
        long date = System.currentTimeMillis();
        ConversionEntity entity = new ConversionEntity(null, date, sourceCurrency, sourceAmount, targetCurrency, conversionDto.getTargetAmount(),
                conversionDto.getExchangeRate());
        ConversionEntity conversionEntity = conversionRepository.save(entity);
        return ConversionConverter.toDto(conversionEntity);
    }

    private void validateCurrency(Double sourceAmount, String sourceCurrency, String targetCurrency) throws ValidationException {
        if (sourceAmount == null || sourceCurrency == null || sourceCurrency.equals("") || targetCurrency == null || targetCurrency.equals("")) {
            throw new ValidationException("Error is occurred while converting the currencies.");
        }
    }

    private void validate(ConversionListRequestModel request) throws ValidationException {
        if (request == null || request.getCriteria() == null || request.getSort() == null) {
            throw new ValidationException("Conversion list data is not valid!");
        }
        if (request.getCriteria() != null) {
            boolean dateIsNull = request.getCriteria().getDate() == null;
            boolean idIsNull = request.getCriteria().getId() == null;
            if (dateIsNull  && idIsNull) {
                throw new ValidationException("Conversion list data is not valid! At least one or more criteria must be provided.");
            }
        }
    }

    private GenericConversionListSpecification<ConversionListCriteriaModel> handleCriteriaList(ConversionListRequestModel request) {
        ConversionListCriteriaModel criteria = request.getCriteria();
        ConversionListSortModel sort = request.getSort();
        GenericConversionListSpecification<ConversionListCriteriaModel> spec = new GenericConversionListSpecification<>(sort);
        if (criteria == null) {
            return spec;
        }
        if (criteria.getId() != null) {
            spec.add(new SearchCriteria(EnumConversionListColumn.ID.getColumnName(), criteria.getId(), SearchOperation.GREATER_THAN_EQUAL));
        }
        if (criteria.getDate() != null) {
            spec.add(new SearchCriteria(EnumConversionListColumn.DATE.getColumnName(), criteria.getDate(), SearchOperation.GREATER_THAN_EQUAL));
        }
        if (criteria.getDate() != null) {
            spec.add(new SearchCriteria(EnumConversionListColumn.DATE.getColumnName(), criteria.getDate(), SearchOperation.LESS_THAN_EQUAL));
        }
        return spec;
    }
}