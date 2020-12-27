package com.springbootcamp.transformers;

import com.springbootcamp.dtos.CustomerDto;
import com.springbootcamp.models.Customer;
import com.springbootcamp.interfaces.CustomerToTransformer;

public class CustomerTransformer implements CustomerToTransformer<Customer, CustomerDto> {
    @Override
    public CustomerDto toDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setEmail(customer.getEmail());
        customerDto.setFirstName(customer.getFirstName());
        if (customer.getMiddleName() != null) {
            customerDto.setMiddleName(customer.getMiddleName());
        }
        customerDto.setLastName(customer.getLastName());
        customerDto.setContact(customer.getContact());
        customerDto.setActive(customer.isActive());
        customerDto.setDeleted(customer.isDeleted());
        customerDto.setFilePath(customer.getFilePath());
        return customerDto;

    }

    @Override
    public Customer fromDto(CustomerDto baseDto) {
        return null;
    }
}
