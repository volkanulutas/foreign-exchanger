package com.ozapp.foreignexchanger.service.impl;

import com.ozapp.foreignexchanger.converter.ConversionConverter;
import com.ozapp.foreignexchanger.data.ConversionEntity;
import com.ozapp.foreignexchanger.data.dto.ConversionDto;
import com.ozapp.foreignexchanger.data.exception.ValidationException;
import com.ozapp.foreignexchanger.data.model.conversionlist.ConversionListCriteriaModel;
import com.ozapp.foreignexchanger.data.model.conversionlist.ConversionListRequestModel;
import com.ozapp.foreignexchanger.data.model.conversionlist.ConversionListSortModel;
import com.ozapp.foreignexchanger.data.model.conversionlist.EnumConversionListColumn;
import com.ozapp.foreignexchanger.repository.ConversionRepository;
import com.ozapp.foreignexchanger.service.ConversionService;
import com.ozapp.foreignexchanger.service.ExchangeRateService;
import com.ozapp.foreignexchanger.service.impl.conversionlist.search.GenericConversionListSpecification;
import com.ozapp.foreignexchanger.service.impl.conversionlist.search.SearchCriteria;
import com.ozapp.foreignexchanger.service.impl.conversionlist.search.SearchOperation;
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

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ConversionServiceImpl(ConversionRepository conversionRepository, ExchangeRateService exchangeRateService) {
        this.conversionRepository = conversionRepository;
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    @Transactional
    public Page<ConversionDto> getConvertedCurrencyList(ConversionListRequestModel request, int page, int size) throws ValidationException {
       try {
           validate(request);
           GenericConversionListSpecification specification = handleCriteriaList(request);
           Page<ConversionEntity> allPage = conversionRepository.findAll(specification, PageRequest.of(page, size));
           List<ConversionDto> targetList = allPage.stream().map(e -> ConversionConverter.toDto(e)).collect(Collectors.toList());
           return new PageImpl<>(targetList, allPage.getPageable(), allPage.getTotalElements());
       }catch (Exception ex){
           log.error("errr");
       }
       return null;

    }

    @Override
    public ConversionDto convertCurrency(Double sourceAmount, String sourceCurrency, String targetCurrency) throws ValidationException {
        validateCurrency(sourceAmount, sourceCurrency, targetCurrency);
        try {
            double exchangeRate = exchangeRateService.calculateExchangeRate(sourceCurrency, targetCurrency);
            double targetAmount = exchangeRate * sourceAmount;
            // long date = System.currentTimeMillis(); // TODO:
            long date = 100;
            ConversionEntity entity =
                    new ConversionEntity(null, date, sourceCurrency, sourceAmount, targetCurrency, targetAmount, exchangeRate);
            ConversionEntity conversionEntity = conversionRepository.save(entity);
            return ConversionConverter.toDto(conversionEntity);
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
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
    }

    private GenericConversionListSpecification<ConversionListCriteriaModel> handleCriteriaList(ConversionListRequestModel request) {
        ConversionListCriteriaModel criteria = request.getCriteria();
        ConversionListSortModel sort = request.getSort();
        GenericConversionListSpecification<ConversionListCriteriaModel> spec = new GenericConversionListSpecification<>(sort);
        if (criteria == null) {
            return spec;
        }
        if (criteria.getId() != null) {
            spec.add(new SearchCriteria(EnumConversionListColumn.ID.getColumnName(), criteria.getId(),
                    SearchOperation.GREATER_THAN_EQUAL));
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