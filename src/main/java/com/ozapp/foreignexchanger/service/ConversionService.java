package com.ozapp.foreignexchanger.service;

import com.ozapp.foreignexchanger.data.dto.ConversionDto;
import com.ozapp.foreignexchanger.data.model.conversionlist.ConversionListRequestModel;
import org.springframework.data.domain.Page;

public interface ConversionService {
    Page<ConversionDto> getConversionList(ConversionListRequestModel request, int page, int size);

}
