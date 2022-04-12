package com.shop.dto;

import com.shop.constant.Category;
import com.shop.constant.Location;
import com.shop.constant.OpenStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreSearchDto {

    private String searchDateType;

    private Location storeLocation;

    private Category storeCategory;

    private OpenStatus storeOpenStatus;

    private String searchBy;

    private String searchQuery = "";
}
