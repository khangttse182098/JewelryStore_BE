package com.swp.jewelrystore.api;


import com.swp.jewelrystore.model.dto.CustomerDTO;
import com.swp.jewelrystore.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/customer")
@CrossOrigin
public class CustomerAPI {
    @Autowired
    private ICustomerService customerService;

    @PostMapping("/information")
    public Long getCustomerId(@RequestBody CustomerDTO customerDTO){
        Long id = customerService.checkExisted(customerDTO);
        return id;
    }

}
