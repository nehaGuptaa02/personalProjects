package com.springbootcamp.interfaces;

import com.springbootcamp.dtos.CustomerDto;
import com.springbootcamp.models.Customer;

import java.io.IOException;

public interface CustomerToTransformer<T extends Customer, T1 extends CustomerDto> {

    T1 toDto(T Customer) throws IOException;

    T fromDto(T1 CustomerDto);

}