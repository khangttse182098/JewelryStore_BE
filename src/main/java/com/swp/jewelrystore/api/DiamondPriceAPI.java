package com.swp.jewelrystore.api;

import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.model.dto.DiamondDTO;
import com.swp.jewelrystore.model.dto.GemKeyDTO;
import com.swp.jewelrystore.model.dto.GemPriceDTO;
import com.swp.jewelrystore.model.dto.GemWithPriceDTO;
import com.swp.jewelrystore.model.response.DiamondResponseDTO;
import com.swp.jewelrystore.model.response.GemDetailResponseDTO;
import com.swp.jewelrystore.model.response.GemPriceDistinctResponseDTO;
import com.swp.jewelrystore.model.response.ResponseDTO;
import com.swp.jewelrystore.service.IDiamondPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/diamond-price")
@CrossOrigin
@RequiredArgsConstructor
public class DiamondPriceAPI {
     private final IDiamondPriceService diamondPriceService;

     // Danh sách thông tin kim cương
     @GetMapping
     public List<DiamondResponseDTO> getDiamondInformation(){
         return diamondPriceService.getDiamondInformation();
     }

    // Danh sách chi tiết thông tin kim cương
    @GetMapping("/information-{id}")
    public GemDetailResponseDTO getDiamondDetail(@PathVariable Long id) {
        return diamondPriceService.getDiamondDetail(id);
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


    @PostMapping("/information/diamond/new-price")
    public ResponseEntity<ResponseDTO> addNewPriceForDiamondEntity(@RequestBody GemPriceDTO gemPriceDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            diamondPriceService.addNewPriceForDiamondEntity(gemPriceDTO);
            responseDTO.setMessage("Thêm giá thành công !!");
            responseDTO.setData(gemPriceDTO);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e){
            responseDTO.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }

    @PostMapping("/information/add")
    public ResponseEntity<ResponseDTO> addDiamondEntity(@Valid @RequestBody DiamondDTO diamondDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            String result = diamondPriceService.addDiamondEntity(diamondDTO);
            responseDTO.setMessage(result);
            responseDTO.setData(diamondDTO);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e){
            responseDTO.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }



    // Lịch sử giá vàng của từng loại đá
    @GetMapping("/history")
    public List<GemPriceDistinctResponseDTO> getHistoryGemPrice() {
       return diamondPriceService.getHistoryGemPrice();
    }

    // Chi tiết lịch sử giá vàng của từng loại
    @GetMapping("/history/details")
    public List<GemPriceDistinctResponseDTO> getHistoryGemPriceDetail(@ModelAttribute GemKeyDTO gemKey) {
        return diamondPriceService.getHistoryGemPriceDetail(gemKey);
    }
}
