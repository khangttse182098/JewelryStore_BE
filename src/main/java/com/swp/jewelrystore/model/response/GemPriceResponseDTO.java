package com.swp.jewelrystore.model.response;

import com.swp.jewelrystore.model.key.GemPriceKey;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;


@Getter
@Setter
@Entity
public class GemPriceResponseDTO {

 @EmbeddedId
 private GemPriceKey id;


}
