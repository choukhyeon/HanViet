package com.shop.dto;

import com.shop.entity.StoreImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class StoreImgDto {
    private  Long id;

    private String storeImgName;

    private String storeOriImgName;

    private String storeImgUrl;

    private String storeRepImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static StoreImgDto of(StoreImg storeImg){
        return modelMapper.map(storeImg,StoreImgDto.class);
    }
}
