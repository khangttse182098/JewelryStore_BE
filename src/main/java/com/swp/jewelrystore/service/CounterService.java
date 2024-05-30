package com.swp.jewelrystore.service;

import com.swp.jewelrystore.entity.CounterEntity;
import com.swp.jewelrystore.model.response.CounterResponseDTO;
import com.swp.jewelrystore.repository.CounterRepository;
import com.swp.jewelrystore.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CounterService implements ICounterService{
    @Autowired
    private CounterRepository counterRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<CounterResponseDTO> getCounterResponseDTO() {
        List<CounterEntity> counterEntities = counterRepository.findAll();
        List<CounterResponseDTO> counterResponseDTOList = new ArrayList<>();
        for (CounterEntity counterEntity : counterEntities) {
            counterResponseDTOList.add(modelMapper.map(counterEntity, CounterResponseDTO.class));
        }
        return counterResponseDTOList;
    }
}
