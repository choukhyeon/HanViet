package com.hanviet.dto;

import com.hanviet.constant.Category;
import com.hanviet.constant.Location;
import com.hanviet.constant.OpenStatus;
import com.hanviet.entity.Store;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class StoreFormDto {

    private Long id;

    @NotBlank(message = "가게명은 입력해야 합니다.")
    private String storeNm;
    @NotBlank(message = "가게 설명은 입력해야 합니다.")
    private String storeDetail;
    @NotBlank(message = "가게 주소는 입력해야 합니다.")
    private String storeAddress;

    private Location storeLocation;

    private Category storeCategory;

    private OpenStatus storeOpenStatus;

    private List<StoreImgDto> storeImgDtoList = new ArrayList<>();

    private List<Long> storeImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Store createStore(){
        return modelMapper.map(this, Store.class);
    }

    public static StoreFormDto of(Store store){
        return modelMapper.map(store,StoreFormDto.class);
    }
}
