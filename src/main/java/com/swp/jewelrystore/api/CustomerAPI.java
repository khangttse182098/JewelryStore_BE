package com.swp.jewelrystore.api;


import com.swp.jewelrystore.entity.CustomerEntity;
import com.swp.jewelrystore.entity.SellOrderEntity;
import com.swp.jewelrystore.model.dto.CustomerDTO;
import com.swp.jewelrystore.model.response.CustomerResponseDTO;
import com.swp.jewelrystore.repository.CustomerRepository;
import com.swp.jewelrystore.repository.SellOrderRepository;
import com.swp.jewelrystore.model.response.CustomerDetailDTO;
import com.swp.jewelrystore.model.response.CustomerResponseDTO;
import com.swp.jewelrystore.service.ICustomerService;
import org.modelmapper.ModelMapper;
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
    @Autowired
    private SellOrderRepository sellOrderRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/list")
    public List<CustomerResponseDTO> customerList(@RequestParam Map<String, String> phoneNumber){
        return customerService.getCustomerList(phoneNumber);
    }

    @GetMapping("/list-{id}")
    public CustomerDetailDTO detailCustomerInformation(@PathVariable Long id){
        return customerService.getCustomerDetail(id);
    }


    @GetMapping
    public CustomerResponseDTO findCustomerBySellOrderCode(@RequestParam String sellOrderCode){
        SellOrderEntity sellOrderEntity = sellOrderRepository.findBySellOrderCodeIs(sellOrderCode);
        CustomerResponseDTO customerResponseDTO = modelMapper.map(sellOrderEntity.getCustomer(), CustomerResponseDTO.class);
        customerResponseDTO.setExpense(null);
        customerResponseDTO.setQuantityOrder(0);
        return customerResponseDTO;
    }
    @PostMapping
    public String addOrUpdateCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerEntity customerEntity = modelMapper.map(customerDTO, CustomerEntity.class);
        customerRepository.save(customerEntity);
        if(customerDTO.getId() == null) return "Add customer successfully";
        return "Update customer successfully";
    }
    @DeleteMapping("{ids}")
    public String deleteCustomer(@PathVariable Long[] ids){}
}
