package com.swp.jewelrystore.config;

import com.swp.jewelrystore.entity.GemPriceEntity;
import com.swp.jewelrystore.model.dto.GemPriceDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.swp.jewelrystore")
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public ModelMapper modelMapperIgnoreId() {
        ModelMapper modelMapper = new ModelMapper();
        // Define the mapping to skip the id field
        PropertyMap<GemPriceEntity, GemPriceEntity> gemPriceMap = new PropertyMap<GemPriceEntity, GemPriceEntity>() {
            protected void configure() {
                skip(destination.getId());
            }
        };
        modelMapper.addMappings(gemPriceMap);
        return modelMapper;
    }

    @Bean
    public ModelMapper modelMapperIgnoreEffectDate() {
        ModelMapper modelMapper = new ModelMapper();
        // Define the mapping to skip the id field
        PropertyMap<GemPriceDTO, GemPriceEntity> gemPriceMap = new PropertyMap<GemPriceDTO, GemPriceEntity>() {
            protected void configure() {
                skip(destination.getEffectDate());
            }
        };
        modelMapper.addMappings(gemPriceMap);
        return modelMapper;
    }

}