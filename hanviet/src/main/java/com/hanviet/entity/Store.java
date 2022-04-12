package com.shop.entity;


import com.shop.constant.Location;
import com.shop.constant.Category;
import com.shop.constant.OpenStatus;
import com.shop.dto.StoreFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "store")
@Getter
@Setter
@ToString
public class Store extends BaseEntity{

    @Id
    @Column(name = "store_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String storeNm; //가게명

    @Lob
    @Column(nullable = false)
    private String storeDetail; //가게 설명

    @Column(nullable = false, length = 100)
    private String storeAddress;    //가게주소

    @Enumerated(EnumType.STRING)
    private Location storeLocation; //가게지역

    @Enumerated(EnumType.STRING)
    private Category storeCategory; //가게업종

    @Enumerated(EnumType.STRING)
    private OpenStatus storeOpenStatus; //가게 운영

    public void updateStore(StoreFormDto storeFormDto){
        this.storeNm = storeFormDto.getStoreNm();
        this.storeDetail = storeFormDto.getStoreDetail();
        this.storeAddress = storeFormDto.getStoreAddress();
        this.storeLocation = storeFormDto.getStoreLocation();
        this.storeCategory = storeFormDto.getStoreCategory();
        this.storeOpenStatus = storeFormDto.getStoreOpenStatus();
    }
}
