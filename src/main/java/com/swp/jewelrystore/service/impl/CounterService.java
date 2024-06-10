package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.entity.CounterEntity;
import com.swp.jewelrystore.model.response.CounterResponseDTO;
import com.swp.jewelrystore.repository.CounterRepository;
import com.swp.jewelrystore.service.ICounterService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CounterService implements ICounterService {

    private final CounterRepository counterRepository;
    private final ModelMapper modelMapper;

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
