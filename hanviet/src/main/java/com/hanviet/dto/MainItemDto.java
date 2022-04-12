package com.shop.dto;


import com.querydsl.core.annotations.QueryProjection;
import com.shop.entity.Item;
import com.shop.entity.Store;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MainItemDto {

    private Long id;

    private String itemNm;

    private String itemDetail;

    private String imgUrl;

    private Integer price;

    private Store store;

    private static ModelMapper modelMapper = new ModelMapper();

    private List<MainItemDto> mainItemDtoList = new ArrayList<>();

    @QueryProjection
    public MainItemDto(Long id, String itemNm, String itemDetail, String imgUrl, Integer price, Store store){
        this.id = id;
        this.itemNm = itemNm;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
        this.store = store;
    }

}
