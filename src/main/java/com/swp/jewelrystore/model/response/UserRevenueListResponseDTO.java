package com.swp.jewelrystore.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRevenueListResponseDTO {
    private List<Long> sellerId;
    private List<String> fullname;
    private List<Double> revenue;
}
