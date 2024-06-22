package com.swp.jewelrystore.api;

import com.swp.jewelrystore.model.response.InvoiceResponseDTO;
import com.swp.jewelrystore.model.response.ResponseDTO;
import com.swp.jewelrystore.model.response.RevenueResponseDTO;
import com.swp.jewelrystore.service.IOrderService;
import com.swp.jewelrystore.service.IRevenueDashboardService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;


@Transactional
@RestController
@RequestMapping("/api/revenue")
@CrossOrigin
@RequiredArgsConstructor
public class RevenueDashboardAPI {
    @Autowired
    private IRevenueDashboardService revenueDashboardService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "time", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startDate", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", dataType = "string", paramType = "query")
    })
    @GetMapping
    public ResponseEntity<ResponseDTO> getRevenueByTime(@RequestParam Map<String, String> params) {
        ResponseDTO responseDTO = new ResponseDTO();
        RevenueResponseDTO revenueResponseDTO = revenueDashboardService.getTotalRevenueByTime(params);
        responseDTO.setData(revenueResponseDTO);
        responseDTO.setMessage("Data retrieved successfully");
        return ResponseEntity.ok(responseDTO);
    }


}
