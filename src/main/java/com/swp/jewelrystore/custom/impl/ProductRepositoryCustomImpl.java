package com.swp.jewelrystore.custom.impl;

import com.swp.jewelrystore.custom.ProductRepositoryCustom;
import com.swp.jewelrystore.entity.*;
import com.swp.jewelrystore.enums.PurchaseDiscountRate;
import com.swp.jewelrystore.model.request.ProductSearchRequestDTO;
import com.swp.jewelrystore.model.response.PurchasePriceResponseDTO;
import com.swp.jewelrystore.repository.GemPriceRepository;
import com.swp.jewelrystore.repository.MaterialPriceRepository;
import com.swp.jewelrystore.repository.SellOrderDetailRepository;
import com.swp.jewelrystore.repository.SellOrderRepository;
import com.swp.jewelrystore.utils.NumberUtils;
import com.swp.jewelrystore.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MaterialPriceRepository materialPriceRepository;

    @Autowired
    private GemPriceRepository gemPriceRepository;

    @Autowired
    private SellOrderDetailRepository sellOrderDetailRepository;

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
        SellOrderDetailEntity sellOrderDetailEntity = sellOrderDetailRepository.findByProductId(productEntity.getId());
        double discountPrice =  (sellOrderDetailEntity.getPrice() - buyPrice) * PurchaseDiscountRate.PURCHASE_DISCOUNT_RATE.getValue();

        return Math.ceil((buyPrice + discountPrice) / 10.0) * 10;
    }

    @Override
    public double calculatePurchaseDiscountPrice(ProductEntity productEntity) {
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
        SellOrderDetailEntity sellOrderDetailEntity = sellOrderDetailRepository.findByProductId(productEntity.getId());
        double discountPrice =  (sellOrderDetailEntity.getPrice() - buyPrice) * PurchaseDiscountRate.PURCHASE_DISCOUNT_RATE.getValue();
        return discountPrice;
    }

    // getProduct
    public void queryWhereNormal(StringBuilder where, ProductSearchRequestDTO productSearchRequestDTO) {
        try {
            // reflection
            Field[] fields = ProductSearchRequestDTO.class.getDeclaredFields();
            for(Field item : fields) {
                item.setAccessible(true);
                String fieldName = item.getName();
                if(fieldName.equals("product_name") || fieldName.equals("product_code") || fieldName.equals("counter_id")) {
                    Object value = item.get(productSearchRequestDTO);
                    if(value != null) {
                        if(StringUtils.check(value.toString())) {
                            if(item.getType().getName().equals("java.lang.Long") ) {
                                where.append(" AND product." + fieldName + " = " + value);
                            }else if(item.getType().getName().equals("java.lang.Integer") ) {
                                where.append(" AND product." + fieldName + " = " + value);
                            }
                            else if(item.getType().getName().equals("java.lang.String") ) {
                                where.append(" AND product." + fieldName+ " Like '%" + value + "%'");
                            }
                        }
                    }

                }
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public void queryWhereSpecial(StringBuilder where, ProductSearchRequestDTO productSearchRequestDTO) {
        String isAvailable = productSearchRequestDTO.getIs_available();
        if (StringUtils.check(isAvailable)) {
            where.append(" AND sellorderdetail.sell_order_detail_id IS NULL ") ;
        }
        String categoryName = productSearchRequestDTO.getCategory_name();
        if (StringUtils.check(categoryName)) {
            where.append(" AND productcategory.category_name LIKE '%" +categoryName+"%' ") ;
        }
        Long materialId = productSearchRequestDTO.getMaterial_id();
        if(materialId != null) {
            where.append(" AND material.material_id = " + materialId) ;
        }
        Long gemId = productSearchRequestDTO.getGem_id();
        if(gemId != null) {
            where.append(" AND gem.gem_id = " + gemId) ;
        }
        String origin = productSearchRequestDTO.getOrigin();
        if (StringUtils.check(origin)) {
            where.append(" AND gem.origin LIKE '%" +origin+"%' ");
        }
        String cut = productSearchRequestDTO.getCut();
        if (StringUtils.check(cut)) {
            where.append(" AND gem.cut LIKE '%" +cut+"%' ");
        }
        Double caratWeight = productSearchRequestDTO.getCarat_weight();
        if (caratWeight != null) {
            where.append(" AND gem.carat_weight = " +caratWeight+" ");
        }
        String color = productSearchRequestDTO.getColor();
        if (StringUtils.check(color)) {
            where.append(" AND gem.color LIKE '%" +color+"%' ");
        }
        String clarity = productSearchRequestDTO.getClarity();
        if (StringUtils.check(clarity)) {
            where.append(" AND gem.clarity LIKE '%" +clarity+"%' ");
        }
    }
    public void queryJoin(StringBuilder sql, ProductSearchRequestDTO productSearchRequestDTO) {
        if(StringUtils.check(productSearchRequestDTO.getCategory_name())){
            sql.append("JOIN productcategory ON product.product_category_id = productcategory.category_id ");
        }
        if(StringUtils.check(productSearchRequestDTO.getIs_available())){
            sql.append("LEFT JOIN sellorderdetail ON product.product_id = sellorderdetail.product_id ");
        }
        if(productSearchRequestDTO.getMaterial_id() != null){
            sql.append("JOIN productmaterial ON product.product_id = productmaterial.product_id JOIN material ON productmaterial.material_id = material.material_id");
        }
        if(productSearchRequestDTO.getGem_id() != null || StringUtils.check(productSearchRequestDTO.getOrigin()) || StringUtils.check(productSearchRequestDTO.getColor()) || StringUtils.check(productSearchRequestDTO.getClarity()) || StringUtils.check(productSearchRequestDTO.getCut()) || productSearchRequestDTO.getCarat_weight() != null){
            sql.append("JOIN productgem ON product.product_id = productgem.product_id JOIN gem ON productgem.gem_id = gem.gem_id");
        }

    }
    @Override
    public List<ProductEntity>getAllProduct(ProductSearchRequestDTO productSearchRequestDTO) {
        // sql
        StringBuilder sql = new StringBuilder("SELECT product.* FROM product ") ;
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        String groupby = " GROUP BY product.product_id";
        queryJoin(sql,productSearchRequestDTO);
        queryWhereNormal(where, productSearchRequestDTO);
        queryWhereSpecial(where,productSearchRequestDTO);
        sql.append(where);
        sql.append(groupby);
        System.out.println(sql.toString());
        Query query = entityManager.createNativeQuery(sql.toString(), ProductEntity.class);
        return query.getResultList();
    }
}




















