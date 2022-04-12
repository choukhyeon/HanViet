package com.hanviet.repository;

import com.hanviet.entity.StoreImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreImgRepository extends JpaRepository<StoreImg,Long> {
    List<StoreImg> findByStoreIdOrderByIdAsc(Long storeId);
}
