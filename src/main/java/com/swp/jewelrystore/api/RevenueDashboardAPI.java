package com.swp.jewelrystore.api;

import com.swp.jewelrystore.entity.UserEntity;
import com.swp.jewelrystore.model.response.*;
import com.swp.jewelrystore.repository.UserRepository;
import com.swp.jewelrystore.service.IOrderService;
import com.swp.jewelrystore.service.IRevenueDashboardService;
import com.swp.jewelrystore.service.IUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

    @Autowired
    private IUserService userService;
    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("seller-revenue")
    public UserRevenueListResponseDTO getSellerRevenueByDate(@RequestParam Map<String, String> params) {
        List<UserEntity> sellerList = userRepository.findByRoleCode("SELLER");
        List<UserRevenueResponseDTO> userRevenueResponseDTOS = userService.getUserRevenue(params);
        for(UserEntity seller : sellerList) {
            int flag = 0;
            for(UserRevenueResponseDTO userRevenueResponseDTO : userRevenueResponseDTOS) {
                if(userRevenueResponseDTO.getSellerId() == seller.getId()) {
                    flag = 1;
                    break;
                }
            }
            if(flag == 0) {
                userRevenueResponseDTOS.add(new UserRevenueResponseDTO(seller.getId(), seller.getFullName(),0.0));
            }
        }
        List<Long> sellerIdList = new ArrayList<>();
        List<Double> revenueList = new ArrayList<>();
        List<String> fullNameList = new ArrayList<>();
        for(UserRevenueResponseDTO userRevenueResponseDTO : userRevenueResponseDTOS) {
            sellerIdList.add(userRevenueResponseDTO.getSellerId());
            revenueList.add(userRevenueResponseDTO.getRevenue());
            fullNameList.add(userRevenueResponseDTO.getFullname());
        }
        UserRevenueListResponseDTO userRevenueListResponseDTO = new UserRevenueListResponseDTO(sellerIdList, fullNameList, revenueList);
        return userRevenueListResponseDTO;
    }

}
