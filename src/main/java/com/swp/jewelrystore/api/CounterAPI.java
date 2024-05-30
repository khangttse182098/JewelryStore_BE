package com.swp.jewelrystore.api;

import com.swp.jewelrystore.model.response.CounterResponseDTO;
import com.swp.jewelrystore.model.response.ProductResponseDTO;
import com.swp.jewelrystore.service.ICounterService;
import com.swp.jewelrystore.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/counter")
@CrossOrigin
public class CounterAPI {
    @Autowired
    private ICounterService counterService;

    @GetMapping
    public List<CounterResponseDTO> getCounterResponseDTO(){
        List<CounterResponseDTO> result = counterService.getCounterResponseDTO();
        return result;
    }
}
