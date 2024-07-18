package com.maxim.documentfiller.DTOs.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public class FileAttributesDTO {

    private String filename;
    private List<AttributeDTO> attributes;

}
