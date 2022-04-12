package com.hanviet.dto;


import com.querydsl.core.annotations.QueryProjection;
import com.hanviet.constant.Category;
import com.hanviet.constant.Location;
import com.hanviet.constant.OpenStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainStoreDto {

    private Long id;

    private String storeNm;

    private String storeDetail;

    private String storeImgUrl;

    private Location storeLocation;

    private Category storeCategory;

    private OpenStatus storeOpenStatus;

    @QueryProjection
    public MainStoreDto(Long id, String storeNm, String storeDetail, String storeImgUrl, Location storeLocation , Category storeCategory , OpenStatus storeOpenStatus){
        this.id = id;
        this.storeNm = storeNm;
        this.storeDetail = storeDetail;
        this.storeImgUrl = storeImgUrl;
        this.storeLocation = storeLocation;
        this.storeCategory = storeCategory;
        this.storeOpenStatus = storeOpenStatus;
    }
}
