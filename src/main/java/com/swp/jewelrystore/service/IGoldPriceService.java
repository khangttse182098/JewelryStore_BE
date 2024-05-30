package com.swp.jewelrystore.service;

import com.swp.jewelrystore.model.response.GoldResponseDTO;

import java.util.List;

public interface IGoldPriceService {
    List<GoldResponseDTO> goldPriceList();
}
