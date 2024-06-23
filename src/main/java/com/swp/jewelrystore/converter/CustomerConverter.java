package com.swp.jewelrystore.converter;

import com.swp.jewelrystore.entity.CustomerEntity;
import com.swp.jewelrystore.entity.SellOrderDetailEntity;
import com.swp.jewelrystore.entity.SellOrderEntity;
import com.swp.jewelrystore.model.response.CustomerResponseDTO;
import com.swp.jewelrystore.repository.CustomerRepository;
import com.swp.jewelrystore.repository.SellOrderDetailRepository;
import com.swp.jewelrystore.repository.SellOrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerConverter {
    private final ModelMapper modelMapper;
    @Autowired
    private SellOrderRepository sellOrderRepository;
    private final SellOrderDetailRepository sellOrderDetailRepository;

    public List<CustomerResponseDTO> convertCustomerToCustomerResponseDTO(List<CustomerEntity> listCustomer){
        List<CustomerResponseDTO> result = new ArrayList<>();
        for (CustomerEntity item: listCustomer){
            CustomerResponseDTO customerResponse = modelMapper.map(item, CustomerResponseDTO.class);
            // quantity order
            List<SellOrderEntity> listPaidSellOrder = sellOrderRepository.
                    findByCustomer_IdAndStatusNot(item.getId(), "Chưa thanh toán");
            customerResponse.setQuantityOrder(listPaidSellOrder.size());
            // expense
            List<SellOrderDetailEntity> listPaidSellOrderDetail =
                    sellOrderDetailRepository.findAllBySellOrderIn(listPaidSellOrder);
            double expense = listPaidSellOrderDetail.stream()
                    .mapToDouble(SellOrderDetailEntity::getPrice)
                    .sum();
            customerResponse.setExpense(expense);
            result.add(customerResponse);
        }

        return result;
    }

}
