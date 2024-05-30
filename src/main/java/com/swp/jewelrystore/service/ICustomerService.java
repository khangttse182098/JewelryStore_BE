package com.swp.jewelrystore.service;

import com.swp.jewelrystore.model.dto.CustomerDTO;

public interface ICustomerService {
    Long checkExisted(CustomerDTO customerDTO);
}
