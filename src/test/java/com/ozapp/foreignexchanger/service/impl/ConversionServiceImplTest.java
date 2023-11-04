package com.ozapp.foreignexchanger.service.impl;

import com.ozapp.foreignexchanger.data.currencyconversion.CurrencyConversionDto;
import com.ozapp.foreignexchanger.data.dto.ConversionDto;
import com.ozapp.foreignexchanger.data.entity.ConversionEntity;
import com.ozapp.foreignexchanger.data.exception.ValidationException;
import com.ozapp.foreignexchanger.data.model.conversionlist.ConversionListCriteriaModel;
import com.ozapp.foreignexchanger.data.model.conversionlist.ConversionListRequestModel;
import com.ozapp.foreignexchanger.data.model.conversionlist.ConversionListSortModel;
import com.ozapp.foreignexchanger.data.model.conversionlist.EnumConversionListColumn;
import com.ozapp.foreignexchanger.data.model.conversionlist.EnumConversionListSortType;
import com.ozapp.foreignexchanger.repository.ConversionRepository;
import com.ozapp.foreignexchanger.restclient.FixerExchangeRestClient;
import com.ozapp.foreignexchanger.service.DbSourceExchangeRateService;
import com.ozapp.foreignexchanger.service.strategy.CurrencyConversionStrategyFactory;
import com.ozapp.foreignexchanger.service.strategy.EnumConversionType;
import com.ozapp.foreignexchanger.service.strategy.FixerCurrencyConversionStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ConversionServiceImplTest {
    @Mock
    private ConversionRepository conversionRepository;

    @Mock
    private CurrencyConversionStrategyFactory currencyConversionStrategyFactory;

    @Mock
    private FixerCurrencyConversionStrategy fixerCurrencyConversionStrategy;

    @Mock
    private FixerExchangeRestClient fixerExchangeRestClient;

    @Mock
    private DbSourceExchangeRateService dbSourceExchangeRateService;

    @InjectMocks
    private ConversionServiceImpl conversionService;

    @Test
    void getConvertedCurrencyList() throws ValidationException {
        ConversionListRequestModel request = getMockRequestData();
        List<ConversionEntity> conversionEntities = new ArrayList<>();
        conversionEntities.add(new ConversionEntity());
        Page<ConversionEntity> pageableData = new PageImpl<>(conversionEntities);
        Mockito.when(conversionRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(pageableData);
        Page<ConversionDto> convertedCurrencyList = conversionService.getConvertedCurrencyList(request, 0, 10);
        Assertions.assertEquals(1, convertedCurrencyList.getTotalElements());
    }

    @Test
    void convertCurrency_Validate() {
        Assertions.assertThrows(ValidationException.class, () -> conversionService.getConvertedCurrencyList(null, 0, 10));
        ConversionListRequestModel request = new ConversionListRequestModel();
        Assertions.assertThrows(ValidationException.class, () -> conversionService.getConvertedCurrencyList(request, 0, 10));
    }

    @Test
    void convertCurrency() throws ValidationException {
        Mockito.when(currencyConversionStrategyFactory.getStrategy(EnumConversionType.FIXER)).thenReturn(fixerCurrencyConversionStrategy);
        CurrencyConversionDto mockCurrencyConversionDto = new CurrencyConversionDto(1000, 1.1);
        Mockito.when(fixerCurrencyConversionStrategy.convert(Mockito.anyDouble(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(mockCurrencyConversionDto);
        ConversionEntity mockEntity = new ConversionEntity();
        mockEntity.setTargetAmount(1001L);
        mockEntity.setId("550e8400-e29b-41d4-a716-446655440000");
        Mockito.when(conversionRepository.save(Mockito.any(ConversionEntity.class))).thenReturn(mockEntity);
        ConversionDto conversionDto = conversionService.convertCurrency(Double.valueOf(1000L), "EUR", "USD");
        Assertions.assertEquals(1001L, conversionDto.getTargetAmount());
        Assertions.assertEquals("550e8400-e29b-41d4-a716-446655440000", conversionDto.getId());
    }

    private static ConversionListRequestModel getMockRequestData() {
        ConversionListRequestModel request = new ConversionListRequestModel();
        ConversionListCriteriaModel criteria = new ConversionListCriteriaModel();
        criteria.setDate(10L);
        criteria.setId("550e8400-e29b-41d4-a716-446655440000");
        request.setCriteria(criteria);
        ConversionListSortModel sort = new ConversionListSortModel();
        sort.setColumn(EnumConversionListColumn.ID); // TODO: no multiple, future work
        sort.setType(EnumConversionListSortType.ASC);
        request.setSort(sort);
        request.setCriteria(criteria);
        return request;
    }

}