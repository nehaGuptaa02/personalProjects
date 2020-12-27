package com.springbootcamp.interfaces;


import com.springbootcamp.dtos.SellerDto;
import com.springbootcamp.models.Seller;

import java.io.IOException;

public interface SellerToTransformer<T extends Seller,T1 extends SellerDto> {


    T1 toDto(T Seller) throws IOException;

    T fromDto(T1 SellerDto);

}
