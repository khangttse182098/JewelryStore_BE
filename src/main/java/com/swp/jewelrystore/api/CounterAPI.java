package com.swp.jewelrystore.api;

import com.swp.jewelrystore.model.response.CounterResponseDTO;
import com.swp.jewelrystore.model.response.ProductResponseDTO;
import com.swp.jewelrystore.service.ICounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
