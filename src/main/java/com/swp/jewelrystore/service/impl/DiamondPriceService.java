package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.converter.DateTimeConverter;
import com.swp.jewelrystore.customexception.DiamondException;
import com.swp.jewelrystore.entity.GemEntity;
import com.swp.jewelrystore.entity.GemPriceEntity;
import com.swp.jewelrystore.entity.ProductGemEntity;
import com.swp.jewelrystore.entity.SellOrderDetailEntity;
import com.swp.jewelrystore.model.dto.*;
import com.swp.jewelrystore.model.response.DiamondResponseDTO;
import com.swp.jewelrystore.model.response.GemDetailResponseDTO;
import com.swp.jewelrystore.model.response.GemPriceDistinctResponseDTO;
import com.swp.jewelrystore.model.response.GemPriceResponseDTO;
import com.swp.jewelrystore.repository.*;
import com.swp.jewelrystore.service.IDiamondPriceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiamondPriceService implements IDiamondPriceService {

    private final GemPriceRepository gemPriceRepository;
    private final GemRepository gemRepository;
    private final ModelMapper modelMapper;
    private final DateTimeConverter dateTimeConverter;
    private final ModelMapper modelMapperIgnoreId;
    private final ModelMapper modelMapperIgnoreEffectDate;
    private final ProductGemRepository productGemRepository;
    private final SellOrderDetailRepository sellOrderDetailRepository;

    @Override
    public List<DiamondResponseDTO> getDiamondInformation() {
        List<DiamondResponseDTO> result = new ArrayList<>();
        List<GemEntity> listGem = gemRepository.findAll();
        for (GemEntity gem : listGem) {
            DiamondResponseDTO diamond = modelMapper.map(gem, DiamondResponseDTO.class);
            DiamondCriteriaDTO diamondCriteria = modelMapper.map(diamond, DiamondCriteriaDTO.class);
            List<GemPriceEntity> listGemExistedWithoutDate = gemPriceRepository.getGemExistedWithoutDate(diamondCriteria);
            // tiêu chí 4C + 1O không tồn tại trong bảng gemprice
            if (listGemExistedWithoutDate.isEmpty()){
                diamond.setSellPrice(0.0);
            } else { // đá tồn tại nhưng chưa có ngày
                GemPriceEntity gemPriceEntity = gemPriceRepository.checkGemCaratInRange(diamondCriteria);
                if (gemPriceEntity == null){
                    diamond.setSellPrice("Chưa đến ngày áp dụng");
                } else {
                    diamond.setSellPrice(gemPriceEntity.getSellPrice());
                }
            }
            // kiểm tra xem kim cương đã đính với hóa đơn bán nào hay chưa -> đã đính = 1, ko đính = 0
            List<ProductGemEntity> checkAssignedGemWithProduct = productGemRepository.findAllByGemId(gem.getId());
            // check xem cái gem id tồn tại trong product-gem hay ko
            if (checkAssignedGemWithProduct.isEmpty()){
                diamond.setStatus(0L);
            } else {
                for (ProductGemEntity productGem : checkAssignedGemWithProduct) {
                    List<SellOrderDetailEntity> listSellOrderWithProduct = sellOrderDetailRepository.findAllByProduct(productGem.getProduct());
                    if (listSellOrderWithProduct.isEmpty()){
                        diamond.setStatus(0L);
                    } else {
                        diamond.setStatus(1L);
                    }
                }
            }
            result.add(diamond);
        }
        return result;
    }

    @Override
    public void addOrUpdateDiamondPrice(GemWithPriceDTO gem) {
            GemEntity gemEntity = gemRepository.findById(gem.getGemId()).orElse(null);
            // change diamond name
            gemEntity.setGemName(gem.getDiamondName());
            gemRepository.save(gemEntity);
            DiamondCriteriaDTO diamondCriteria = modelMapper.map(gemEntity, DiamondCriteriaDTO.class);
            GemPriceEntity gemPriceEntity = gemPriceRepository.checkGemCaratInRange(diamondCriteria);
            GemPriceEntity newRecordForGem = modelMapperIgnoreId.map(gemPriceEntity, GemPriceEntity.class);
            newRecordForGem.setBuyPrice(gem.getBuyPrice());
            newRecordForGem.setSellPrice(gem.getSellPrice());
            newRecordForGem.setEffectDate(dateTimeConverter.convertToDateTimeDTO(gem.getEffectDate()));
            gemPriceRepository.save(newRecordForGem);
    }

    @Override
    public GemDetailResponseDTO getDiamondDetail(Long id) {
        GemEntity gemEntity = gemRepository.findGemEntityById(id);
        GemDetailResponseDTO gemDetail = modelMapper.map(gemEntity, GemDetailResponseDTO.class);
        return gemDetail;
    }

    @Override
    public List<GemPriceDistinctResponseDTO> getHistoryGemPrice() {
        List<GemPriceResponseDTO> listDistinctGem = gemPriceRepository.getGemDistinct();
        List<GemPriceDistinctResponseDTO> result = new ArrayList<>();
        for (GemPriceResponseDTO gemPrice : listDistinctGem) {
            GemPriceDistinctResponseDTO gemDistinct = modelMapper.map(gemPrice.getId(), GemPriceDistinctResponseDTO.class);
            GemPriceEntity gemPriceEntity = gemPriceRepository.getGemDistinctInformation(gemPrice);
            // null là do chưa đến ngày áp dụng
            // chưa đến ngày áp dụng thì
            if (gemPriceEntity == null){
               // query to get gem with higher effect date but smallest in the higher
                GemPriceEntity gemPriceEntityWithHigherDate = gemPriceRepository.getGemExistedWithHigherDate(gemPrice);
                gemDistinct.setCaratWeightFrom(gemPriceEntityWithHigherDate.getCaratWeightFrom());
                gemDistinct.setCaratWeightTo(gemPriceEntityWithHigherDate.getCaratWeightTo());
                gemDistinct.setSellPrice(gemPriceEntityWithHigherDate.getSellPrice());
                gemDistinct.setBuyPrice(gemPriceEntityWithHigherDate.getBuyPrice());
                gemDistinct.setEffectDate(dateTimeConverter.convertToDateTimeResponse(gemPriceEntityWithHigherDate.getEffectDate()));
                result.add(gemDistinct);
            } else {
                gemDistinct.setCaratWeightFrom(gemPriceEntity.getCaratWeightFrom());
                gemDistinct.setCaratWeightTo(gemPriceEntity.getCaratWeightTo());
                gemDistinct.setSellPrice(gemPriceEntity.getSellPrice());
                gemDistinct.setBuyPrice(gemPriceEntity.getBuyPrice());
                gemDistinct.setEffectDate(dateTimeConverter.convertToDateTimeResponse(gemPriceEntity.getEffectDate()));
                result.add(gemDistinct);
            }

        }
        return result;
    }

    @Override
    public String addOrUpdateDiamondEntity(DiamondDTO diamondDTO) {
        List<GemEntity> listGem = gemRepository.findAll();
          // add information for gem
           if (diamondDTO.getId() == null){
               GemEntity existedGemEntity = gemRepository.findByGemName(diamondDTO.getGemName().trim());
               if (existedGemEntity != null){
                   throw new DiamondException("Tên kim cương đã tồn tại ! Vui lòng thử lại");
               }
               GemEntity gemEntity = modelMapper.map(diamondDTO, GemEntity.class);
               gemEntity.setGemCode(gemPriceRepository.autoGenerateCode());
               gemEntity.setActive(1L);
               gemRepository.save(gemEntity);
               DiamondCriteriaDTO diamondCriteriaDTO = modelMapper.map(gemEntity, DiamondCriteriaDTO.class);
               GemPriceEntity existedGemPriceEntity = gemPriceRepository.checkGemCaratInRange(diamondCriteriaDTO);
               // existed
               if (existedGemPriceEntity == null){
                   return "Kim cương chưa có giá !";
               }
               return "Thêm kim cương thành công";
           } else { // update information for product gem
               // check whether diamond is assigned to sell order or not
               List<ProductGemEntity> listAssignedGemWithProduct = productGemRepository.findAllByGemId(diamondDTO.getId());
               // gem nằm trong sản phẩm thì phải check xem sản phẩm đinh kèm có nằm trong hóa đơn bán hay ko
               // nếu gem ko nằm trong sản phẩm thì auto sửa được
               if (!listAssignedGemWithProduct.isEmpty()){
                   for (ProductGemEntity productGem : listAssignedGemWithProduct) {
                       List<SellOrderDetailEntity> listSellOrderWithProduct = sellOrderDetailRepository.findAllByProduct(productGem.getProduct());
                       if (!listSellOrderWithProduct.isEmpty()){
                           throw new DiamondException("Kim cương đã được đính kèm với sản phẩm hóa đơn bán, ko thể xóa");
                       }
                   }
               }
               for (GemEntity gem : listGem) {
                   if (gem.getGemName().trim().equals(diamondDTO.getGemName().trim()) && gem.getId() != diamondDTO.getId()){
                       throw new DiamondException("Tên kim cương đã tồn tại ! Vui lòng thử lại");
                   }
               }
               GemEntity updateGemEntity = modelMapper.map(diamondDTO, GemEntity.class);
               updateGemEntity.setGemCode(gemRepository.findById(diamondDTO.getId()).get().getGemCode());
               updateGemEntity.setActive(1L);
               gemRepository.save(updateGemEntity);
               return "Cập nhật thông tin mới cho kim cương thành công";
           }

    }

    @Override
    public void addNewPriceForDiamondEntity(GemPriceDTO gemPriceDTO) {
        GemPriceEntity gemPrice = modelMapperIgnoreEffectDate.map(gemPriceDTO, GemPriceEntity.class);
        gemPrice.setEffectDate(dateTimeConverter.convertToDateTimeDTO(gemPriceDTO.getEffectDate()));
        // thêm try catch cho lỗi quá ngày hiện tại
        gemPriceRepository.save(gemPrice);
    }

    @Override
    public List<GemPriceDistinctResponseDTO> getHistoryGemPriceDetail(GemKeyDTO gemKey) {
        List<GemPriceEntity> listGemPrice = gemPriceRepository.findAllGemPriceHistory(gemKey);
        List<GemPriceDistinctResponseDTO> result = new ArrayList<>();
        for (GemPriceEntity gem : listGemPrice) {
            GemPriceDistinctResponseDTO gemDistinct = modelMapper.map(gem, GemPriceDistinctResponseDTO.class);
            gemDistinct.setEffectDate(dateTimeConverter.convertToDateTimeResponse(gem.getEffectDate()));
            result.add(gemDistinct);
        }
        Collections.reverse(result);
        return result;
    }
}
