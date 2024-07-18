package com.maxim.documentfiller.DTOs;

import org.springframework.stereotype.Service;

/**
 *
 * @param <O> original class
 * @param <D> DTO class
 */

@FunctionalInterface
public interface ConverterToDTO<O,D> {
    D toDTO(O o);
}
