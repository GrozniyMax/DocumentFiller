package com.maxim.documentfiller.DTOs.Converters;

import com.maxim.documentfiller.MongoDB.templates.Properties.Attribute;
import com.maxim.documentfiller.MongoDB.templates.Properties.FileAttributes;
import com.maxim.documentfiller.DTOs.ConverterToDTO;
import com.maxim.documentfiller.DTOs.DTOs.AttributeDTO;
import com.maxim.documentfiller.DTOs.DTOs.FileAttributesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileConverter implements ConverterToDTO<FileAttributes, FileAttributesDTO> {
    @Autowired
    private ConverterToDTO<Attribute, AttributeDTO> attributeConverter;

    @Override
    public FileAttributesDTO toDTO(FileAttributes o) {
        return FileAttributesDTO.builder()
                .filename(o.getFilename())
                .attributes(
                        o.getProperties().stream().map(attributeConverter::toDTO).toList()
                ).build();
    }
}
