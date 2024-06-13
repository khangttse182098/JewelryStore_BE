package com.swp.jewelrystore.model.response;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DiamondCriteriaEnum {
      List<String> clarity;
      List<String> color;
      List<String> cut;
      List<String> origin;
}
