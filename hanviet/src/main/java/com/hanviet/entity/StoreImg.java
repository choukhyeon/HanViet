package com.hanviet.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "store_img")
@Getter
@Setter
public class StoreImg extends BaseEntity{

    @Id
    @Column(name = "store_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String storeImgName;
    private String storeOriImgName;
    private String storeImgUrl;
    private String storeRepImgYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public void updateStoreImg(String storeOriImgName, String storeImgName, String storeImgUrl){
        this.storeOriImgName = storeOriImgName;
        this.storeImgName = storeImgName;
        this.storeImgUrl = storeImgUrl;
    }
}
