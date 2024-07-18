package com.maxim.documentfiller.DTOs;

import org.springframework.stereotype.Service;

/**
 * <strong>All fields that exist in original object and don't exist in DTO must be null if there is no other rule of setting them</strong>
 * @param <O> original object class
 * @param <D> DTO class
 * @author Taranenko Maxim
 */
@FunctionalInterface
public interface FromDTORecoverer<O,D> {
    O fromDTO(D dto);
}
