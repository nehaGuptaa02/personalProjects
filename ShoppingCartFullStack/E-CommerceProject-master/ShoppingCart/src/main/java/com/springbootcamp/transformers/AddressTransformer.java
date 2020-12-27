package com.springbootcamp.transformers;

import com.springbootcamp.models.Address;
import com.springbootcamp.dtos.AddressDto;
import com.springbootcamp.interfaces.AddressToTransformer;

public class AddressTransformer implements AddressToTransformer<Address, AddressDto>
{
    @Override
    public AddressDto toDto(Address address)  {
        AddressDto addressDto=new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setCity(address.getCity());
        addressDto.setState(address.getState());
        addressDto.setCountry(address.getCountry());
        addressDto.setZipCode(address.getZipCode());
        addressDto.setLabel(address.getLabel());
        addressDto.setAddressLine(address.getAddressLine());
        return addressDto;
    }

    @Override
    public Address fromDto(AddressDto addressDto) {
        Address address=new Address();
        address.setId(addressDto.getId());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setCountry(addressDto.getCountry());
        address.setZipCode(addressDto.getZipCode());
        address.setLabel(addressDto.getLabel());
        address.setAddressLine(addressDto.getAddressLine());
        return address;
    }
}
