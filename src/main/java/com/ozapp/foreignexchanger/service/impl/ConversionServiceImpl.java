package com.ozapp.foreignexchanger.service.impl;

import com.ozapp.foreignexchanger.converter.ConversionConverter;
import com.ozapp.foreignexchanger.data.ConversionEntity;
import com.ozapp.foreignexchanger.data.dto.ConversionDto;
import com.ozapp.foreignexchanger.data.model.conversionlist.ConversionListCriteriaModel;
import com.ozapp.foreignexchanger.data.model.conversionlist.ConversionListRequestModel;
import com.ozapp.foreignexchanger.repository.ConversionRepository;
import com.ozapp.foreignexchanger.service.ConversionService;
import com.ozapp.foreignexchanger.service.impl.conversionlist.search.GenericDetectionSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConversionServiceImpl implements ConversionService {
    private final ConversionRepository conversionRepository;

    @Autowired
    public ConversionServiceImpl(ConversionRepository conversionRepository) {this.conversionRepository = conversionRepository;}

    @Override
    @Transactional
    public Page<ConversionDto> getConversionList(ConversionListRequestModel request, int page, int size) {
        if (request == null) {
            return new PageImpl<>(Collections.emptyList());
        }
        GenericDetectionSpecification specification = handleCriteriaList(request);
        Page<ConversionEntity> allPage = conversionRepository.findAll(specification, PageRequest.of(page, size));
        List<ConversionDto> targetList = allPage.stream().map(e -> ConversionConverter.toDto(e)).collect(Collectors.toList());
        return new PageImpl<>(targetList, allPage.getPageable(), allPage.getTotalElements());
    }

    private GenericDetectionSpecification<ConversionListCriteriaModel> handleCriteriaList(ConversionListRequestModel request) {
        return null;
    }
}
