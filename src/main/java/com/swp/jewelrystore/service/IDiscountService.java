package com.swp.jewelrystore.service;


import com.swp.jewelrystore.model.dto.DiscountDTO;
import com.swp.jewelrystore.model.response.DiscountResponseDTO;

import java.util.List;

public interface IDiscountService {
   List<DiscountResponseDTO> getDiscountInformation();
   void addDiscountInformation(DiscountDTO discountDTO);
}
