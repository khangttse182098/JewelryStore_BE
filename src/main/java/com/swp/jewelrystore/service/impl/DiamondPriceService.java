package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.converter.DateTimeConverter;
import com.swp.jewelrystore.customexception.DiamondException;
import com.swp.jewelrystore.entity.GemEntity;
import com.swp.jewelrystore.entity.GemPriceEntity;
import com.swp.jewelrystore.model.dto.DiamondCriteriaDTO;
import com.swp.jewelrystore.model.dto.DiamondDTO;
import com.swp.jewelrystore.model.dto.GemWithPriceDTO;
import com.swp.jewelrystore.model.response.DiamondResponseDTO;
import com.swp.jewelrystore.model.response.GemDetailResponseDTO;
import com.swp.jewelrystore.repository.GemPriceRepository;
import com.swp.jewelrystore.repository.GemRepository;
import com.swp.jewelrystore.service.IDiamondPriceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
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

    @Override
    public List<DiamondResponseDTO> getDiamondPrice() {
        List<DiamondResponseDTO> result = new ArrayList<>();
        List<GemEntity> listGem = gemRepository.findAll();
        for (GemEntity gem : listGem) {
            GemPriceEntity gemPrice = gemPriceRepository.findLatestGemPrice(gem);
            DiamondResponseDTO diamond = modelMapper.map(gemPrice, DiamondResponseDTO.class);
            diamond.setId(gem.getId());
            diamond.setName(gem.getGemName());
            diamond.setEffectDate(dateTimeConverter.convertToDateTimeResponse(gemPrice.getEffectDate()));
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
    public GemDetailResponseDTO getGemDetail(Long id) {
        GemEntity gemEntity = gemRepository.findGemEntityById(id);
        GemDetailResponseDTO gemDetail = modelMapper.map(gemEntity, GemDetailResponseDTO.class);
        GemPriceEntity gemPrice = gemPriceRepository.findLatestGemPrice(gemEntity);
        gemDetail.setEffectDate(dateTimeConverter.convertToDateTimeResponse(gemPrice.getEffectDate()));
        gemDetail.setBuyPrice(gemPrice.getBuyPrice());
        gemDetail.setSellPrice(gemPrice.getSellPrice());
        return gemDetail;
    }

    @Override
    public List<DiamondResponseDTO> getHistoryGemPrice(Long id) {
        List<DiamondResponseDTO> result = new ArrayList<>();
        GemEntity gemEntity = gemRepository.findGemEntityById(id);
        DiamondCriteriaDTO diamondCriteria = modelMapper.map(gemEntity, DiamondCriteriaDTO.class);
        List<GemPriceEntity> listGemPrice = gemPriceRepository.findAllGemPriceHistory(diamondCriteria);
        for (GemPriceEntity item: listGemPrice){
            DiamondResponseDTO diamondResponseDTO = new DiamondResponseDTO();
            diamondResponseDTO.setId(id);
            diamondResponseDTO.setName(gemEntity.getGemName());
            diamondResponseDTO.setSellPrice(item.getSellPrice());
            diamondResponseDTO.setBuyPrice(item.getBuyPrice());
            diamondResponseDTO.setEffectDate(dateTimeConverter.convertToDateTimeResponse(item.getEffectDate()));
            result.add(diamondResponseDTO);
        }
        return result;
    }

    @Override
    public String addDiamondEntity(DiamondDTO diamondDTO) {
          GemEntity existedGemEntity = gemRepository.findByGemName(diamondDTO.getGemName().trim());
           if (existedGemEntity != null){
              throw new DiamondException("Tên kim cương đã tồn tại ! Vui lòng thử lại");
          }
          // add information for gem
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
          return "";
    }
}
