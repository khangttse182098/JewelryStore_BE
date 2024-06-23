package com.swp.jewelrystore.service;



import com.swp.jewelrystore.model.response.GemResponseDTO;

import java.util.List;

public interface IGemService {
    List<GemResponseDTO> getAllGem();
}
