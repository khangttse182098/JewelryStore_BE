package com.swp.jewelrystore.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchRequestDTO {
        private String product_name;
        private String product_code;
        private Long counter_id;
        private String category_name;
        private String is_available;
    private Double price_rate_from;
    private Double price_rate_to;
    private String created_date_from;
    private String created_date_to;
    private Long material_id;
    private Long gem_id;
    private String origin;
    private String color;
    private String cut;
    private Double carat_weight;
    private String clarity;
}
