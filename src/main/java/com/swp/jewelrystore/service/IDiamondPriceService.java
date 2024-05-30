package com.swp.jewelrystore.service;

import com.swp.jewelrystore.model.response.DiamondResponseDTO;

import java.util.List;

public interface IDiamondPriceService {
    List<DiamondResponseDTO> getDiamondPrice();
}
