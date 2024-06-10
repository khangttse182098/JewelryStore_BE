package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.entity.*;
import com.swp.jewelrystore.model.dto.*;
import com.swp.jewelrystore.model.response.*;
import com.swp.jewelrystore.repository.*;
import com.swp.jewelrystore.service.IPurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseOrderService implements IPurchaseOrderService {

    private final GemRepository gemRepository;
    private final MaterialPriceRepository materialPriceRepository;
    private final GemPriceRepository gemPriceRepository;
    private final MaterialRepository materialRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderDetailRepository purchaseOrderDetailRepository;
    private final ProductRepository productRepository;
    private final SellOrderRepository sellOrderRepository;
    private final ModelMapper modelMapper;

    @Override
    public void addProductPurchaseOrder(PurchaseInvoiceDTO purchaseInvoiceDTO) {
        SellOrderEntity sellOrderEntity = sellOrderRepository.findBySellOrderCodeIs(purchaseInvoiceDTO.getSellOrderCode());
        PurchaseOrderEntity purchaseOrderEntity = new PurchaseOrderEntity();
        purchaseOrderEntity.setPurchaseOrderCode(purchaseOrderRepository.generatePurchaseOrderCode());
        purchaseOrderEntity.setUser(userRepository.findByIdIs(purchaseInvoiceDTO.getUserId()));
        purchaseOrderEntity.setStatus(SystemConstant.UNPAID);
        purchaseOrderEntity.setCustomer(sellOrderEntity.getCustomer());
        purchaseOrderRepository.save(purchaseOrderEntity);
        for (Long id : purchaseInvoiceDTO.getProductId()) {
            PurchaseOrderDetailEntity purchaseOrderDetailEntity = new PurchaseOrderDetailEntity();
            ProductEntity productEntity = productRepository.findByIdIs(id);
            purchaseOrderDetailEntity.setPurchaseOrder(purchaseOrderEntity);
            purchaseOrderDetailEntity.setProduct(productEntity);
            purchaseOrderDetailEntity.setPrice(productRepository.calculateBuyPrice(productEntity));
            purchaseOrderDetailRepository.save(purchaseOrderDetailEntity);
        }
    }

    @Override
    public List<PurchasePriceResponseDTO> showPurchasePrice(List<Long> productIds) {
        List<PurchasePriceResponseDTO> purchasePriceResponseDTOList = new ArrayList<>();
        for (Long productId : productIds) {
            ProductEntity productEntity = productRepository.findByIdIs(productId);
            PurchasePriceResponseDTO purchasePriceResponseDTO = new PurchasePriceResponseDTO();
            purchasePriceResponseDTO.setProductId(productId);
            purchasePriceResponseDTO.setPurchasePrice(productRepository.calculateBuyPrice(productEntity));
            purchasePriceResponseDTOList.add(purchasePriceResponseDTO);
        }
        return purchasePriceResponseDTOList;
    }
    @Override
    public CriteriaResponseDTO showMaterialInvoice(CriteriaDTO criteriaDTO) {
        CriteriaResponseDTO listMaterial = new CriteriaResponseDTO();
        List<GoldCriteriaResponseDTO> listGold = new ArrayList<>();
        List<DiamondCriteriaResponseDTO> listDiamond = new ArrayList<>();
        // gold criteria (done)
        for (GoldCriteriaDTO goldCriteria : criteriaDTO.getListGoldCriteria()){
            GoldCriteriaResponseDTO gold = new GoldCriteriaResponseDTO();
            gold.setId(goldCriteria.getId());
            MaterialEntity materialEntity = materialRepository.findById(goldCriteria.getId()).get();
            gold.setWeight(goldCriteria.getWeight());
            gold.setName(materialEntity.getName());
            // set price
            double price = goldCriteria.getWeight() * materialPriceRepository.findLatestGoldPrice(materialEntity).getBuyPrice();
            gold.setPrice(price);
            // add to list gold
            listGold.add(gold);
        }
        // diamond criteria (done)
        for (DiamondCriteriaDTO diamondCriteria : criteriaDTO.getListDiamondCriteria()){
            GemEntity gem = gemRepository.findByOriginAndColorAndClarityAndCaratWeightAndCut(
    diamondCriteria.getOrigin(), diamondCriteria.getColor(), diamondCriteria.getClarity(), diamondCriteria.getCaratWeight(),
                     diamondCriteria.getCut());
            DiamondCriteriaResponseDTO diamond = modelMapper.map(gem, DiamondCriteriaResponseDTO.class);
            // set price
            GemPriceEntity gemPriceEntity = gemPriceRepository.findLatestGemPrice(gem);
            double price = gemPriceEntity.getBuyPrice();
            diamond.setPrice(price);
            // add to listDiamond
            listDiamond.add(diamond);
        }
        listMaterial.setGoldCriteriaResponseDTO(listGold);
        listMaterial.setDiamondCriteriaResponseDTO(listDiamond);
        return listMaterial;
    }


    @Override
    public void addPurchaseInvoiceInformation(PurchaseOrderDTO purchaseOrderDTO) {
        CustomerEntity customer = customerRepository.findByFullNameOrPhoneNumber(purchaseOrderDTO.getFullName(), purchaseOrderDTO.getPhoneNumber());
        UserEntity user = userRepository.findById(purchaseOrderDTO.getUserId()).get();
        if (customer == null){
            CustomerEntity newCustomer = new CustomerEntity();
            newCustomer.setFullName(purchaseOrderDTO.getFullName());
            newCustomer.setPhoneNumber(purchaseOrderDTO.getPhoneNumber());
            newCustomer.setAddress(purchaseOrderDTO.getAddress());
            customerRepository.save(newCustomer);
            PurchaseOrderEntity purchaseOrderEntity = new PurchaseOrderEntity();
            purchaseOrderEntity.setCustomer(newCustomer);
            purchaseOrderEntity.setUser(user);
            purchaseOrderEntity.setPurchaseOrderCode(purchaseOrderRepository.generatePurchaseOrderCode());
            purchaseOrderEntity.setStatus(purchaseOrderDTO.getPurchaseOrderStatus());
            purchaseOrderRepository.save(purchaseOrderEntity);
            splitGoldOrGem(purchaseOrderDTO.getCriteria(), purchaseOrderEntity);
        } else {
            PurchaseOrderEntity purchaseOrderEntity = new PurchaseOrderEntity();
            purchaseOrderEntity.setCustomer(customer);
            purchaseOrderEntity.setUser(user);
            purchaseOrderEntity.setPurchaseOrderCode(purchaseOrderRepository.generatePurchaseOrderCode());
            purchaseOrderEntity.setStatus(purchaseOrderDTO.getPurchaseOrderStatus());
            purchaseOrderRepository.save(purchaseOrderEntity);
            splitGoldOrGem(purchaseOrderDTO.getCriteria(), purchaseOrderEntity);
        }
    }

    public void splitGoldOrGem(CriteriaResponseDTO criteriaResponseDTO, PurchaseOrderEntity purchaseOrderEntity){
        // save information when purchase gold
        for (GoldCriteriaResponseDTO item: criteriaResponseDTO.getGoldCriteriaResponseDTO()){
            MaterialEntity materialEntity = materialRepository.findByName(item.getName());
            PurchaseOrderDetailEntity purchaseOrderDetailEntity = new PurchaseOrderDetailEntity();
            purchaseOrderDetailEntity.setWeight(item.getWeight());
            purchaseOrderDetailEntity.setMaterial(materialEntity);
            purchaseOrderDetailEntity.setPurchaseOrder(purchaseOrderEntity);
            purchaseOrderDetailEntity.setPrice(item.getPrice());
            purchaseOrderDetailRepository.save(purchaseOrderDetailEntity);
        }
        // save information when purchase diamond
        for (DiamondCriteriaResponseDTO item: criteriaResponseDTO.getDiamondCriteriaResponseDTO()){
                GemEntity gemEntity = gemRepository.findByOriginAndColorAndClarityAndCaratWeightAndCut(
                        item.getOrigin(), item.getColor(), item.getClarity(), item.getCaratWeight(), item.getCut()
                );
                PurchaseOrderDetailEntity purchaseOrderDetailEntity = new PurchaseOrderDetailEntity();
                purchaseOrderDetailEntity.setOrigin(gemEntity.getOrigin());
                purchaseOrderDetailEntity.setColor(gemEntity.getColor());
                purchaseOrderDetailEntity.setCut(gemEntity.getCut());
                purchaseOrderDetailEntity.setCaratWeight(gemEntity.getCaratWeight());
                purchaseOrderDetailEntity.setClarity(gemEntity.getClarity());
                purchaseOrderDetailEntity.setPrice(item.getPrice());
                purchaseOrderDetailEntity.setPurchaseOrder(purchaseOrderEntity);
                purchaseOrderDetailRepository.save(purchaseOrderDetailEntity);
        }
    }
}
