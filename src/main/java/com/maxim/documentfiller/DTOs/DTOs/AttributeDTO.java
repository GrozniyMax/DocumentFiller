package com.maxim.documentfiller.DTOs.DTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Builder
@Getter
@Setter
public class AttributeDTO {

    private String name;
    private Optional<Boolean> required;
    private Optional<String> validationRegExpString;

}
