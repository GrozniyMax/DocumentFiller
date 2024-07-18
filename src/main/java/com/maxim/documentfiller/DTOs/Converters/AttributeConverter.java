package com.maxim.documentfiller.DTOs.Converters;


import com.maxim.documentfiller.MongoDB.templates.Properties.Attribute;
import com.maxim.documentfiller.DTOs.ConverterToDTO;
import com.maxim.documentfiller.DTOs.DTOs.AttributeDTO;
import org.springframework.stereotype.Component;

@Component
public class AttributeConverter implements ConverterToDTO<Attribute, AttributeDTO> {

    @Override
    public AttributeDTO toDTO(Attribute o) {
        return AttributeDTO.builder()
                .name(o.getName())
                .required(o.getRequired())
                .validationRegExpString(o.getValidationRExString())
                .build();
    }
}
