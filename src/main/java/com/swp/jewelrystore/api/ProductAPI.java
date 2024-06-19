package com.swp.jewelrystore.api;


import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.model.dto.ProductDTO;
import com.swp.jewelrystore.model.request.ProductSearchRequestDTO;
import com.swp.jewelrystore.model.response.ProductResponseDTO;
import com.swp.jewelrystore.repository.ProductRepository;
import com.swp.jewelrystore.repository.PurchaseOrderDetailRepository;
import com.swp.jewelrystore.repository.SellOrderDetailRepository;
import com.swp.jewelrystore.service.IProductService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin
public class ProductAPI {
    @Autowired
    private IProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PurchaseOrderDetailRepository purchaseOrderDetailRepository;
    @Autowired
    private SellOrderDetailRepository sellOrderDetailRepository;
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "counter_id", value = "1", dataType = "Long", paramType = "query"),
//            @ApiImplicitParam(name = "category_name", value = "Trang sức", dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "product_name", value = "Nhẫn Kim cương Vàng trắng 14K",dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "product_code", value = "NKCVT14K", dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "is_available", value = "true", dataType = "string", paramType = "query")
//    })
    @GetMapping
    public List<ProductResponseDTO> getListProduct(@ModelAttribute ProductSearchRequestDTO productSearchRequestDTO){
        List<ProductResponseDTO> result = productService.getAllProduct(productSearchRequestDTO);
        return result;
    }

    @PostMapping
    public String addOrUpdateProduct(@Valid @RequestBody ProductDTO productDTO){
        productService.addOrUpdateProduct(productDTO);
        if(productDTO.getId() == null) return "Add product successfully";
        return "Update product successfully";
    }

    @DeleteMapping()
    public String deleteProduct(@RequestBody List<Long> ids){
        List<ProductEntity> productEntities = productRepository.findByIdIsIn(ids);
        if(purchaseOrderDetailRepository.findPurchaseOrderDetailEntitiesByProductIsIn(productEntities).isEmpty() && sellOrderDetailRepository.findSellOrderDetailEntitiesByProductIsIn(productEntities).isEmpty()){
            productService.deleteByIdsIn(ids);
            return "Delete product successfully";
        }
        return "Cannot delete product already existed in invoices!";
    }

}
