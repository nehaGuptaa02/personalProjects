package com.springbootcamp.interfaces;

import com.springbootcamp.dtos.AddressDto;
import com.springbootcamp.dtos.CategoryMetadataFieldDto;
import com.springbootcamp.models.Address;
import com.springbootcamp.models.CategoryMetadataField;

import java.io.IOException;

public interface CategoryMetadataFieldToTransformer<T extends CategoryMetadataField, T1 extends CategoryMetadataFieldDto> {

    T1 toDto(T CategoryMetadataField) throws IOException;

    T fromDto(T1 CategoryMetadataField);

}