package com.swp.jewelrystore.service;

import com.swp.jewelrystore.model.response.RevenueResponseDTO;

import java.util.Map;

public interface IRevenueDashboardService {
    RevenueResponseDTO getTotalRevenueByTime(Map<String, String> params);
}
