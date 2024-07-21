package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.converter.DateTimeConverter;
import com.swp.jewelrystore.customexception.DateException;
import com.swp.jewelrystore.customexception.DiscountException;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class DiscountService implements IDiscountService {

    private final DiscountRepository discountRepository;
    private final ModelMapper modelMapper;
    private final DateTimeConverter dateTimeConverter;

    @Override
    public List<DiscountResponseDTO> getDiscountInformation(Map<String, String> filter) {
        List<DiscountResponseDTO> result = new ArrayList<>();
         List<DiscountEntity> listDiscount = discountRepository.searchWithRequired(filter);
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
                 responseDTO.setStatus(SystemConstant.APPLYING);
             } else if (startDate.compareTo(currentDate) > 0) {
                 responseDTO.setStatus(SystemConstant.NOT_APPLY_YET);
             } else if (endDate.compareTo(currentDate) < 0) {
                 responseDTO.setStatus(SystemConstant.STOP_APPLY);
             }
             responseDTO.setStartDate(dateTimeConverter.convertToDateTimeResponse(item.getStartDate()));
             responseDTO.setEndDate(dateTimeConverter.convertToDateTimeResponse(item.getEndDate()));
             result.add(responseDTO);
         }
         return result;
    }

    @Override
    public void addOrUpdateDiscountInformation(DiscountDTO discountDTO) {
         DiscountEntity discountEntity = new DiscountEntity();
         if(discountDTO.getId() != null && discountDTO.getId() > 0){
             discountEntity.setId(discountDTO.getId());
         }
         if(discountRepository.findByCode(discountDTO.getCode()) != null && discountDTO.getId() == null){
             throw new DiscountException(discountDTO.getCode() + " đã tồn tại!");
         }
         discountEntity.setCode(discountDTO.getCode());
         discountEntity.setValue(discountDTO.getValue());
         Date startDate = dateTimeConverter.convertToDateTimeDTO(discountDTO.getStartDateDTO());
         Date endDate = dateTimeConverter.convertToDateTimeDTO(discountDTO.getEndDateDTO());
         DiscountEntity applyingDiscount = discountRepository.findApplyingDiscount();
         if (!endDate.after(startDate)){
             throw new DateException("Ngày kết thúc phải lớn hơn ngày bắt đầu!");
         } else if (!startDate.after(applyingDiscount.getEndDate())) {
             throw new DiscountException("Chương trình khuyến mãi hiện tại có hiệu lực đến ngày " + dateTimeConverter.convertToDateTimeResponse(applyingDiscount.getEndDate()) +" !");
         }
         discountEntity.setStartDate(dateTimeConverter.convertToDateTimeDTO(discountDTO.getStartDateDTO()));
         discountEntity.setEndDate(dateTimeConverter.convertToDateTimeDTO(discountDTO.getEndDateDTO()));
         discountRepository.save(discountEntity);
    }

    @Override
    public void deleteDiscountById(List<Long> ids) {
        List<DiscountEntity> listDiscount = discountRepository.findAllById(ids);
        discountRepository.deleteAll(listDiscount);
    }


}
