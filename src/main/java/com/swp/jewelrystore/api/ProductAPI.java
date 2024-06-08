package com.swp.jewelrystore.api;


import com.swp.jewelrystore.model.dto.ProductDTO;
import com.swp.jewelrystore.model.response.ProductResponseDTO;
import com.swp.jewelrystore.service.IProductService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin
public class ProductAPI {
    @Autowired
    private IProductService productService;
    @ApiImplicitParams({
            @ApiImplicitParam(name = "counter_id",  required = false, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "category_name", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "product_name", required = false, dataType = "string", paramType = "query")
    })
    @GetMapping
    public List<ProductResponseDTO> getListProduct(@RequestParam Map <String, String> params ){
        List<ProductResponseDTO> result = productService.getAllProduct(params);
        return result;
    }

    @PostMapping
    public String addOrUpdateProduct(@RequestBody ProductDTO productDTO){
        productService.addOrUpdateProduct(productDTO);
        if(productDTO.getId() == null) return "Add product successfully";
        return "Update product successfully";
    }

    @DeleteMapping("/{ids}")
    public String deleteProduct(@PathVariable List<Long> ids){
        productService.deleteByIdsIn(ids);
        return "Delete product successfully";
    }





}
