package com.swp.jewelrystore.api;

import com.swp.jewelrystore.model.response.GemResponseDTO;
import com.swp.jewelrystore.service.IGemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/gem")
public class GemAPI {
    @Autowired
    private IGemService iGemService;

    @GetMapping
    public List<GemResponseDTO> getGem() {
        List<GemResponseDTO> gemResponseDTOS = iGemService.getAllGem();
        return gemResponseDTOS;
    }
}
