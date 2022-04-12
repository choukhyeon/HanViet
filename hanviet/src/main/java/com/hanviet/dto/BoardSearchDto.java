package com.shop.dto;

import com.shop.constant.Category;
import com.shop.constant.Location;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardSearchDto {
    private String searchDateType;

    private Location boardLocation;    //지역별 기준으로 데이터 검색

    private Category boardCategory;

    private String searchBy;    //검색 유형 설정

    private String searchQuery= "";
}
