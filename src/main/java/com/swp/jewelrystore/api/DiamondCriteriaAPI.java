package com.swp.jewelrystore.api;


import com.swp.jewelrystore.model.response.DiamondCriteriaEnum;
import com.swp.jewelrystore.service.IDiamondCriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/diamond-criteria")
@RequiredArgsConstructor
public class git DiamondCriteriaAPI {

    private final IDiamondCriteriaService iDiamondCriteriaService;
    @GetMapping
    public DiamondCriteriaEnum getListDiamondCriteriaEnum(){
       return iDiamondCriteriaService.getAllDiamondCriteria();
    }
}
