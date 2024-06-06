package com.swp.jewelrystore.service;

import com.swp.jewelrystore.model.dto.CustomerDTO;
import com.swp.jewelrystore.model.response.CustomerDetailDTO;
import com.swp.jewelrystore.model.response.CustomerResponseDTO;

import java.util.List;
import java.util.Map;

public interface ICustomerService {
    Long checkExisted(CustomerDTO customerDTO);
    List<CustomerResponseDTO> getCustomerList(Map<String, String> phoneNumber);
    CustomerDetailDTO getCustomerDetail(Long id);
}
