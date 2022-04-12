package com.shop.repository;

import com.shop.dto.MainStoreDto;
import com.shop.dto.StoreSearchDto;
import com.shop.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreRepositoryCustom {
    Page<Store> getAdminStorePage(StoreSearchDto storeSearchDto, Pageable pageable);

    Page<MainStoreDto> getMainStorePage(StoreSearchDto storeSearchDto, Pageable pageable);
}
