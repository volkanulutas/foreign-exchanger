package com.ozapp.foreignexchanger.converter;

import com.ozapp.foreignexchanger.data.entity.ConversionEntity;
import com.ozapp.foreignexchanger.data.dto.ConversionDto;

public class ConversionConverter {
    public static ConversionDto toDto(ConversionEntity source) {
        ConversionDto target = new ConversionDto();
        target.setId(source.getId());
        target.setTargetAmount(source.getTargetAmount());
        return target;
    }

    public static ConversionEntity toEntity(ConversionDto source) {
        ConversionEntity target = new ConversionEntity();
        target.setId(source.getId());
        target.setTargetAmount(source.getTargetAmount());
        return target;
    }
}
