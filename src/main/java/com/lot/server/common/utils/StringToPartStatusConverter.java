package com.lot.server.common.utils;

import com.lot.server.part.domain.entity.PartStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPartStatusConverter implements Converter<String, PartStatus> {

    @Override
    public PartStatus convert(String source) {
        for (PartStatus status : PartStatus.values()) {
            if (status.getValue().equalsIgnoreCase(source)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid PartStatus: " + source);
    }
}
