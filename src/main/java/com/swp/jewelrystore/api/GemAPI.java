package com.swp.jewelrystore.api;


import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.model.dto.GemWithPriceDTO;
import com.swp.jewelrystore.model.response.GemDetailResponseDTO;
import com.swp.jewelrystore.model.response.GemResponseDTO;
import com.swp.jewelrystore.model.response.ResponseDTO;
import com.swp.jewelrystore.service.IGemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/gem")
@RequiredArgsConstructor
public class GemAPI {

    private final IGemService iGemService;
    
    @GetMapping
    public List<GemResponseDTO> getGemForProduct() {
        return iGemService.getAllGem();
    }

    @GetMapping
    public List<GemResponseDTO> getGem() {
        return iGemService.getAllGem();
    }

    @GetMapping("/information-{id}")
    public GemDetailResponseDTO getGemDetail(@PathVariable Long id) {
        return iGemService.getGemDetail(id);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addOrUpdateGemWithPrice(@RequestBody GemWithPriceDTO gem){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            iGemService.addOrUpdateGemWithPrice(gem);
            responseDTO.setMessage(SystemConstant.ADD_OR_UPDATE_DIAMOND_SUCCESSFULLY);
            responseDTO.setData(gem);
            return ResponseEntity.ok(responseDTO);
        }  catch (Exception e) {
            responseDTO.setMessage(e.toString());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
