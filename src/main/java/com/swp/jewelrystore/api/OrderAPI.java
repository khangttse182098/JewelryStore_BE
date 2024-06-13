package com.swp.jewelrystore.api;

import com.swp.jewelrystore.entity.PurchaseOrderEntity;
import com.swp.jewelrystore.entity.SellOrderEntity;
import com.swp.jewelrystore.model.dto.CriteriaDTO;
import com.swp.jewelrystore.model.dto.StatusDTO;
import com.swp.jewelrystore.model.response.CriteriaResponseDTO;
import com.swp.jewelrystore.model.response.InvoiceResponseDTO;
import com.swp.jewelrystore.model.response.MaterialResponseDTO;
import com.swp.jewelrystore.repository.PurchaseOrderRepository;
import com.swp.jewelrystore.repository.SellOrderRepository;
import com.swp.jewelrystore.service.IOrderService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.swp.jewelrystore.model.dto.InvoiceDTO;
import com.swp.jewelrystore.model.dto.PurchaseOrderDTO;
import com.swp.jewelrystore.service.IPurchaseOrderService;
import com.swp.jewelrystore.service.ISellOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
@CrossOrigin
@RequiredArgsConstructor
@Transactional
public class OrderAPI {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private SellOrderRepository sellOrderRepository;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", dataType = "string", paramType = "query")
    })
    @GetMapping
    public List<InvoiceResponseDTO> getAllOrder(@RequestParam Map<String, String> params){
        List<InvoiceResponseDTO> invoiceList = orderService.getAllOrder(params);
        return invoiceList;
    }
    @GetMapping ("/seller-page")
    public List<InvoiceResponseDTO> getPaidAndDeliveredSellOrder(){
        List<InvoiceResponseDTO> invoiceList = orderService.getPaidAndDeliveredSellOrder();
        return invoiceList;
    }

    @GetMapping("/cashier-page")
    public List<InvoiceResponseDTO> getPaidAndReceivedPurchaseOrder(){
            List<InvoiceResponseDTO> invoiceList = orderService.getPaidAndReceivedPurchaseOrder();
        return invoiceList;
    }

    @PostMapping("/status/paid")
    public String changeStatusToPaid(@RequestBody StatusDTO statusDTO){
        PurchaseOrderEntity purchaseOrderEntity = purchaseOrderRepository.findByPurchaseOrderCode(statusDTO.getInvoiceCode());
        if(purchaseOrderEntity != null){
            purchaseOrderEntity.setStatus("Đã thanh toán");
            purchaseOrderRepository.save(purchaseOrderEntity);
            return "Change to Paid successfully";
        }
        SellOrderEntity sellOrderEntity = sellOrderRepository.findBySellOrderCodeIs(statusDTO.getInvoiceCode());
        if(sellOrderEntity != null){
            sellOrderEntity.setStatus("Đã thanh toán");
            sellOrderRepository.save(sellOrderEntity);
        }
        return "Change to Paid successfully";
    }
    @PostMapping("/status/delivered")
    public String changeStatusToDelivered(@RequestBody StatusDTO statusDTO){
        PurchaseOrderEntity purchaseOrderEntity = purchaseOrderRepository.findByPurchaseOrderCode(statusDTO.getInvoiceCode());
        if(purchaseOrderEntity != null){
            purchaseOrderEntity.setStatus("Đã nhận hàng");
            purchaseOrderRepository.save(purchaseOrderEntity);
            return "Change to Delivered successfully";
        }
        SellOrderEntity sellOrderEntity = sellOrderRepository.findBySellOrderCodeIs(statusDTO.getInvoiceCode());
        if(sellOrderEntity != null){
            sellOrderEntity.setStatus("Đã giao hàng");
            sellOrderRepository.save(sellOrderEntity);
        }
        return "Change to Delivered successfully";
    }
}
