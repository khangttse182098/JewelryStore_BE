package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.enums.*;
import com.swp.jewelrystore.model.response.DiamondCriteriaEnum;
import com.swp.jewelrystore.service.IDiamondCriteriaService;
import org.springframework.stereotype.Service;


@Service
public class DiamondCriteriaService implements IDiamondCriteriaService {

    @Override
    public DiamondCriteriaEnum getAllDiamondCriteria() {
        DiamondCriteriaEnum diamondCriteriaEnum = new DiamondCriteriaEnum();
        // 3C + 1O
        diamondCriteriaEnum.setClarity(Clarity.getAllNameOfClarity());
        diamondCriteriaEnum.setCut(Cut.getAllNameOfCut());
        diamondCriteriaEnum.setOrigin(Origin.getAllNameOfOrigin());
        diamondCriteriaEnum.setColor(Color.getAllNameOfColor());
        // polish + symmetry + fluorescence + proportions
        diamondCriteriaEnum.setPolish(Polish.getAllNameOfPolish());
        diamondCriteriaEnum.setFluorescence(Fluorescence.getAllNameOfFluorescence());
        diamondCriteriaEnum.setSymmetry(Symmetry.getAllNameOfSymmetry());
        diamondCriteriaEnum.setProportion(Proportion.getAllNameOfProportion());
        return diamondCriteriaEnum;
    }
}
