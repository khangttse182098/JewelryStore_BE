package com.swp.jewelrystore.custom.impl;

import com.swp.jewelrystore.custom.GemPriceRepositoryCustom;
import com.swp.jewelrystore.entity.GemEntity;
import com.swp.jewelrystore.entity.GemPriceEntity;
import com.swp.jewelrystore.entity.PurchaseOrderEntity;
import com.swp.jewelrystore.model.dto.DiamondCriteriaDTO;
import com.swp.jewelrystore.model.dto.DiamondDTO;
import com.swp.jewelrystore.model.dto.GemKeyDTO;
import com.swp.jewelrystore.model.response.GemPriceResponseDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Repository
public class GemPriceRepositoryCustomImpl implements GemPriceRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public GemPriceEntity findLatestGemPrice(GemEntity gemEntity) {
        String sql = buildQueryFilter(gemEntity);
        Query query = entityManager.createNativeQuery(sql,GemPriceEntity.class);
        List<GemPriceEntity> gemPriceEntities = query.getResultList();
        if (!gemPriceEntities.isEmpty()) {
            return gemPriceEntities.get(0);
        }
        return null;
    }

    @Override
    public GemPriceEntity checkGemCaratInRange(DiamondCriteriaDTO diamondCriteriaDTO) {
        String sql = buildQueryForCheckGem(diamondCriteriaDTO);
        Query query = entityManager.createNativeQuery(sql,GemPriceEntity.class);
        List<GemPriceEntity> gemPriceEntities = query.getResultList();
        if (!gemPriceEntities.isEmpty()) {
            return gemPriceEntities.get(0);
        }
        return null;
    }

    @Override
    public List<GemPriceEntity> findAllGemPriceHistory(GemKeyDTO gemKey) {
        String sql = buildQueryForGetAllGemPrice(gemKey);
        Query query = entityManager.createNativeQuery(sql,GemPriceEntity.class);
        return query.getResultList();
    }

    @Override
    public List<GemPriceEntity> getGemExistedWithoutDate(DiamondCriteriaDTO diamondCriteriaDTO) {
        String sql = buildQueryForCheckGemWithoutDate(diamondCriteriaDTO);
        Query query = entityManager.createNativeQuery(sql,GemPriceEntity.class);
        List<GemPriceEntity> result = query.getResultList();
        return result;
    }


    private String buildQueryForCheckGemWithoutDate(DiamondCriteriaDTO diamondCriteriaDTO){
        String sql = "select gemprice.* from gemprice where origin = '"
                + diamondCriteriaDTO.getOrigin() +"' and color = '"
                + diamondCriteriaDTO.getColor()+ "' and clarity = '"
                + diamondCriteriaDTO.getClarity() + "' and ( carat_weight_from <= "
                + diamondCriteriaDTO.getCaratWeight()+ " and "
                + diamondCriteriaDTO.getCaratWeight() + " <= carat_weight_to ) and cut = '" +
                diamondCriteriaDTO.getCut()+ "'";
        System.out.println(sql);
        return sql;
    }


    private String buildQueryForCheckGem(DiamondCriteriaDTO diamondCriteriaDTO){
        String sql = "select gemprice.* from gemprice where origin = '"
                + diamondCriteriaDTO.getOrigin() +"' and color = '"
                + diamondCriteriaDTO.getColor()+ "' and clarity = '"
                + diamondCriteriaDTO.getClarity() + "' and ( carat_weight_from <= "
                + diamondCriteriaDTO.getCaratWeight()+ " and "
                + diamondCriteriaDTO.getCaratWeight() + " <= carat_weight_to ) and cut = '" +
                diamondCriteriaDTO.getCut()+ "'" + " and effect_date <= now() order by effect_date DESC, gem_price_id DESC limit 1";
        System.out.println(sql);
        return sql;
    }

    private String buildQueryForGetAllGemPrice(GemKeyDTO gemKey){
        String sql = "select gemprice.* from gemprice where origin = '"
                + gemKey.getOrigin() +"' and color = '"
                + gemKey.getColor()+ "' and clarity = '"
                + gemKey.getClarity() + "' and carat_weight_from = '"
                + gemKey.getCaratWeightFrom()+ "' and carat_weight_to = '"
                + gemKey.getCaratWeightTo() + "' and cut = '" +
                gemKey.getCut()+ "'";
        System.out.println(sql);
        return sql;
    }

    private String buildQueryFilter(GemEntity gemEntity) {
        String sql = "select gemprice.* from gemprice where origin = '" + gemEntity.getOrigin() +"' and color = '" + gemEntity.getColor()+ "' and clarity = '" + gemEntity.getClarity() + "' and ( carat_weight_from <= " + gemEntity.getCaratWeight()+ " and " + gemEntity.getCaratWeight() + " <= carat_weight_to ) and cut = '" + gemEntity.getCut()+"' and effect_date <= now() order by effect_date DESC, gem_price_id DESC limit 1";
        return sql;
    }

    @Override
    public String autoGenerateCode(){
        String diamondCode = "DIA";
        int number = countTotalDiamond() + 1;
        diamondCode += String.valueOf(number);
        return diamondCode;
    }

    @Override
    public List<GemPriceResponseDTO> getGemDistinct() {
        String sql = buildQueryForGetDistinctGem();
        Query query = entityManager.createNativeQuery(sql,GemPriceResponseDTO.class);
        List<GemPriceResponseDTO> result = query.getResultList();
        System.out.println(result);
        return result;
    }

    private String buildQueryForGetDistinctGem(){
        String sql = "SELECT DISTINCT origin, color, clarity, carat_weight_from, carat_weight_to, cut FROM gemprice ";
        return sql;
    }

    @Override
    public GemPriceEntity getGemDistinctInformation(GemPriceResponseDTO gemPriceResponseDTO) {
        String sql = buildQueryToGetDistinctInformation(gemPriceResponseDTO);
        Query query = entityManager.createNativeQuery(sql,GemPriceEntity.class);
        List<GemPriceEntity> gemPriceEntities = query.getResultList();
        if (!gemPriceEntities.isEmpty()) {
            return gemPriceEntities.get(0);
        }
        return null;
    }


    private String buildQueryToGetDistinctInformation(GemPriceResponseDTO gemPriceResponseDTO){
        String sql = "select * from gemprice where origin = '"
                + gemPriceResponseDTO.getId().getOrigin() +"' and color = '"
                + gemPriceResponseDTO.getId().getColor()+ "' and clarity = '"
                + gemPriceResponseDTO.getId().getClarity() + "' and carat_weight_from = '"
                + gemPriceResponseDTO.getId().getCarat_weight_from()+ "' and carat_weight_to = '"
                + gemPriceResponseDTO.getId().getCarat_weight_to() + "' and cut = '" +
                gemPriceResponseDTO.getId().getCut()+ "' and effect_date <= now() order by effect_date DESC limit 1";
        System.out.println(sql);
        return sql;
    }



    private int countTotalDiamond() {
        String sql = "SELECT gem.* FROM gem";
        Query query = entityManager.createNativeQuery(sql, GemEntity.class);
        return query.getResultList().size();
    }
}
