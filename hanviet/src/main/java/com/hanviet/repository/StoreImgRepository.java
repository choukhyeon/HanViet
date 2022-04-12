package com.shop.repository;

import com.shop.entity.StoreImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreImgRepository extends JpaRepository<StoreImg,Long> {
    List<StoreImg> findByStoreIdOrderByIdAsc(Long storeId);
}