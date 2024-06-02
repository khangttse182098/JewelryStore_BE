package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.entity.CustomerEntity;
import com.swp.jewelrystore.model.dto.CustomerDTO;
import com.swp.jewelrystore.repository.CustomerRepository;
import com.swp.jewelrystore.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;


    @Override
    public Long checkExisted(CustomerDTO customerDTO) {
        CustomerEntity customer = customerRepository.findByPhoneNumber(customerDTO.getPhoneNumber());
        if (customer != null){
            return customer.getId();
        }
        CustomerEntity newCustomer = modelMapper.map(customerDTO, CustomerEntity.class);
        customerRepository.save(newCustomer);
        return newCustomer.getId();
    }
}
