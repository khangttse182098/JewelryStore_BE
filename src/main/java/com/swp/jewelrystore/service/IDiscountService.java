package com.swp.jewelrystore.service;


import com.swp.jewelrystore.model.dto.DiscountDTO;
import com.swp.jewelrystore.model.response.DiscountResponseDTO;

import java.util.List;
import java.util.Map;

public interface IDiscountService {
   List<DiscountResponseDTO> getDiscountInformation(Map<String, String> filter);
   void addDiscountInformation(DiscountDTO discountDTO);
}
