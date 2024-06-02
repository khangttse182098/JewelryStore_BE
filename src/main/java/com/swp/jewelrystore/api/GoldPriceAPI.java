package com.swp.jewelrystore.api;


import com.swp.jewelrystore.model.response.GoldResponseDTO;
import com.swp.jewelrystore.service.IGoldPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/gold-price")
@CrossOrigin
@RequiredArgsConstructor
public class GoldPriceAPI {

      private final IGoldPriceService goldPriceService;

      @GetMapping
      public List<GoldResponseDTO> goldPriceList(){
          return goldPriceService.goldPriceList();
      }
}
