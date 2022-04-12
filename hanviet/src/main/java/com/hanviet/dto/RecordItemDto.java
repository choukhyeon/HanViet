package com.shop.dto;

import com.shop.entity.RecordItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordItemDto {


    public RecordItemDto(RecordItem recordItem, String recordImgUrl){
        this.itemNm = recordItem.getItem().getItemNm();
        this.count = recordItem.getCount();
        this.recordPrice = recordItem.getRecordPrice();
        this.recordImgUrl = recordImgUrl;


    }
    private  String itemNm;

    private int count;

    private int recordPrice;

    private String recordImgUrl;


}
