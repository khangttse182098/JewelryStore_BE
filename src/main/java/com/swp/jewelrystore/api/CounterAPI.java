package com.swp.jewelrystore.api;

import com.swp.jewelrystore.model.response.CounterResponseDTO;
import com.swp.jewelrystore.model.response.ProductResponseDTO;
import com.swp.jewelrystore.service.ICounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/counter")
@CrossOrigin
@RequiredArgsConstructor
public class CounterAPI {

    private final ICounterService counterService;

    @GetMapping
    public List<CounterResponseDTO> getCounterResponseDTO(){
        List<CounterResponseDTO> result = counterService.getCounterResponseDTO();
        return result;
    }
}
