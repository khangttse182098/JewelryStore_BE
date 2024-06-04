package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.entity.*;
import com.swp.jewelrystore.model.dto.*;
import com.swp.jewelrystore.model.response.MaterialResponseDTO;
import com.swp.jewelrystore.model.response.PurchasePriceResponseDTO;
import com.swp.jewelrystore.repository.*;
import com.swp.jewelrystore.service.IPurchaseOrderService;
import lombok.RequiredArgsConstructor;
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

    @Autowired
    PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public void addProductPurchaseOrder(PurchaseInvoiceDTO purchaseInvoiceDTO) {
        PurchaseOrderEntity purchaseOrderEntity = new PurchaseOrderEntity();
        purchaseOrderEntity.setPurchaseOrderCode(purchaseOrderRepository.generatePurchaseOrderCode());
        purchaseOrderEntity.setUser(userRepository.findByIdIs(purchaseInvoiceDTO.getUserId()));
        purchaseOrderEntity.setStatus("Chưa thanh toán");
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
    public List<MaterialResponseDTO> addPurchaseOrderInformation(CriteriaDTO criteriaDTO) {
        List<MaterialResponseDTO> listMaterial = new ArrayList<>();
        for (GoldCriteriaDTO goldCriteria : criteriaDTO.getListGoldCriteria()){
            MaterialResponseDTO material = new MaterialResponseDTO();
            MaterialEntity materialEntity = materialRepository.findById(goldCriteria.getId()).get();
            material.setWeight(goldCriteria.getWeight());
            material.setName(materialEntity.getName());
            // set price
            double price = goldCriteria.getWeight() * 0.267 * materialPriceRepository.findLatestGoldPrice(materialEntity).getBuyPrice();
            material.setPrice(price);
            // add to listMaterial
            listMaterial.add(material);
        }
        for (DiamondCriteriaDTO diamondCriteria : criteriaDTO.getListDiamondCriteria()){
            MaterialResponseDTO material = new MaterialResponseDTO();
            GemEntity gem = gemRepository.findByOriginAndColorAndClarityAndCaratWeightAndCut( diamondCriteria.getOrigin(), diamondCriteria.getColor(), diamondCriteria.getClarity(), diamondCriteria.getCaratWeight(), diamondCriteria.getCut());
            material.setName(gem.getGemName());
            material.setWeight(gem.getCaratWeight());
            // set price
            GemPriceEntity gemPriceEntity = gemPriceRepository.findLatestGemPrice(gem);
            double price = gemPriceEntity.getBuyPrice();
            material.setPrice(price);
            // add to listMaterial
            listMaterial.add(material);
        }
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
            splitGoldOrGem(purchaseOrderDTO.getListMaterialResponse(), purchaseOrderEntity);
        } else {
            PurchaseOrderEntity purchaseOrderEntity = new PurchaseOrderEntity();
            purchaseOrderEntity.setCustomer(customer);
            purchaseOrderEntity.setUser(user);
            purchaseOrderEntity.setPurchaseOrderCode(purchaseOrderRepository.generatePurchaseOrderCode());
            purchaseOrderEntity.setStatus(purchaseOrderDTO.getPurchaseOrderStatus());
            purchaseOrderRepository.save(purchaseOrderEntity);
            splitGoldOrGem(purchaseOrderDTO.getListMaterialResponse(), purchaseOrderEntity);
        }
    }

    public void splitGoldOrGem(List<MaterialResponseDTO> list, PurchaseOrderEntity purchaseOrderEntity){
        for (MaterialResponseDTO item: list){
            if (item.getName().contains("Vàng")){
                MaterialEntity materialEntity = materialRepository.findByName(item.getName());
                PurchaseOrderDetailEntity purchaseOrderDetailEntity = new PurchaseOrderDetailEntity();
                purchaseOrderDetailEntity.setWeight(item.getWeight());
                purchaseOrderDetailEntity.setMaterial(materialEntity);
                purchaseOrderDetailEntity.setPurchaseOrder(purchaseOrderEntity);
                purchaseOrderDetailEntity.setPrice(item.getPrice());
                purchaseOrderDetailRepository.save(purchaseOrderDetailEntity);
            } else if (item.getName().contains("Diamond")){
                GemEntity gemEntity = gemRepository.findByGemName(item.getName());
                PurchaseOrderDetailEntity purchaseOrderDetailEntity = new PurchaseOrderDetailEntity();
                purchaseOrderDetailEntity.setOrigin(gemEntity.getOrigin());
                purchaseOrderDetailEntity.setColor(gemEntity.getColor());
                purchaseOrderDetailEntity.setCut(gemEntity.getCut());
                purchaseOrderDetailEntity.setCaratWeight(item.getWeight());
                purchaseOrderDetailEntity.setClarity(gemEntity.getClarity());
                purchaseOrderDetailEntity.setPrice(item.getPrice());
                purchaseOrderDetailEntity.setPurchaseOrder(purchaseOrderEntity);
                purchaseOrderDetailRepository.save(purchaseOrderDetailEntity);
            }
        }
    }
}
