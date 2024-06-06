package com.swp.jewelrystore.api;


import com.swp.jewelrystore.model.dto.CustomerDTO;
import com.swp.jewelrystore.model.response.CustomerDetailDTO;
import com.swp.jewelrystore.model.response.CustomerResponseDTO;
import com.swp.jewelrystore.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/customer")
@CrossOrigin
public class CustomerAPI {
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/list")
    public List<CustomerResponseDTO> customerList(@RequestParam Map<String, String> phoneNumber){
        return customerService.getCustomerList(phoneNumber);
    }

    @GetMapping("/list-{id}")
    public CustomerDetailDTO detailCustomerInformation(@PathVariable Long id){
        return customerService.getCustomerDetail(id);
    }


}
