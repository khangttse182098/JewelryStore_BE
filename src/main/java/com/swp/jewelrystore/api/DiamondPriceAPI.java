package com.swp.jewelrystore.api;

import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.model.dto.GemWithPriceDTO;
import com.swp.jewelrystore.model.response.DiamondResponseDTO;
import com.swp.jewelrystore.model.response.GemDetailResponseDTO;
import com.swp.jewelrystore.model.response.ResponseDTO;
import com.swp.jewelrystore.service.IDiamondPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/diamond-price")
@CrossOrigin
@RequiredArgsConstructor
public class DiamondPriceAPI {
     private final IDiamondPriceService diamondPriceService;

     @GetMapping
     public List<DiamondResponseDTO> getDiamondPrice(){
         return diamondPriceService.getDiamondPrice();
     }

    @GetMapping("/information-{id}")
    public GemDetailResponseDTO getGemDetail(@PathVariable Long id) {
        return diamondPriceService.getGemDetail(id);
    }

     @PostMapping("/information")
     public ResponseEntity<ResponseDTO> addOrUpdateDiamondPrice(@RequestBody GemWithPriceDTO gem) {
         ResponseDTO responseDTO = new ResponseDTO();
         try {
             diamondPriceService.addOrUpdateDiamondPrice(gem);
             responseDTO.setData(gem);
             responseDTO.setMessage(SystemConstant.ADD_OR_UPDATE_DIAMOND_SUCCESSFULLY);
             return ResponseEntity.ok().body(responseDTO);
         } catch (Exception e){
             responseDTO.setMessage(e.getMessage());
             return ResponseEntity.badRequest().body(responseDTO);
         }
     }

    @GetMapping("/history-{id}")
    public List<DiamondResponseDTO> getHistoryGemPrice(@PathVariable Long id) {
        return diamondPriceService.getHistoryGemPrice(id);
    }
}
