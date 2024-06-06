package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.converter.DateTimeConverter;
import com.swp.jewelrystore.entity.DiscountEntity;
import com.swp.jewelrystore.model.dto.DiscountDTO;
import com.swp.jewelrystore.model.response.DiscountResponseDTO;
import com.swp.jewelrystore.repository.DiscountRepository;
import com.swp.jewelrystore.service.IDiscountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiscountService implements IDiscountService {

    private final DiscountRepository discountRepository;
    private final ModelMapper modelMapper;
    private final DateTimeConverter dateTimeConverter;

    @Override
    public List<DiscountResponseDTO> getDiscountInformation() {
        List<DiscountResponseDTO> result = new ArrayList<>();
        List<DiscountEntity> listDiscount = discountRepository.findAll();
        // get current date
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentDate = myDateObj.format(myFormatObj);
        System.out.println(currentDate);
         for (DiscountEntity item : listDiscount) {
             DiscountResponseDTO responseDTO = modelMapper.map(item, DiscountResponseDTO.class);
             String startDate = item.getStartDate().toString();
             System.out.println("Start date: " + startDate);
             String endDate = item.getEndDate().toString();
             System.out.println("End date: " + endDate);
             if (startDate.compareTo(currentDate) < 0 && endDate.compareTo(currentDate) > 0) {
                 responseDTO.setStatus("Đang áp dụng");
             } else {
                 responseDTO.setStatus("Chưa áp dụng");
             }
             responseDTO.setStartDate(dateTimeConverter.convertToDateTimeResponse(startDate));
             responseDTO.setEndDate(dateTimeConverter.convertToDateTimeResponse(endDate));
             result.add(responseDTO);
         }
         return result;
    }

    @Override
    public void addDiscountInformation(DiscountDTO discountDTO) {
         DiscountEntity discountEntity = new DiscountEntity();
         discountEntity.setCode(discountDTO.getCode());
         discountEntity.setValue(discountDTO.getValue());
         discountEntity.setStartDate(dateTimeConverter.convertToDateTimeDTO(discountDTO.getStartDateDTO()));
         discountEntity.setEndDate(dateTimeConverter.convertToDateTimeDTO(discountDTO.getEndDateDTO()));
         discountRepository.save(discountEntity);
    }
}
