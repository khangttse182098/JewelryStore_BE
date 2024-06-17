package com.swp.jewelrystore.api;

import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.model.dto.DiamondDTO;
import com.swp.jewelrystore.model.response.DiamondResponseDTO;
import com.swp.jewelrystore.model.response.ResponseDTO;
import com.swp.jewelrystore.service.IDiamondPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

     @PostMapping("/information")
     public ResponseEntity<ResponseDTO> addOrUpdateDiamondPrice(@RequestBody @Valid DiamondDTO diamondDTO) {
         ResponseDTO responseDTO = new ResponseDTO();
         try {
             diamondPriceService.addOrUpdateDiamondPrice(diamondDTO);
             responseDTO.setData(diamondDTO);
             responseDTO.setMessage(SystemConstant.ADD_OR_UPDATE_DIAMOND_SUCCESSFULLY);
             return ResponseEntity.ok().body(responseDTO);
         } catch (Exception e){
             responseDTO.setMessage(e.getMessage());
             return ResponseEntity.badRequest().body(responseDTO);
         }
     }
}
