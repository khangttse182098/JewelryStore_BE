package com.swp.jewelrystore.service;

import com.swp.jewelrystore.model.response.CounterResponseDTO;

import java.util.List;

public interface ICounterService {
    List<CounterResponseDTO > getCounterResponseDTO();
}
