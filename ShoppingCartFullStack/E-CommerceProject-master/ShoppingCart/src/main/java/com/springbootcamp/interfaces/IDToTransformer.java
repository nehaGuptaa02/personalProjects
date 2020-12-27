package com.springbootcamp.interfaces;

import com.springbootcamp.dtos.BaseDto;
import com.springbootcamp.models.DomainBase;

import java.io.IOException;

public interface IDToTransformer<T extends DomainBase, T1 extends BaseDto> {

    T1 toDto(T domainBase) throws IOException;

    T fromDto(T1 baseDto);

}