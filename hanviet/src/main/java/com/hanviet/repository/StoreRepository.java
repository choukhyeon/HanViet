package com.hanviet.repository;


import com.hanviet.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store,Long>, QuerydslPredicateExecutor<Store>,StoreRepositoryCustom {
    List<Store> findByStoreNm(String storeNm);
}
