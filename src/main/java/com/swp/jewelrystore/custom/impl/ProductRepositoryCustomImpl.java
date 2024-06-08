package com.swp.jewelrystore.custom.impl;

import com.swp.jewelrystore.custom.ProductRepositoryCustom;
import com.swp.jewelrystore.entity.MaterialPriceEntity;
import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.entity.ProductGemEntity;
import com.swp.jewelrystore.entity.ProductMaterialEntity;
import com.swp.jewelrystore.model.response.PurchasePriceResponseDTO;
import com.swp.jewelrystore.repository.GemPriceRepository;
import com.swp.jewelrystore.repository.MaterialPriceRepository;
import com.swp.jewelrystore.utils.NumberUtils;
import com.swp.jewelrystore.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MaterialPriceRepository materialPriceRepository;

    @Autowired
    private GemPriceRepository gemPriceRepository;

    @Override
    public double calculateSellPrice(ProductEntity productEntity) {
        // Giá vốn sản phẩm = [giá vàng thời điểm * trọng lượng sản phẩm]
        // + tiền công + tiền đá
        double primePrice = 0;
        // tinh tien vang neu co
        if(!productEntity.getProductMaterialEntities().isEmpty()){
            List<ProductMaterialEntity> productMaterialEntities = productEntity.getProductMaterialEntities();
            for(ProductMaterialEntity productMaterialEntity : productMaterialEntities){
                primePrice += (productMaterialEntity.getWeight() * 0.267 * materialPriceRepository.findLatestGoldPrice(productMaterialEntity.getMaterial()).getSellPrice());
            }
        }
        // tinh tien kim cuong neu co
        if(!productEntity.getProductGemEntities().isEmpty()){
            List<ProductGemEntity> productGemEntities = productEntity.getProductGemEntities();
            for(ProductGemEntity productGemEntity : productGemEntities) {
                primePrice += gemPriceRepository.findLatestGemPrice(productGemEntity.getGem()).getSellPrice();
            }
        }
        // tinh tien nhan cong, da, vat lieu
        primePrice += productEntity.getMaterialCost() + productEntity.getGemCost() + productEntity.getProductionCost();
        // Giá bán = giá vốn sản phẩm * tỉ lệ áp giá,
        double sellPrice = primePrice * productEntity.getPriceRate();
        // Làm tron gia ban
        sellPrice = Math.ceil(sellPrice / 10.0) * 10;
        return sellPrice;
    }

    @Override
    public double calculateBuyPrice(ProductEntity productEntity) {
        double buyPrice = 0;
        // tinh tien material neu co
        if(!productEntity.getProductMaterialEntities().isEmpty()){
            List<ProductMaterialEntity> productMaterialEntities = productEntity.getProductMaterialEntities();
            for(ProductMaterialEntity productMaterialEntity : productMaterialEntities){
                buyPrice += (productMaterialEntity.getWeight() * 0.267 * materialPriceRepository.findLatestGoldPrice(productMaterialEntity.getMaterial()).getBuyPrice());
            }
        }
        // tinh tien kim cuong neu co
        if(!productEntity.getProductGemEntities().isEmpty()){
            List<ProductGemEntity> productGemEntities = productEntity.getProductGemEntities();
            for(ProductGemEntity productGemEntity : productGemEntities) {
                buyPrice += gemPriceRepository.findLatestGemPrice(productGemEntity.getGem()).getBuyPrice();
            }
        }
        return Math.ceil((buyPrice * 1.2) / 10.0) * 10;  // uu dai 20%);
    }

    @Override
    public List<ProductEntity>getAllProduct(Map<String, String> params) {
        // sql
        StringBuilder sql = new StringBuilder("SELECT product.* FROM product ") ;
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        String groupby = " GROUP BY product.product_id";
        if(params.containsKey("invoiceCode")){
            if(params.get("invoiceCode").trim().startsWith("PURC")){
                sql.append("JOIN purchaseorderdetail ON product.product_id = purchaseorderdetail.product_id JOIN purchaseorder ON purchaseorderdetail.purchase_order_id = purchaseorder.purchase_order_id");
                where.append(" AND purchase_order_code LIKE '%"+ params.get("invoiceCode") +"%'");
            }else if(params.get("invoiceCode").trim().startsWith("SELL")){
                sql.append("JOIN sellorderdetail ON product.product_id = sellorderdetail.product_id JOIN sellorder ON sellorderdetail.sell_order_id = sellorder.sell_order_id");
                where.append(" AND sell_order_code LIKE '%"+params.get("invoiceCode")+"%' " );
            }
        }
        if(params.containsKey("category_name")){
            sql.append("JOIN productcategory ON product.product_category_id = productcategory.category_id ");
        }
        sql.append(where);
        for(Map.Entry<String, String> param : params.entrySet()){

            if(NumberUtils.isLong(param.getValue())){
                sql.append(" AND " + param.getKey() + " = " + param.getValue().trim());
            }else if(StringUtils.check(param.getValue()) && !param.getKey().equals("invoiceCode")){
                sql.append(" AND " + param.getKey() + " LIKE '%" + param.getValue().trim() + "%'");
            }
        }
        sql.append(groupby);
        System.out.println(sql.toString());
        Query query = entityManager.createNativeQuery(sql.toString(), ProductEntity.class);
        return query.getResultList();
    }
}




















