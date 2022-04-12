package com.hanviet.dto;

import com.hanviet.constant.Category;
import com.hanviet.constant.Location;
import com.hanviet.constant.OpenStatus;
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
