package com.springbootcamp.transformers;

import com.springbootcamp.dtos.SellerDto;
import com.springbootcamp.models.Seller;
import com.springbootcamp.interfaces.SellerToTransformer;

public class SellerTransformer implements SellerToTransformer<Seller, SellerDto> {
    @Override
    public SellerDto toDto(Seller seller) {
        SellerDto sellerDto = new SellerDto();
        sellerDto.setId(seller.getId());
        sellerDto.setEmail(seller.getEmail());
        sellerDto.setFirstName(seller.getFirstName());
        if (seller.getMiddleName() != null)
            sellerDto.setMiddleName(seller.getMiddleName());
        sellerDto.setLastName(seller.getLastName());
        sellerDto.setGst(seller.getGst());
        sellerDto.setCompanyName(seller.getCompanyName());
        sellerDto.setCompanyContact(seller.getCompanyContact());
        sellerDto.setActive(seller.isActive());
        sellerDto.setDeleted(seller.isDeleted());
        sellerDto.setFilePath(seller.getFilePath());
        return sellerDto;

    }

    @Override
    public Seller fromDto(SellerDto SellerDto) {
        return null;
    }
}
