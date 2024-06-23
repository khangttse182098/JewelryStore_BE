package com.swp.jewelrystore.api;


import com.swp.jewelrystore.model.response.GemResponseDTO;
import com.swp.jewelrystore.service.IGemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/gem")
@RequiredArgsConstructor
public class GemAPI {

    private final IGemService iGemService;

    @GetMapping
    public List<GemResponseDTO> getGem() {
        return iGemService.getAllGem();
    }

}
