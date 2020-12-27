package com.springbootcamp.interfaces;

import com.springbootcamp.models.Address;
import com.springbootcamp.dtos.AddressDto;

import java.io.IOException;

public interface AddressToTransformer<T extends Address, T1 extends AddressDto> {

    T1 toDto(T Address) throws IOException;

    T fromDto(T1 AddressDto);

}