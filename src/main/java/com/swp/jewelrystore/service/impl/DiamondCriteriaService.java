package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.enums.Clarity;
import com.swp.jewelrystore.enums.Color;
import com.swp.jewelrystore.enums.Cut;
import com.swp.jewelrystore.enums.Origin;
import com.swp.jewelrystore.model.response.DiamondCriteriaEnum;
import com.swp.jewelrystore.service.IDiamondCriteriaService;
import org.springframework.stereotype.Service;


@Service
public class DiamondCriteriaService implements IDiamondCriteriaService {

    @Override
    public DiamondCriteriaEnum getAllDiamondCriteria() {
        DiamondCriteriaEnum diamondCriteriaEnum = new DiamondCriteriaEnum();
        diamondCriteriaEnum.setClarity(Clarity.getAllNameOfClarity());
        diamondCriteriaEnum.setCut(Cut.getAllNameOfCut());
        diamondCriteriaEnum.setOrigin(Origin.getAllNameOfOrigin());
        diamondCriteriaEnum.setColor(Color.getAllNameOfColor());
        return diamondCriteriaEnum;
    }
}
