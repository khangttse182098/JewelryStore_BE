package com.swp.jewelrystore.service;

import com.swp.jewelrystore.entity.CustomerEntity;
import com.swp.jewelrystore.model.dto.CustomerDTO;
import com.swp.jewelrystore.model.response.CustomerResponseDTO;
import com.swp.jewelrystore.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService implements ICustomerService{

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
